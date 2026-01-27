package training;

public class loops {
    public static void main(String[] args) {
        int[] numbers = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        for (int i = 0; i < numbers.length; i++){
            System.out.println(numbers[i]);

        }

        int index = 0;
        while(index < numbers.length){
            System.out.println(numbers[index]);
            index++;
        }

        int index1 = 0;
        do{
            System.out.println(numbers[index1]);
            index1++;
        }
        while (index1 < numbers.length);
    }
}
