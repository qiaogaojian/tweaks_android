package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityLeakStaticViewBinding;
import com.etatech.test.utils.BaseActivity;

public class LeakStaticViewActivity extends BaseActivity<ActivityLeakStaticViewBinding> {

    @Override
    public ActivityLeakStaticViewBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this,R.layout.activity_leak_static_view);
    }

    @Override
    public void init() {

    }
}
