package com.etatech.test.utils;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Paint;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;

/**
 * Created by Michael
 * Date:  2019/12/27
 * Func:
 */
public class App extends Application {

    private static App _instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        _instance = (App) getApplicationContext();
    }

    public static App getInstance()
    {
        return _instance;
    }

    // 如果是悬浮窗适配，因为 inflate 用到的 context 是 application 级别的，所以需要在自定义的 Application 中重写 getResource。
//    @Override
//    public Resources getResources() {
//        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
//    }
}
