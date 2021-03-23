package com.etatech.test.java.basic;

/**
 * Created by Michael
 * Date:  2020/8/29
 * Func:
 */
public class ValueTypeRefTypeTest {
    private static int valueType1 = 1;
    private static String valueType2 = "1";  // String是特殊的引用类型
    private static RefTypeBean refBean;

    public static void main(String[] args) {
        System.out.println(String.format("valueType before: %d/%s", valueType1, valueType2));
        testValueType(valueType1, valueType2);
        System.out.println(String.format("valueType after: %d/%s", valueType1, valueType2));

        refBean = new RefTypeBean();
        System.out.println(String.format("\nrefType before: %d/%s", refBean.value, refBean.value2));
        testRefType(refBean);
        System.out.println(String.format("refType after: %d/%s", refBean.value, refBean.value2));

        refBean = new RefTypeBean();
        System.out.println(String.format("\nrefTypeChild before: %d/%s", refBean.child.childValue, refBean.child.childValue2));
        testRefChild(refBean);
        System.out.println(String.format("refTypeChild after: %d/%s", refBean.child.childValue, refBean.child.childValue2));

        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println("\ni1 == i2 " + (i1 == i2));

        Integer i5 = 128;
        Integer i6 = 128;
        System.out.println("i5 == i6 " + (i5 == i6));

        String i7 = "100";
        String i8 = "100";
        System.out.println("\ni7 == i8 " + (i7.equals(i8)));

        refBean = new RefTypeBean();
        refBean.value2 = "100";
        refBean.value3 = "100";
        System.out.println("refBean.value2 == refBean.value3 " + (refBean.value2 == refBean.value3));
    }

    private static void testValueType(int value, String valueType2) {
        value++;
        valueType2 = "2";
    }

    private static void testRefType(RefTypeBean bean) {
        bean.value++;
        bean.value2 = "2";
    }

    private static void testRefChild(RefTypeBean bean) {
        bean.child.childValue++;
        bean.child.childValue2 = "2";
    }
}


class RefTypeBean {
    public int value = 1;
    public String value2 = "1";
    public String value3 = "3";
    public RefChildBean child = new RefChildBean();
}

class RefType2Bean {
    public int value = 1;
    public String value2 = "1";
    public RefChildBean child = new RefChildBean();
}

class RefChildBean {
    public int childValue = 1;
    public String childValue2 = "1";
}