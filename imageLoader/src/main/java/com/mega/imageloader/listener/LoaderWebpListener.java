package com.mega.imageloader.listener;

import android.graphics.drawable.Drawable;

public interface LoaderWebpListener {
    void onAnimationEnd(Drawable drawable);
    void onAnimationStart(Drawable drawable);
    void onAnimationFail();
}
