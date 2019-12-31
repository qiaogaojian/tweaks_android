package com.etatech.test.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael
 * Date:  2019/12/27
 * Func:
 */
public class BaseActivity extends AppCompatActivity
{
    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 1080);
        }
    }
}
