package com.etatech.test.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestDataBindingBinding;
import com.etatech.test.utils.BaseActivity;

public class TestDataBindingActivity extends BaseActivity<ActivityTestDataBindingBinding>
{
    @Override
    public ActivityTestDataBindingBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_data_binding);
    }

    @Override
    public void init() {
        ObjectAnimator transAni = ObjectAnimator.ofFloat(binding.ivHello, View.TRANSLATION_X, -200, 200);
        transAni.setDuration(3000);
        transAni.setRepeatCount(ObjectAnimator.INFINITE);
        transAni.setRepeatMode(ObjectAnimator.REVERSE);
        transAni.start();

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 2f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 2f, 1f);
        PropertyValuesHolder alpha  = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0.7f, 1f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(binding.tvHello, scaleX, scaleY, alpha);
        animator.setDuration(3000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.start();
    }
}
