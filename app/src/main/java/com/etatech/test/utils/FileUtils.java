package com.etatech.test.utils;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import static com.etatech.test.utils.App.RES_PATH;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/5
 * Desc: 获取文件目录工具类
 */

public class FileUtils {
    private static String fileBasePath = "/res/";

    public static String getResPath() {
        String fileURL = RES_PATH + fileBasePath;
        File file = new File(fileURL);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
        return fileURL;
    }

    public static String getExternalPath() {
        String root =  Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileURL = root + fileBasePath;
        File file = new File(fileURL);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
        return fileURL;
    }

    public static String getResFilePath(String fileName) {
        return RES_PATH + fileBasePath + fileName;
    }

    public static boolean copyToRes(AssetManager assets, String srcRelPath) {

        String desPath = getResPath() + srcRelPath;
        File desFile = new File(desPath);
        if (!desFile.exists()) {
            File folder = new File(desFile.getParent());
            folder.mkdirs();
        }
        return copyAssetFile(assets, srcRelPath, desPath);
    }

    public static boolean copyAssetFile(AssetManager assets, String srcRelPath, String dstAbsPath) {
        try (InputStream is = assets
                .open(srcRelPath, AssetManager.ACCESS_STREAMING)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            FileOutputStream fos = new FileOutputStream(dstAbsPath);
            fos.write(buffer);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
