package com.etatech.test.view;

import android.content.res.Resources;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.blankj.utilcode.util.AdaptScreenUtils;
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

    }

    @Override
    public Resources getResources()
    {
        return AdaptScreenUtils.adaptHeight(super.getResources(), 1920);
    }
}
