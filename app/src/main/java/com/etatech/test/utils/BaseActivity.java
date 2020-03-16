package com.etatech.test.utils;

import android.content.res.Resources;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by Michael
 * Date:  2019/12/27
 * Func:
 */
public abstract class BaseActivity<DataBindingType extends ViewDataBinding> extends RxAppCompatActivity {
    public  DataBindingType binding;
    private boolean         darkModeOpen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = onCreateView(savedInstanceState);
        darkModeOpen = SPUtils.getInstance().getBoolean("dark_mode_open", false);
        init();
    }

    public abstract DataBindingType onCreateView(Bundle savedInstanceState);

    public abstract void init();

    private Resources resources;

    // 完美像素适配 pt
    @Override
    public Resources getResources() {
        if (App.getInstance().isWide) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 1920);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean darkModeState = SPUtils.getInstance().getBoolean("dark_mode_open", false);
        if (darkModeState != darkModeOpen) {
            recreate();
        }
    }
}
