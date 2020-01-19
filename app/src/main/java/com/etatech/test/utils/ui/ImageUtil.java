package com.etatech.test.utils.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Michael
 * Data: 2020/1/19 11:07
 * Desc: 图片加载处理工具类
 */
public class ImageUtil {

    /**
     * 返回View的Bitmap图片
     *
     * @param v
     * @param save
     * @return
     */
    public static Bitmap view2Bitmap(View v, boolean save) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        if (!save) {
            try {
                FileOutputStream fout = new FileOutputStream("mnt/sdcard/display.png");
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fout);
                fout.flush();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 保存View截图
     *
     * @param v 要保存的View
     */
    public static void viewShot(@NonNull final View v) {
        if (null == v) {
            return;
        }
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                // 核心代码start
                Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
                v.draw(c);
                // end
                saveBmp2Gallery(bitmap, "cropTest", v.getContext());
            }
        });
    }

    /**
     * 保存图片到相册
     *
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Bitmap bmp, String picName, Context context) {
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "mega";
        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }
        } catch (Exception e) {
            ToastUtils.showShort("图片保存失败");
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ToastUtils.showShort("图片保存成功");
        //通知相册更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
}
