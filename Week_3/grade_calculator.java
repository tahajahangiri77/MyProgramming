package Main;

public class grade_calculator {
    public static Object main(String[] args) {
        int score = 90;
        switch (score / 10) {
            case 10:
            case 9:
                return "A";
            case 8:
                return "B";
            case 7:
                return "C";
            case 6:
                return "D";
            default:
                return "F";
        }
    }
}
