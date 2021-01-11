package com.etatech.test.view;

import android.content.res.Resources;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.etatech.test.R;
import com.etatech.test.utils.BaseActivity;

public class AdaptHeightActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapt_height);
    }

    @Override
    public ViewDataBinding onCreateView(Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void init() {
        int h = ScreenUtils.getScreenHeight();
        int w = ScreenUtils.getScreenWidth();
        ToastUtils.showShort(String.format("Screen Size: %d x %d", h, w));
    }

    @Override
    public Resources getResources()
    {
        return AdaptScreenUtils.adaptHeight(super.getResources(), 1920);
    }
}
