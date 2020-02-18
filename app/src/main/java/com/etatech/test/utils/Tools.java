package com.etatech.test.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by Michael
 * Date:  2020/2/18
 * Func:
 */
public class Tools {

    public static <T extends ViewModel> T getViewModel(BaseActivity appCompatActivity, @NonNull Class<T> modelClass) {
        if (appCompatActivity.getSupportFragmentManager().isDestroyed()) {
            LogUtils.e("getViewModel", "isDestroyed");
        }
        return ViewModelProviders.of(appCompatActivity).get(modelClass);
    }

    public static <T extends ViewModel> T getViewModel(Fragment appCompatActivity, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(appCompatActivity).get(modelClass);
    }
}
