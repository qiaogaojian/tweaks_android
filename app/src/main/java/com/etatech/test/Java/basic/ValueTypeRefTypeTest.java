package com.etatech.test.Java.basic;

/**
 * Created by Michael
 * Date:  2020/8/29
 * Func:
 */
public class ValueTypeRefTypeTest {
    private static int valueType1 = 1;
    private static RefTypeBean refBean;

    public static void main(String[] args) {
        System.out.println("valueType before:" + valueType1);
        testValueType(valueType1);
        System.out.println("valueType after:" + valueType1);

        refBean = new RefTypeBean();
        System.out.println("refType before:" + refBean.value);
        testRefType(refBean);
        System.out.println("refType after:" + refBean.value);

    }

    private static void testValueType(int value) {
        value++;
    }

    private static void testRefType(RefTypeBean bean) {
        bean.value++;
    }
}


class RefTypeBean {
    public int value = 1;
}