package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestMvvmBinding;
import com.etatech.test.utils.BaseActivity;

public class TestMvvmActivity extends BaseActivity<ActivityTestMvvmBinding> {

    @Override
    public ActivityTestMvvmBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_mvvm);
    }

    @Override
    public void init() {

    }
}
