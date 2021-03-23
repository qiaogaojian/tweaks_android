package com.etatech.test.java.blockchain;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/23
 * Desc:
 */
public class Encrypt {

    /**
     * 传入文本内容，返回 MD5 串
     *
     * @param strText
     * @return
     */
    public static String MD5(final String strText) {
        return SHA(strText, "MD5");
    }

    /**
     * 传入对象，返回 MD5 串
     *
     * @param obj
     * @return
     */
    public static String MD5(final Object obj) {
        return SHA(new Gson().toJson(obj), "MD5");
    }

    /**
     * 传入文本内容，返回 SHA-1 串
     *
     * @param strText
     * @return
     */
    public static String SHA1(final String strText) {
        return SHA(strText, "SHA-1");
    }

    /**
     * 传入对象，返回 SHA-1 串
     *
     * @param obj
     * @return
     */
    public static String SHA1(final Object obj) {
        return SHA(new Gson().toJson(obj), "SHA-1");
    }

    /**
     * 传入文本内容，返回 SHA-224 串
     *
     * @param strText
     * @return
     */
    public static String SHA224(final String strText) {
        return SHA(strText, "SHA-224");
    }

    /**
     * 传入对象，返回 SHA-1 串
     *
     * @param obj
     * @return
     */
    public static String SHA224(final Object obj) {
        return SHA(new Gson().toJson(obj), "SHA-224");
    }

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    public static String SHA256(final String strText) {
        return SHA(strText, "SHA-256");
    }

    /**
     * 传入对象，返回 SHA-256 串
     *
     * @param obj
     * @return
     */
    public static String SHA256(final Object obj) {
        return SHA(new Gson().toJson(obj), "SHA-256");
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    public static String SHA512(final String strText) {
        return SHA(strText, "SHA-512");
    }

    /**
     * 传入对象，返回 SHA-512 串
     *
     * @param obj
     * @return
     */
    public static String SHA512(final Object obj) {
        return SHA(new Gson().toJson(obj), "SHA-512");
    }

    /**
     * 字符串 SHA 加密
     * @param strText
     * @param strType
     * @return
     */
    private static String SHA(final String strText, final String strType) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }


}
