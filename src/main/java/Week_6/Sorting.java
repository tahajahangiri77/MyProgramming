package Week_6;

public class Sorting {
    public static void main(String[] args){
        int[] array = {1, 10, 4, 8, 27, 21, 23};
        System.out.println("before");
        printArray(array);
        mergeSort(array);
        System.out.println("after");
        printArray(array);


    }
    public static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }


    }
    public static void mergeSort(int[] array){
        if (array.length <= 1)
            return ;
        int middle = array.length / 2;
        int[] left = new int[middle];
        int[] right = new int[array.length - middle];
        for (int i = 0; i < middle; i++) {
            left[i] = array[i];
        }

        // Copy data into right array
        for (int i = middle; i < array.length; i++) {
            right[i - middle] = array[i];
        }

        mergeSort(left);
        mergeSort(right);
        merge(array,left,right);

    }
    public static void merge(int[] array, int[] left, int[] right) {

        int i = 0; // index for left array
        int j = 0; // index for right array
        int k = 0; // index for merged array

        // Compare elements from left and right arrays
        while (i < left.length && j < right.length) {

            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
            } else {
                array[k] = right[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements from left array (if any)
        while (i < left.length) {
            array[k] = left[i];
            i++;
            k++;
        }

        // Copy remaining elements from right array (if any)
        while (j < right.length) {
            array[k] = right[j];
            j++;
            k++;
        }
    }

}
