package Week_3;

import java.util.Scanner;
public class earthquake {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter earthquake magnitude");

        double magnitude = input.nextDouble();

        if(magnitude<0 || magnitude>10 )
            System.out.println("Value out of range , please try again with values between 0 and 10");


        String category = (magnitude < 2.0) ? "Micro":
                (magnitude < 4.0) ? "Minor" :
                (magnitude < 5.0) ? "Light" :
                (magnitude < 6.0) ? "Moderate" :
                (magnitude < 7.0) ? "Strong" :
                (magnitude < 8.0) ? "Major" : "Great" ;
        switch (category){
            case "Micro" -> System.out.println("Micro: No action needed; usually not felt.");
            case "Minor" -> System.out.println("Minor: Slight shaking; stay aware.");
            case "Light" -> System.out.println("Light:Drop, Cover, and Hold On; minor damage possible.");
            case "Moderate" -> System.out.println("Moderate: Protect yourself; expect aftershocks.");
            case "Strong" -> System.out.println("Strong: Serious damage possible; avoid hazards.");
            case "Major" -> System.out.println("Major: Severe damage expected; follow emergency instructions.");
            case "Great" -> System.out.println("Great: Catastrophic event; seek immediate safety and official guidance.");

    }
}
}