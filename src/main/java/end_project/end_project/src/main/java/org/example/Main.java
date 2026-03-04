package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

public class Main extends Application {

    // ⚠️ Do NOT publish this key on GitHub
    private static final String USDA_API_KEY = "Enter Your key";

    private int score = 0;
    private int total = 0;

    private final Label questionLabel = new Label("Loading...");
    private final Label feedbackLabel = new Label(" ");
    private final Label scoreLabel = new Label("Score: 0 / 0");

    private final Button[] answerButtons = new Button[4];
    private final Button nextButton = new Button("Next Question");

    private question currentQuestion;

    // Reuse objects (better performance)
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new Random();

    // Cache calories so repeated foods don't hit API again
    private final Map<String, Double> calorieCache = new HashMap<>();

    @Override
    public void start(Stage stage) {
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new Button(" ");
            answerButtons[i].setMaxWidth(Double.MAX_VALUE);

            final int index = i;
            answerButtons[i].setOnAction(e -> checkAnswer(index));
            answerButtons[i].setDisable(true);
        }

        nextButton.setOnAction(e -> loadQuestion());

        VBox root = new VBox(
                10,
                questionLabel,
                answerButtons[0],
                answerButtons[1],
                answerButtons[2],
                answerButtons[3],
                feedbackLabel,
                scoreLabel,
                nextButton
        );

        root.setStyle("-fx-padding: 20;");

        stage.setTitle("Fitness & Nutrition Quiz");
        stage.setScene(new Scene(root, 500, 400));
        stage.show();

        loadQuestion();
    }

    private void loadQuestion() {
        questionLabel.setText("Loading...");
        feedbackLabel.setText(" ");

        for (Button b : answerButtons) b.setDisable(true);
        nextButton.setDisable(true);

        Task<question> task = new Task<>() {
            @Override
            protected question call() throws Exception {
                return fetchquestion();
            }
        };

        task.setOnSucceeded(e -> {
            currentQuestion = task.getValue();
            questionLabel.setText(currentQuestion.text);

            for (int i = 0; i < 4; i++) {
                answerButtons[i].setText(currentQuestion.options.get(i));
                answerButtons[i].setDisable(false);
            }

            nextButton.setDisable(false);
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            ex.printStackTrace();

            questionLabel.setText("Failed to load question");
            feedbackLabel.setText(ex.getMessage() != null ? ex.getMessage() : "Unknown error");

            nextButton.setDisable(false);
        });

        new Thread(task).start();
    }

    private void checkAnswer(int chosenIndex) {
        if (currentQuestion == null) return;

        total++;

        if (chosenIndex == currentQuestion.correctIndex) {
            score++;
            feedbackLabel.setText("✅ Correct!");
        } else {
            feedbackLabel.setText("❌ Wrong!");
        }

        scoreLabel.setText("Score: " + score + " / " + total);

        for (Button b : answerButtons) b.setDisable(true);
    }

    private double fetchCaloriesPer100g(String food) throws Exception {

        // 1) Search food -> get fdcId
        String searchUrl =
                "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=" + USDA_API_KEY +
                        "&query=" + URLEncoder.encode(food, StandardCharsets.UTF_8) +
                        "&pageSize=1";

        HttpRequest searchRequest = HttpRequest.newBuilder()
                .uri(URI.create(searchUrl))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> searchResponse = client.send(searchRequest, HttpResponse.BodyHandlers.ofString());

        if (searchResponse.statusCode() != 200) {
            throw new RuntimeException("USDA search error " + searchResponse.statusCode() + ": " + searchResponse.body());
        }

        JsonNode searchJson = mapper.readTree(searchResponse.body());
        JsonNode foodsNode = searchJson.get("foods");

        if (foodsNode == null || !foodsNode.isArray() || foodsNode.size() == 0) {
            throw new RuntimeException("USDA: no results for " + food);
        }

        int fdcId = foodsNode.get(0).get("fdcId").asInt();

        // 2) Get food details -> find Energy (kcal)
        String detailsUrl =
                "https://api.nal.usda.gov/fdc/v1/food/" + fdcId + "?api_key=" + USDA_API_KEY;

        HttpRequest detailsRequest = HttpRequest.newBuilder()
                .uri(URI.create(detailsUrl))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> detailsResponse = client.send(detailsRequest, HttpResponse.BodyHandlers.ofString());

        if (detailsResponse.statusCode() != 200) {
            throw new RuntimeException("USDA details error " + detailsResponse.statusCode() + ": " + detailsResponse.body());
        }

        JsonNode detailsJson = mapper.readTree(detailsResponse.body());
        JsonNode nutrients = detailsJson.get("foodNutrients");

        if (nutrients == null || !nutrients.isArray()) {
            throw new RuntimeException("USDA: nutrients missing for " + food);
        }

        // USDA nutrientId 1008 = Energy (kcal) commonly used in FDC
        for (JsonNode n : nutrients) {
            JsonNode nutrient = n.get("nutrient");
            JsonNode amount = n.get("amount");

            if (nutrient != null && amount != null && nutrient.has("id")) {
                int nutrientId = nutrient.get("id").asInt();
                if (nutrientId == 1008) {
                    double kcal = amount.asDouble();
                    System.out.println(food + " => " + kcal + " kcal (FDC entry)");
                    return kcal; // typically per 100g for many FDC items
                }
            }
        }

        throw new RuntimeException("USDA: calories (energy) not found for " + food);
    }

    private question fetchquestion() throws Exception {
        String[] foods = {"banana", "apple", "rice", "chicken breast", "salmon", "bread", "milk", "cola","water","burger","pizza"};

        for (int attempts = 0; attempts < 6; attempts++) {
            String a = foods[random.nextInt(foods.length)];
            String b = foods[random.nextInt(foods.length)];
            while (b.equals(a)) b = foods[random.nextInt(foods.length)];

            try {
                double calA = fetchCaloriesPer100g(a);
                Thread.sleep(250);
                double calB = fetchCaloriesPer100g(b);

                String text = "Which has MORE calories per 100g?";

                List<String> baseOptions = List.of(a, b, "Both same", "Not sure");
                List<String> shuffled = new ArrayList<>(baseOptions);
                Collections.shuffle(shuffled);

                int correctIndex;
                if (Math.abs(calA - calB) < 1.0) {
                    correctIndex = shuffled.indexOf("Both same");
                } else if (calA > calB) {
                    correctIndex = shuffled.indexOf(a);
                } else {
                    correctIndex = shuffled.indexOf(b);
                }

                return new question(text, shuffled, correctIndex);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        throw new RuntimeException("Could not generate a question after retries.");
    }
    public static void main(String[] args) {
        launch(args);
    }
}