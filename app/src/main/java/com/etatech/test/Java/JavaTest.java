package com.etatech.test.Java;

import com.etatech.test.utils.Tools;

import java.util.LinkedList;

/**
 * Created by Michael
 * Date:  2020/3/25
 * Func:
 */
public class JavaTest {

    public static void main(String args[]) {
        testRandomRange();
    }

    public static void hello() {
        int num = 666;
        System.out.println(String.format("Hello World %d", num));
    }

    private static void testNull() {
        System.out.println("android" + null);
    }

    private static void testLinklist() {
        LinkedList<TestBean> linkedList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.add(new TestBean(i));
        }
        for (TestBean s : linkedList) {
            if (s.getValue() == 6) {
                s.setValue(666);
            }
        }
        for (TestBean s : linkedList) {
            System.out.print(String.format("%d ", s.getValue()));
        }
    }

    private static void testRandomRange() {
        for (int i = 0; i < 100; i++) {
            System.out.print(String.format("%d ", Tools.randomRange(10, 20)));
        }
    }
}

class TestBean {
    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TestBean(int value) {
        this.value = value;
    }
}