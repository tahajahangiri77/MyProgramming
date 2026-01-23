package Main;
import java.util.Scanner;
public class ticket_price {
    public static void main(String[] args) {
        Scanner isStudent = new Scanner(System.in);
        System.out.println("Are you a student? true or false");
        Boolean student = isStudent.nextBoolean();
        Scanner age = new Scanner(System.in);
        System.out.println("How old are you?");
        int student_age = age.nextInt();
        int price = 30;
        int discount = 15;
        if (student) {
            System.out.println("Student is a student");
        }

    }
}
