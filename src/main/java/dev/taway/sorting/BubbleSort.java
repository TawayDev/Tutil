package dev.taway.sorting;
import dev.taway.logging.LogLevel;
import dev.taway.logging.Logger;
public class BubbleSort {
    static Logger logger = new Logger("BubbleSort");
    /**
     * Sorts integer array from smallest to largest.
     * @param arr Array to be sorted.
     * @return Returns sorted array.
     * @since 0.1.3
     */
    public int[] sort(int[] arr) {
        try{
            int i, j, temp;
            boolean swapped;
            for (i = 0; i < arr.length - 1; i++) {
                swapped = false;
                for (j = 0; j < arr.length - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                        swapped = true;
                    }
                }
                if (!swapped) break;
            }
            return arr;
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "sort", exception.getMessage());
        }
        return new int[0];
    }

    /**
     * Sorts integer array from largest to smallest.
     * @param arr Array to be sorted
     * @return Returns sorted array.
     * @since 0.1.3
     */
    public int[] sortInverted(int[] arr) {
        try {
//            Sort
            arr = sort(arr);
//            Invert
            int[] arrInverted = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                arrInverted[i] = arr[arr.length - 1 - i];
            }
            return arrInverted;
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "sortInverted", exception.getMessage());
        }
        return new int[0];
    }
}
