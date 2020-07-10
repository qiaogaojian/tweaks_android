package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ClickUtils;
import com.etatech.test.R;
import com.etatech.test.ResourcesHelper;
import com.etatech.test.databinding.ActivityTestObbBuildBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import java.io.IOException;

import rx.functions.Action1;

public class TestObbBuildActivity extends BaseActivity<ActivityTestObbBuildBinding> {

    @Override
    public ActivityTestObbBuildBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_obb_build);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnGetPath, new Action1() {
            @Override
            public void call(Object o) {
                binding.tvPath.setText(ResourcesHelper.getObbFilePath(TestObbBuildActivity.this));
            }
        });

        ClickUtil.setOnClick(binding.btnUnzip, new Action1() {
            @Override
            public void call(Object o) {
                ResourcesHelper.unZipObb(TestObbBuildActivity.this);
            }
        });
    }
}
