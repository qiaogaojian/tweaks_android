package com.etatech.test.Java.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael
 * Date:  2020/7/31
 * Func: 冒泡排序
 */
public class BubbleSort {
    public static List<Integer> input = Arrays.asList(8, 5, 6, 9, 3, 1, 2, 4, 7);

    public static void main(String[] args) {
        for (Integer integer : input) {
            System.out.print(String.format("%d  ", integer));
        }

        sort(input);

        System.out.println();
        System.out.println("======>> 冒泡排序 ======>>");
        for (Integer integer : input) {
            System.out.print(String.format("%d  ", integer));
        }
    }


    public static void sort(List<Integer> input) {
        for (int i = 1; i < input.size(); i++) {
            for (int j = 0; j < input.size() - i; j++) {
                int tem = input.get(j);
                if (input.get(j) > input.get(j + 1)) {
                    input.set(j, input.get(j + 1));
                    input.set(j + 1, tem);
                }
            }
        }
    }
}
