package com.etatech.test.utils;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Environment;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import android.os.Handler;
import android.os.Looper;
import android.text.Spanned;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.etatech.test.BuildConfig;
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
    public static final String licenceURL = "http://license.vod2.myqcloud.com/license/v1/b56a5afde8826ac55c32ee4837a1f5a7/TXLiveSDK.licence";
    public static final String licenceKey = "c8e2d9d1b40330c6ec4d36b9ec7a089a";

    public static App getInstance() {
        return instance;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 必须在初始化ARouter之前配置
        if (BuildConfig.DEBUG) {
            // 日志开启
            ARouter.openLog();
            // 调试模式开启，如果在install run模式下运行，则必须开启调试模式
            ARouter.openDebug();
        }
        ARouter.init(this);

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

    private void initTxLive() {
        TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);

        // 打印 licence 信息
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("TXLive Licence", "onCreate: " + TXLiveBase.getInstance().getLicenceInfo(App.this));
            }
        }, 5 * 1000);//5秒后打印 Licence 的信息
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
    protected void attachBaseContext(Context base) {
        // 保存系统语言
        MultiLanguage.saveSystemCurrentLanguage(base);

        super.attachBaseContext(MultiLanguage.setLocal(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 保存系统语言
        MultiLanguage.onConfigurationChanged(getApplicationContext(), newConfig);
    }
}
