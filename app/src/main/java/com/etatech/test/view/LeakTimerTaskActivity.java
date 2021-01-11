package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityLeakTimerTaskBinding;
import com.etatech.test.utils.BaseActivity;

public class LeakTimerTaskActivity extends BaseActivity<ActivityLeakTimerTaskBinding> {

    @Override
    public ActivityLeakTimerTaskBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_leak_timer_task);
    }

    @Override
    public void init() {

    }
}
