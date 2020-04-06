/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Helper
 *
 * @author Philipp Winter
 */
public class Helper {

    public static <E> void shuffle(E[] values) {
        int index;
        Random random = new Random();
        for (int i = values.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            if (index != i) {
                E temp = values[index];
                values[index] = values[i];
                values[i] = temp;
            }
        }
    }

    public static <T extends Comparable<T>> void quickSort(ArrayList<T> list,
                                                           Comparator<T> c) {
        int low = 0;
        int high = list.size() - 1;
        quickSort(list, low, high, c);
    }

    private static <T extends Comparable<T>> void quickSort(ArrayList<T> list,
                                                            int low, int high, Comparator<T> c) {
        int left = low;
        int right = high;
        T pivot = list.get((low + high) / 2);
        do {
            while (c.compare(list.get(left), pivot) < 0) {
                left++;
            }
            while (c.compare(pivot, list.get(right)) < 0) {
                right--;
            }
            if (left <= right) {
                T tmp = list.get(left);
                list.set(left, list.get(right));
                list.set(right, tmp);
                left++;
                right--;
            }
        } while (left <= right);
        if (low < right) {
            quickSort(list, low, right, c);
        }
        if (left < high) {
            quickSort(list, left, high, c);

        }
    }

}
