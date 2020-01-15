package com.etatech.test.utils;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by Michael
 * Date:  2019/12/27
 * Func:
 */
public class App extends Application {

    private static App      instance;
    private        Typeface typeface;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = (App) getApplicationContext();

        typeface = Typeface.createFromAsset(getAssets(), "fonts/gen_shin_gothic.ttf");
    }

    public static App getInstance() {
        return instance;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    // 如果是悬浮窗适配，因为 inflate 用到的 context 是 application 级别的，所以需要在自定义的 Application 中重写 getResource。
//    @Override
//    public Resources getResources() {
//        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
//    }
}
