package com.etatech.test.utils;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * Created by QiaoGaojian
 * Date:  2021/6/28
 * Desc:
 */
public class StorageUtil {
    /**
     * 获取sd卡剩余存储空间
     *
     * @return 返回kb字节为单位的long整形数值
     */
    public static long readSDCard() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            Log.d("", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + blockSize
                    * blockCount / 1024 + "KB");
            Log.d("", "可用的block数目：:" + availCount + ",剩余空间:" + availCount * blockSize / 1024
                    + "KB");
            return availCount * blockSize / 1000;
        } else {
            return 0;
        }
    }

    public static String getSDCardStorageLog() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();

            StringBuilder builder = new StringBuilder();
            builder.append("\n");
            builder.append("内存卡储存空间:\n");
            builder.append("总大小:" + blockSize * blockCount / (1000 * 1000) + "MB");
            builder.append("\n");
            builder.append("剩余空间:" + availCount * blockSize / (1000 * 1000) + "MB");
            builder.append("\n");

            return builder.toString();
        } else {
            return "没有内存卡\n";
        }
    }

    /**
     * 获取sd卡剩余存储空间
     *
     * @return 返回M字节为单位的long整形数值
     */
    public static long readSDCardM() {
        return readSDCard() / 1000;
    }

    /**
     * 判断剩余空间是否大于指定空间
     *
     * @param sizeMb 以MB为单位的指定大小
     * @return 返回值true为有相应空间，false为小于对应空间
     */
    public static boolean isAvailableSpace(int sizeMb) {
        boolean hasSpace = false;
        long availableSpare = readSDCardM();
        if (availableSpare > sizeMb) {
            hasSpace = true;
        }
        return hasSpace;
    }

    /**
     * 判断剩余空间是否大于指定空间
     *
     * @return 返回值true为有100M，false为小于100M
     */
    public static boolean isAvailableSpace() {
        return isAvailableSpace(100);
    }

}
