package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityLeakThreadBinding;
import com.etatech.test.utils.BaseActivity;

public class LeakThreadActivity extends BaseActivity<ActivityLeakThreadBinding> {

    @Override
    public ActivityLeakThreadBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_leak_thread);
    }

    @Override
    public void init() {

    }
}
