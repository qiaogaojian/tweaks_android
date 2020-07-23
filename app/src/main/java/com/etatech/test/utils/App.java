package com.etatech.test.utils;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Debug;
import android.os.Environment;
import android.support.v7.app.AppCompatDelegate;
import android.text.Spanned;
import android.util.Log;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.etatech.test.netstate.NetWorkMonitorManager;

import java.util.ArrayList;
import java.util.List;

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
    public  static String   filesPath;
    public  static String   externalPath;
    public  static List<Spanned> logArr;

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
        if (SPUtils.getInstance().getBoolean("dark_mode_open", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        filesPath = getFilesDir().getAbsolutePath();
        externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        logArr = new ArrayList<>();

        NetWorkMonitorManager.getInstance().init(this);
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
