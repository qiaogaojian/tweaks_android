package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestCustomViewBinding;
import com.etatech.test.utils.BaseActivity;

public class TestCustomViewActivity extends BaseActivity<ActivityTestCustomViewBinding> {
    @Override
    public ActivityTestCustomViewBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this,R.layout.activity_test_custom_view);
    }

    @Override
    public void init() {

    }
}
