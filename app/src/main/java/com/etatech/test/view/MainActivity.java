package com.etatech.test.view;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.etatech.test.R;
import com.etatech.test.view.AdaptHeightActivity;
import com.etatech.test.view.AdaptWidthActivity;
import com.etatech.test.view.TestTranslationActivity;
import com.etatech.test.utils.BaseActivity;


import android.content.Intent;
import android.content.res.Resources;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity implements View.OnClickListener
{
    private Button btnAdaptWidth;
    private Button btnAdaptHeight;
    private Button btnTestTranslation;
    private Button btnTestSurfaceview;
    private Button btnTestDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdaptWidth = (Button) findViewById(R.id.btn_adapt_width);
        btnAdaptHeight = (Button) findViewById(R.id.btn_adapt_height);
        btnTestTranslation = (Button) findViewById(R.id.btn_test_translation);
        btnTestSurfaceview = (Button) findViewById(R.id.btn_test_surfaceview);
        btnTestDataBinding = (Button) findViewById(R.id.btn_test_databinding);
        btnAdaptWidth.setOnClickListener(this);
        btnAdaptHeight.setOnClickListener(this);
        btnTestTranslation.setOnClickListener(this);
        btnTestSurfaceview.setOnClickListener(this);
        btnTestDataBinding.setOnClickListener(this);
    }

    @Override
    public ViewDataBinding onCreateView(Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 1080);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_adapt_width:
                intent.setClass(this, AdaptWidthActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_adapt_height:
                intent.setClass(this, AdaptHeightActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_test_translation:
                intent.setClass(this, TestTranslationActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_test_surfaceview:
                intent.setClass(this, TestSurfaceviewActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_test_databinding:
                intent.setClass(this, TestDataBindingActivity.class);
                this.startActivity(intent);
                break;
        }
    }
}
