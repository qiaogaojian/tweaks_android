package com.etatech.test.view;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;

import com.blankj.utilcode.util.SPUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestDarkModeBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class TestDarkModeActivity extends BaseActivity<ActivityTestDarkModeBinding> {
    private boolean darkModeOpen;

    @Override
    public ActivityTestDarkModeBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_dark_mode);
    }

    @Override
    public void init() {
        darkModeOpen = SPUtils.getInstance().getBoolean("dark_mode_open", false);
        setDarkMode(darkModeOpen);
        ClickUtil.setOnClick(binding.btnSwitch, new Action1() {
            @Override
            public void call(Object o) {
                darkModeOpen = !darkModeOpen;
                setDarkMode(darkModeOpen);
                finish();
                Intent intent = new Intent(TestDarkModeActivity.this, TestDarkModeActivity.this.getClass());
                startActivity(intent);
                overridePendingTransition(0, 0);

                SPUtils.getInstance().put("dark_mode_open", darkModeOpen);
                if (darkModeOpen) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    private void setDarkMode(boolean isOpen) {
        if (isOpen) {
            binding.btnSwitch.setImageDrawable(getResources().getDrawable(R.drawable.switch_bg_on));
            binding.tvDarkSwitch.setText("夜间模式");
        } else {
            binding.btnSwitch.setImageDrawable(getResources().getDrawable(R.drawable.switch_bg_off));
            binding.tvDarkSwitch.setText("白天模式");
        }
    }
}
