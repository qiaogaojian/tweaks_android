package com.etatech.test.utils;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Environment;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import android.text.Spanned;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.etatech.test.netstate.NetWorkMonitorManager;
import com.sdbean.localize.MultiLanguage;
import com.tencent.rtmp.TXLiveBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael
 * Date:  2019/12/27
 * Func:
 */
public class App extends MultiDexApplication {

    public final float designRatio = 16f / 9f;
    private static App instance;
    private Typeface typeface;
    public boolean isWide;
    public boolean isPortrait = true;
    public int screenHeight;
    public int screenWidth;
    public static String filesPath;
    public static String externalPath;
    public static List<Spanned> logArr;
    public static String RES_PATH = "";

    public static App getInstance() {
        return instance;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RES_PATH = getFilesDir().getAbsolutePath();
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

        initTxLive();
        NetWorkMonitorManager.getInstance().init(this);
        // 设置系统语言
        MultiLanguage.setApplicationLanguage(this);
    }

    private void initTxLive(){
        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/b56a5afde8826ac55c32ee4837a1f5a7/TXLiveSDK.licence";
        String licenceKey = "c8e2d9d1b40330c6ec4d36b9ec7a089a";
        TXLiveBase.getInstance().setLicence(this,licenceURL,licenceKey);
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

    @Override
    protected void attachBaseContext(Context base)
    {
        // 保存系统语言
        MultiLanguage.saveSystemCurrentLanguage(base);

        super.attachBaseContext(MultiLanguage.setLocal(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        // 保存系统语言
        MultiLanguage.onConfigurationChanged(getApplicationContext(),newConfig);
    }
}
