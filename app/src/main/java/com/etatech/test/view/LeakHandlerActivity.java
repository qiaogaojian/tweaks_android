package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
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
