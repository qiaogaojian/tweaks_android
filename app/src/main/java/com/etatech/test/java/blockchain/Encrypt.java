package com.etatech.test.java.blockchain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;

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

    //Applies ECDSA Signature and returns the result ( as bytes ).
    public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
        Signature dsa;
        byte[] output = new byte[0];
        try {
            dsa = Signature.getInstance("ECDSA", "BC");
            dsa.initSign(privateKey);
            byte[] strByte = input.getBytes();
            dsa.update(strByte);
            byte[] realSig = dsa.sign();
            output = realSig;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    //Verifies a String signature
    public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStringFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }


    //Tacks in array of transactions and returns a merkle root.
    public static String getMerkleRoot(ArrayList<Transaction> transactions) {
        int count = transactions.size();
        ArrayList<String> previousTreeLayer = new ArrayList<String>();
        for(Transaction transaction : transactions) {
            previousTreeLayer.add(transaction.transactionId);
        }
        ArrayList<String> treeLayer = previousTreeLayer;
        while(count > 1) {
            treeLayer = new ArrayList<String>();
            for(int i=1; i < previousTreeLayer.size(); i++) {
                treeLayer.add(SHA256(previousTreeLayer.get(i-1) + previousTreeLayer.get(i)));
            }
            count = treeLayer.size();
            previousTreeLayer = treeLayer;
        }
        String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
        return merkleRoot;
    }
}
