package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityLeakInnerClassBinding;
import com.etatech.test.utils.BaseActivity;

public class LeakInnerClassActivity extends BaseActivity<ActivityLeakInnerClassBinding> {


    @Override
    public ActivityLeakInnerClassBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this,R.layout.activity_leak_inner_class);
    }

    @Override
    public void init() {

    }
}
