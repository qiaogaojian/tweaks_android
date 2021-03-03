package com.etatech.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestSpineBinding;
import com.etatech.test.spine.SpineBoyActivity;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class TestSpineActivity extends BaseActivity<ActivityTestSpineBinding> {

    @Override
    public ActivityTestSpineBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestSpineActivity.this, R.layout.activity_test_spine);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnSpineBoy, new Action1() {
            @Override
            public void call(Object o) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SpineBoyActivity.class);
                startActivity(intent);
            }
        });
    }

    private TestSpineActivity getContext() {
        return this;
    }
}