package com.etatech.test.utils;

/**
 * Created by Michael
 * Date:  2020/2/6
 * Func:
 */

public class StringUtil {

    /**
     * 左对齐填充字符串
     *
     * @param c       要填充的字符串
     * @param length  总宽度
     * @param content 实际内容
     * @return 填充后的字符串
     */
    public static String fillStringLeft(String content, long length, char c) {
        String str = "";
        long   cl  = 0;
        String cs  = "";
        if (content.length() > length)
        {
            str = content;
        } else
        {
            for (int i = 0; i < length - content.length(); i++)
            {
                cs = cs + c;
            }
        }
        str = content + cs;
        return str;
    }
}
