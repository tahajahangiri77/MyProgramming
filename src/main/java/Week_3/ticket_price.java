package Week_3;
import java.util.Scanner;
public class ticket_price {
    public static void main(String[] args) {
        Scanner isStudent = new Scanner(System.in);
        System.out.println("Are you a student? true or false");

        Boolean student = isStudent.nextBoolean();
        Scanner age = new Scanner(System.in);
        System.out.println("How old are you?");
        int student_age = age.nextInt();
        int original_price = 30;
        int discount = 15;
        if (student) {
            int discount_price = original_price / 2;
            System.out.println("The price you have to pay: " + discount_price);
        } else if (student_age < 15 || student_age > 65) {
            int discount_price = original_price / 2;
        } else {
            int discount_price = original_price;

        }
    }
}
