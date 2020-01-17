package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityLeakHandlerBinding;
import com.etatech.test.utils.BaseActivity;

public class LeakHandlerActivity extends BaseActivity<ActivityLeakHandlerBinding> {


    @Override
    public ActivityLeakHandlerBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this,R.layout.activity_leak_handler);
    }

    @Override
    public void init() {

    }
}
