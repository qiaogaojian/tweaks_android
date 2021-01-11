package com.etatech.test.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityLeakStaticViewBinding;
import com.etatech.test.utils.BaseActivity;

public class LeakStaticViewActivity extends BaseActivity<ActivityLeakStaticViewBinding> {

    private static ImageView ivLena;

    @Override
    public ActivityLeakStaticViewBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_leak_static_view);
    }

    @Override
    public void init() {
        Glide.with(this)
                .load(R.drawable.image_lena)
                .into(binding.ivStaticView);

        ivLena = binding.ivStaticView;
        PropertyValuesHolder scaleAniX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1F, 2F, 1F);
        PropertyValuesHolder scaleAniY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1F, 2F, 1F);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(binding.ivStaticView, scaleAniX, scaleAniY);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ivLena = null;
    }
}
