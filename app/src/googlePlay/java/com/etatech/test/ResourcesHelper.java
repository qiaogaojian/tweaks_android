package com.etatech.test;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.etatech.test.utils.App;
import com.leo618.zip.IZipCallback;
import com.leo618.zip.ZipManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Michael
 * Date:  2020/7/6
 * Func:
 */
public class ResourcesHelper {
    public static String obbPath = App.filesPath + "/obb/";

    //    public static void playSplashVideoResource(VideoView videoView) {
    //        String filePath = ObbHelper.getCurrentObbFileFolder() + "raw/" + "splash.mp4";
    //        videoView.setVideoPath(filePath);
    //    }
    public static File getObbResourceFile(String fileName) {
        File file = new File(obbPath + fileName);
        return file;
    }

    public static Uri getObbResourceUri(String fileName) {
        Uri uri = Uri.parse(obbPath + fileName);
        return uri;
    }

    public static void unZipObb(Context context) {
        String obbFilePath = getObbFilePath(context);
        if (obbFilePath == null) {
            return;
        } else {
            File obbFile = new File(obbFilePath);
            if (!obbFile.exists()) {
                //TODO 下载obb文件
            } else {
                File outputFolder = new File(obbPath);
                if (!outputFolder.exists()) {
                    //目录未创建 没有解压过
                    outputFolder.mkdirs();
                    ZipManager.unzip(obbFilePath, outputFolder.getAbsolutePath(), new IZipCallback() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onProgress(int percentDone) {

                        }

                        @Override
                        public void onFinish(boolean success) {
                            if (success) {
                                ToastUtils.showShort("解压成功");
                            }
                        }
                    });
                } else {
                    //目录已创建 判断是否解压过
                    File[] files = outputFolder.listFiles();
                    if (files.length == 0) {
                        // 解压过的文件被删除
                        ZipManager.unzip(obbFilePath, outputFolder.getAbsolutePath(), new IZipCallback() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onProgress(int percentDone) {

                            }

                            @Override
                            public void onFinish(boolean success) {
                                if (success) {
                                    ToastUtils.showShort("解压成功");
                                }
                            }
                        });
                    } else {
                        //TODO 此处可添加文件对比逻辑
                    }
                }
            }
        }
    }

    //这里没有添加解压密码逻辑，小伙伴们可以自己修改添加以下
    public static void unZip(File zipFile, String outPathString) throws IOException {
        createDirectoryIfNeeded(outPathString);
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry zipEntry;
        String szName;
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {
                File file = new File(outPathString + File.separator + szName);
                createDirectoryIfNeeded(file.getParent());
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = inZip.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }

    public static String createDirectoryIfNeeded(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
        return folderPath;
    }

    public static String getObbFilePath(Context context) {
        try {
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/Android/obb/"
                    + context.getPackageName()
                    + File.separator
                    + "main."
                    + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode
                    + "."
                    + context.getPackageName()
                    + ".obb";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
