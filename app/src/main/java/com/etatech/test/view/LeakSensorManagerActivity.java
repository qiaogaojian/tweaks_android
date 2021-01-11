package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityLeakSensorManagerBinding;
import com.etatech.test.utils.BaseActivity;

public class LeakSensorManagerActivity extends BaseActivity<ActivityLeakSensorManagerBinding> {


    @Override
    public ActivityLeakSensorManagerBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_leak_sensor_manager);
    }

    @Override
    public void init() {

    }
}