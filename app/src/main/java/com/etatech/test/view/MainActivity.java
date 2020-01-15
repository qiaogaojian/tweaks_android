package com.etatech.test.view;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityMainBinding;
import com.etatech.test.utils.ClickUtil;
import com.etatech.test.view.AdaptHeightActivity;
import com.etatech.test.view.AdaptWidthActivity;
import com.etatech.test.view.TestTranslationActivity;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.vm.TestMvpVM;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;


import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener {
    private Button btnAdaptWidth;
    private Button btnAdaptHeight;
    private Button btnTestTranslation;
    private Button btnTestSurfaceview;
    private Button btnTestDataBinding;

    private Intent intent;

    @Override
    public ActivityMainBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void init() {
        intent = new Intent();

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

        RxView.clicks(binding.btnTestMvp)
                .compose(this.<Void>bindUntilEvent(ActivityEvent.DESTROY))
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        intent.setClass(MainActivity.this, TestMvpActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });

        ClickUtil.setOnClick(binding.btnTestAudio, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestAudioActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
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
