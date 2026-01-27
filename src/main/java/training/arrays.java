package training;

import java.util.Arrays;

public class arrays {
    public static void main(String[] args) {
        int [] numbers = {1,2,3,4,5};
        String[] words = {"Ignition sequence start!", "Liftoff!"};

        System.out.println(words[0]);
        System.out.println(numbers[4]);
        System.out.println(numbers[3]);
        System.out.println(numbers[2]);
        System.out.println(numbers[1]);
        System.out.println(numbers[0]);
        System.out.println(words[1]);

        for (int i = numbers.length-1; i >= 0; i--) {
            System.out.println(numbers[i]);
        }

    }
}
