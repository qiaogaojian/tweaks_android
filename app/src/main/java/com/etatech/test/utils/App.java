package com.etatech.test.utils;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Debug;
import android.util.Log;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;

/**
 * Created by Michael
 * Date:  2019/12/27
 * Func:
 */
public class App extends Application {

    public final   float    designRatio = 16f / 9f;
    private static App      instance;
    private        Typeface typeface;
    public         boolean  isWide;
    public         int      screenHeight;
    public         int      screenWidth;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        typeface = Typeface.createFromAsset(getAssets(), "fonts/gen_shin_gothic.ttf");

        screenHeight = ScreenUtils.getScreenHeight();
        screenWidth = ScreenUtils.getScreenWidth();
        if (screenHeight / (float) screenWidth >= designRatio) {
            isWide = false;
            LogUtils.i(String.format("The App Screen Height %d Width %d ", screenHeight, screenWidth));
        } else {
            isWide = true;
            LogUtils.i(String.format("The App Screen Height %d Width %d It's Wide", screenHeight, screenWidth));
        }

    }

    public static App getInstance() {
        return instance;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        try {
            if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
                Glide.get(this).clearMemory();
            }
            Glide.get(this).trimMemory(level);
        } catch (Exception e) {

        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            Glide.get(this).clearMemory();
        } catch (Exception e) {

        }
    }
}
