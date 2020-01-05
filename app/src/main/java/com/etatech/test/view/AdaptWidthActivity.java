package com.etatech.test.view;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.utils.BaseActivity;

public class AdaptWidthActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapt_width);
    }

    @Override
    public ViewDataBinding onCreateView(Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void init() {

    }
}
