package com.etatech.test.Java;

import java.util.LinkedList;

/**
 * Created by Michael
 * Date:  2020/3/25
 * Func:
 */
public class JavaTest {

    public static void main(String args[]) {
        System.out.println("android"+testNull());
    }

    public static void hello() {
        int num = 666;
        System.out.println(String.format("Hello World %d", num));
    }

    public static String testNull() {
        return null;
    }

    public static void testLinklist() {
        LinkedList<TestBean> linkedList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.add(new TestBean(i+""));
        }
        for (TestBean s : linkedList) {   // 如果集合中储存的是值类型 无法修改
            if (s.getValue().equals("6")) {
                s.setValue("666");
            }
        }
        for (TestBean s : linkedList) {
            System.out.println(s.getValue());
        }
    }

    static class TestBean {
        String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public TestBean(String value) {
            this.value = value;
        }
    }
}
