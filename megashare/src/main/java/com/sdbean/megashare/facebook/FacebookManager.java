package com.sdbean.megashare.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.sdbean.megashare.share.MegaShare;
import com.sdbean.megashare.share.ShareListener;
import com.sdbean.megashare.share.SharePlatform;
import com.sdbean.megashare.util.FileHelper;
import com.sdbean.megashare.util.PlatformHelper;

/**
 * Facebook https://developers.facebook.com/docs/sharing/android
 */
final public class FacebookManager extends MegaShare
{

    private final String TAG = "FacebookManager";

    private static FacebookManager mManager;
    private        ShareListener   mListener;
    private        Context         mContext;

    private FacebookManager(){}
    public static FacebookManager getInstance(){
        if (mManager == null) {
            synchronized (FacebookManager.class) {
                if (mManager == null) {
                    mManager = new FacebookManager();
                }
            }
        }
        return mManager;
    }

    // 获得散列
    public String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            String packageName = context.getApplicationContext().getPackageName();

            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        return key;
    }

    /**
     * 分享网页.
     *
     * @param context 上下文.
     * @param webpageUrl 网页链接.
     * @param quote 网页引文描述.
     * @param hashTag 主题标签, 形如: "#HelloWorld", 不能有任何符号.
     * @param mode 分享模式 {@linkplain MegaShare.Mode}.
     * @param listener {@linkplain ShareListener}.
     */
    public void shareWebpage(@NonNull Context context,
                             @NonNull String webpageUrl,
                             @Nullable String quote,
                             @Nullable String hashTag,
                             MegaShare.Mode mode,
                             @Nullable ShareListener listener) {

        if (mode == Mode.Native && !PlatformHelper.isInstalled(context, SharePlatform.Platform
                .Facebook)) {
            Log.e(TAG, "Facebook not installed");
            return;
        }
        mContext = context;
        mListener = listener;

        Intent intent = new Intent(context, FacebookActivity.class);
        intent.putExtra("webpageUrl", webpageUrl);
        intent.putExtra("quote", quote);
        intent.putExtra("hashTag", hashTag);
        intent.putExtra("mode", mode);
        intent.putExtra("type", ShareContentType.Webpage);
        context.startActivity(intent);
    }

    /**
     * 分享图片.
     * @param context 上下文.
     * @param photos bitmap格式的图片数据数组, 不能超过6张, 每张大小不能超过12M.
     */
    public void sharePhoto(@NonNull Context context,
                           @NonNull Bitmap photos) {

        if (!PlatformHelper.isInstalled(context, SharePlatform.Platform
                .Facebook)) {
            Log.e(TAG, "Facebook not installed.");
            return;
        }

        mContext = context;

        Intent intent = new Intent(context, FacebookActivity.class);
        FileHelper.deleteExternalShareDirectory(context);
        FileHelper.saveBitmapToExternalSharePath(context, photos);

        intent.putExtra("type", ShareContentType.Photo);
        context.startActivity(intent);
    }

    /**
     * 分享图片.
     * @param context 上下文.
     * @param photos bitmap格式的图片数据数组, 不能超过6张, 每张大小不能超过12M.
     */
    public void sharePhotos(@NonNull Context context,
                           @NonNull ArrayList<Bitmap>photos) {

        if (!PlatformHelper.isInstalled(context, SharePlatform.Platform
                .Facebook)) {
            Log.e(TAG, "Facebook not installed.");
            return;
        }

        if (photos.size() > 6) {
            Log.e(TAG, "图片不能超过6张!");
            return;
        }

        mContext = context;

        Intent intent = new Intent(context, FacebookActivity.class);
        FileHelper.deleteExternalShareDirectory(context);
        for (int i = 0; i < photos.size(); i ++) {
            FileHelper.saveBitmapToExternalSharePath(context, photos.get(i));
            Log.e(TAG, i + "");
        }

        intent.putExtra("type", ShareContentType.Photo);
        context.startActivity(intent);
    }

    /**
     * 分享本地视频到 Facebook.
     * @param context 上下文.
     * @param localVideoUri 本地视频 Uri.
     */
    public void shareLocalVideo(@NonNull Context context, @NonNull Uri localVideoUri) {

        if (!PlatformHelper.isInstalled(context, SharePlatform.Platform
                .Facebook)) {
            Log.e(TAG, "Facebook not installed.");
            return;
        }
        mContext = context;
        Intent intent = new Intent(context, FacebookActivity.class);
        intent.putExtra("type", ShareContentType.Video);
        intent.putExtra("local_video_path", localVideoUri);
        context.startActivity(intent);
    }


    protected ShareListener getListener() {
        return mListener;
    }
    protected Context getContext() { return  mContext; }

}
