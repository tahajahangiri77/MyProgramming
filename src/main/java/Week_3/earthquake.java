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



    }
}
