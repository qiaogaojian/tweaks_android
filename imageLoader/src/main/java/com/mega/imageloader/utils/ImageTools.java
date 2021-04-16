package com.mega.imageloader.utils;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.File;

public class ImageTools {

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }
    /**
     * 清除所有缓存
     * @param context
     */
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        Glide.get(context).clearMemory();
    }

    public static void trimMemory(Context context,int level){
        Glide.get(context).trimMemory(level);
    }

    public static void clearMemory(Context context){
        Glide.get(context).clearMemory();
    }

    public static void setTagId(@IdRes int id){
        ViewTarget.setTagId(id);
    }


    /**
     * 获取缓存大小
     * @return
     */
//    public static synchronized long getDiskCacheSize(Context context) {
//        long size = 0L;
//        File cacheDir = PathUtils.getDiskCacheDir(context, CacheConfig.IMG_DIR);
//        if (cacheDir != null && cacheDir.exists()) {
//            File[] files = cacheDir.listFiles();
//            if (files != null) {
//                File[] arr$ = files;
//                int len$ = files.length;
//
//                for (int i$ = 0; i$ < len$; ++i$) {
//                    File imageCache = arr$[i$];
//                    if (imageCache.isFile()) {
//                        size += imageCache.length();
//                    }
//                }
//            }
//        }
//
//        return size;
//    }

//    public static void clearTarget(Context context, String uri) {
//        if (SimpleGlideModule.cache != null && uri != null) {
//            SimpleGlideModule.cache.delete(new StringSignature(uri));
//            Glide.get(context).clearMemory();
//        }
//    }

    public static void clearTarget(View view) {

       // Glide.clear(view);
    }

//    public static File getTarget(Context context, String uri) {
//        return SimpleGlideModule.cache != null && uri != null ? SimpleGlideModule.cache.get(new StringSignature(uri)) : null;
//    }

    public static int dipTopx(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
