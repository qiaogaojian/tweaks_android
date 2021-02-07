package com.sdbean.megashare.line;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import android.util.Log;

import com.sdbean.megashare.share.MegaShare;
import com.sdbean.megashare.share.SharePlatform;
import com.sdbean.megashare.util.FileHelper;
import com.sdbean.megashare.util.PlatformHelper;

final public class LineManager extends MegaShare {
    private final String TAG = "RInstagramManager===>";

    private static LineManager mManager;

    private LineManager() {
    }

    public static LineManager getInstance() {
        if (mManager == null) {
            synchronized (LineManager.class) {
                if (mManager == null) {
                    mManager = new LineManager();
                }
            }
        }
        return mManager;
    }

    public void shareText(Context context, String text) {
        if (!PlatformHelper.isInstalled(context, SharePlatform.Platform.Line)) {
            Log.e(TAG, "Line not installed");
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/*");

        intent.setPackage("jp.naver.line.android");
        context.startActivity(intent);

    }

    public void shareImage(Context context, Bitmap image) {
        if (!PlatformHelper.isInstalled(context, SharePlatform.Platform.Line)) {
            Log.e(TAG, "Line not installed");
            return;
        }
        FileHelper.detectFileUriExposure();
        FileHelper.saveBitmapToExternalSharePath(context, image);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");

        Uri uri = FileHelper.getExternalSharePathFileUris(context).get(0);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage("jp.naver.line.android");
        context.startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void shareImageR(Context context, Bitmap image) {
        if (!PlatformHelper.isInstalled(context, SharePlatform.Platform.Line)) {
            Log.e(TAG, "Line not installed");
            return;
        }
        FileHelper.detectFileUriExposure();
        Uri uri = FileHelper.saveImageToGallery(context, image);
        if (uri == null) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage("jp.naver.line.android");
        context.startActivity(intent);
    }
}
