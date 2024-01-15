package dev.taway.tutil.sorting;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BubbleSortTest {

    BubbleSort bubbleSort = new BubbleSort();

    @Test
    void sort() {
        int[] arr = {2, 1, 4, 3};
        int[] arrSorted = bubbleSort.sort(arr);
        assertEquals("[1, 2, 3, 4]", Arrays.toString(arrSorted));
    }

    @Test
    void sortInverted() {
        int[] arr = {2, 1, 4, 3};
        int[] arrSorted = bubbleSort.sortInverted(arr);
        assertEquals("[4, 3, 2, 1]", Arrays.toString(arrSorted));
    }
}