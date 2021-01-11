package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityLeakAnonymousClassBinding;
import com.etatech.test.utils.BaseActivity;

public class LeakAnonymousClassActivity extends BaseActivity<ActivityLeakAnonymousClassBinding> {

    @Override
    public ActivityLeakAnonymousClassBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_leak_anonymous_class);
    }

    @Override
    public void init() {

    }
}
