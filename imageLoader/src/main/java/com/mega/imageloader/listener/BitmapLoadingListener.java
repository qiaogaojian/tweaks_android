package com.mega.imageloader.listener;

import android.graphics.Bitmap;

public interface BitmapLoadingListener {
    void onSuccess(Bitmap b);
    void onError();
}