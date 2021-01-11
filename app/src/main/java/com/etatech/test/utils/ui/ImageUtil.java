package com.etatech.test.utils.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.ToastUtils;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, fout);
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
            file = new File(galleryPath, picName + ".png");
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream);
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

    public static void saveSvg(final Context context, final String url, final SaveResultCallback saveResultCallback) {
        new Thread(new Runnable() {


            @Override
            public void run() {
                File appDir = new File(Environment.getExternalStorageDirectory(), "out_photo");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置以当前时间格式为图片名称
                String fileName = df.format(new Date()) + ".jpg";
                File file = new File(appDir, fileName);
                if (url.endsWith(".svg")) {
                    //拿到图片在assets目录下的相对路径
                    String replaceUrl = url.replace("file:///android_asset/", "");
                    try {
                        SVG svg = new SVGBuilder().readFromAsset(context.getAssets(), replaceUrl).build();
                        //拿到svg图片的drawable
                        PictureDrawable drawable = svg.getDrawable();
                        //图片背景的画笔
                        Paint paint = new Paint();
                        paint.setColor(Color.WHITE);
                        //图片线条的画笔
                        Paint paint1 = new Paint();
                        paint1.setColor(Color.BLACK);
                        //创建bitmap对象
                        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawRect(0, 0, bitmap.getWidth() + 50, bitmap.getHeight() + 50, paint);
                        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        drawable.draw(canvas);
                        FileOutputStream fos = new FileOutputStream(file);
                        //转为jpg格式并写入到sd卡
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                        saveResultCallback.onSavedSuccess();
                    } catch (IOException e) {
                        e.printStackTrace();
                        saveResultCallback.onSavedFailed();
                    }
                } else {
                    try {
                        //保存jpg格式的图片到相册中
                        FileOutputStream fos = new FileOutputStream(file);
                        InputStream fis = context.getAssets().open(url.replace("file:///android_asset/", ""));
                        int len = 0;
                        byte[] bytes = new byte[1024];
                        while ((len = fis.read(bytes)) != -1) {
                            fos.write(bytes, 0, len);
                        }
                        fos.flush();
                        fis.close();
                        fos.close();
                        saveResultCallback.onSavedSuccess();
                    } catch (FileNotFoundException e) {
                        saveResultCallback.onSavedFailed();
                        e.printStackTrace();
                    } catch (IOException e) {
                        saveResultCallback.onSavedFailed();
                        e.printStackTrace();
                    }
                }

                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        }).start();
    }

    public interface SaveResultCallback {
        void onSavedSuccess();

        void onSavedFailed();
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}