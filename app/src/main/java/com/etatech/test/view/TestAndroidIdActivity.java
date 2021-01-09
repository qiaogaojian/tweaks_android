package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestAndroidIdBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class TestAndroidIdActivity extends BaseActivity<ActivityTestAndroidIdBinding> {

    @Override
    public ActivityTestAndroidIdBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestAndroidIdActivity.this,R.layout.activity_test_android_id);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnTestAndroidId, new Action1() {
            @Override
            public void call(Object o) {
                String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                binding.tvLog.setText(android_id);
            }
        });
    }
}