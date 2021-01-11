package com.etatech.test.view;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestLeakBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class TestLeakActivity extends BaseActivity<ActivityTestLeakBinding> {


    @Override
    public ActivityTestLeakBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_leak);
    }

    @Override
    public void init() {
        final Intent intent = new Intent();

        ClickUtil.setOnClick(binding.btnStaticActivity, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(getContext(), LeakStaticActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnStaticView, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(getContext(), LeakStaticViewActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnInnerClass, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(getContext(), LeakInnerClassActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnAnonymousClass, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(getContext(), LeakAnonymousClassActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnHandler, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(getContext(), LeakHandlerActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnThread, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(getContext(), LeakThreadActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTimerTask, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(getContext(), LeakTimerTaskActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnSensorManager, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(getContext(), LeakSensorManagerActivity.class);
                startActivity(intent);
            }
        });
    }

    private Context getContext() {
        return this;
    }
}
