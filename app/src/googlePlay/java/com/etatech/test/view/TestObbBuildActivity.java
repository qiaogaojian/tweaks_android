package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.ResourcesHelper;
import com.etatech.test.databinding.ActivityTestObbBuildBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import com.etatech.test.utils.rxbus.Action1;

public class TestObbBuildActivity extends BaseActivity<ActivityTestObbBuildBinding> {

    @Override
    public ActivityTestObbBuildBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_obb_build);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnGetPath, new Action1() {
            @Override
            public void accept(Object o) {
                binding.tvPath.setText(ResourcesHelper.getObbFilePath(TestObbBuildActivity.this));
            }
        });

        ClickUtil.setOnClick(binding.btnUnzip, new Action1() {
            @Override
            public void accept(Object o) {
                ResourcesHelper.unZipObb(TestObbBuildActivity.this);
            }
        });
    }
}
