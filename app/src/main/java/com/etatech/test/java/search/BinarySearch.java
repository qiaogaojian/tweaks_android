package com.etatech.test.java.search;

/**
 * Created by Michael
 * Date:  2020/8/22
 * Func: binary search max search Num = log(n,2) + 1
 */
public class BinarySearch {
    private static int count = 0;

    public static void main(String[] args) {
        System.out.println("searchNum:");
        count = 0;
        searchNum(0, 100, 99);
        System.out.println("searchNumRecursion:");
        count = 0;
        searchNumRecursion(0, 100, 99);
    }

    private static void searchNumRecursion(int min, int max, int target) {
        count++;
        int mid = (min + max) / 2;
        if (target > mid) {
            min = mid;
            System.out.println(String.format("%2d: It's bigger than %d", count, mid));
            searchNumRecursion(min, max, target);
        } else if (target < mid) {
            max = mid;
            System.out.println(String.format("%2d: It's smaller than %d", count, mid));
            searchNumRecursion(min, max, target);
        } else {
            System.out.println(String.format("%2d: You get it! It's %d", count, mid));
        }
    }

    private static void searchNum(int min, int max, int target) {
        int mid = (min + max) / 2;
        count++;
        while (target != mid) {
            if (target > mid) {
                min = mid;
                System.out.println(String.format("%2d: It's bigger than %d", count, mid));
            } else {
                max = mid;
                System.out.println(String.format("%2d: It's smaller than %d", count, mid));
            }
            mid = (min + max) / 2;
            count++;
        }
        System.out.println(String.format("%2d: You get it! It's %d", count, mid));
    }
}
