package com.mega.imageloader.listener;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;


public interface DrawableLoadingListener {
    void onSuccess(Drawable b);
    void onError();
}