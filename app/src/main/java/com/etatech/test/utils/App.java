package com.etatech.test.utils;

import android.app.Application;
import android.content.res.Resources;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;

/**
 * Created by Michael
 * Date:  2019/12/27
 * Func:
 */
public class App extends Application {

    // 如果是悬浮窗适配，因为 inflate 用到的 context 是 application 级别的，所以需要在自定义的 Application 中重写 getResource。
    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 1080);
        }
    }
}
