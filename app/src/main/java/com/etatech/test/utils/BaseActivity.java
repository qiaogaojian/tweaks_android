package com.etatech.test.utils;

import android.content.Context;
import android.content.res.Resources;

import androidx.databinding.ViewDataBinding;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import android.view.MotionEvent;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.SPUtils;
import com.sdbean.localize.MultiLanguage;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

/**
 * Created by Michael
 * Date:  2019/12/27
 * Func:
 */
public abstract class BaseActivity<DataBindingType extends ViewDataBinding> extends RxAppCompatActivity implements LifecycleOwner {
    public DataBindingType binding;
    private boolean darkModeOpen;

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
        if (App.getInstance().isPortrait) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 1920);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {

                break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguage.getLocalContext(newBase));
    }
}
