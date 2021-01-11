package com.etatech.test.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.etatech.test.R;
import com.etatech.test.adapter.AnimationAdapter;
import com.etatech.test.databinding.ActivityTestAnimationBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.utils.ui.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class TestAnimationActivity extends BaseActivity<ActivityTestAnimationBinding> {

    @Override
    public ActivityTestAnimationBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestAnimationActivity.this, R.layout.activity_test_animation);
    }

    @Override
    public void init() {

        ClickUtil.setOnClick(binding.btnTranslation, new Action1() {
            @Override
            public void call(Object o) {
                AnimatorSet rotationAni = new AnimatorSet();
                ObjectAnimator ani1 = ObjectAnimator.ofFloat(binding.ivAnimation, View.TRANSLATION_Y,100);
                ObjectAnimator ani2 = ObjectAnimator.ofFloat(binding.ivAnimation,View.ROTATION,0,25);
                rotationAni.setDuration(1000);
                rotationAni.playTogether(ani1,ani2);
                rotationAni.start();
            }
        });
        ClickUtil.setOnClick(binding.btnRotation, new Action1() {
            @Override
            public void call(Object o) {
                AnimatorSet rotationAni = new AnimatorSet();
                ObjectAnimator ani1 = ObjectAnimator.ofFloat(binding.ivAnimation, View.ROTATION_Y,180);
                ObjectAnimator ani2 = ObjectAnimator.ofFloat(binding.ivAnimation,View.ROTATION,25,0);
                rotationAni.setDuration(1000);
                rotationAni.playTogether(ani1,ani2);
                rotationAni.start();
            }
        });
        ClickUtil.setOnClick(binding.btnRotationy, new Action1() {
            @Override
            public void call(Object o) {
                binding.ivAnimation.animate().setDuration(1000).rotationY(180).start();
            }
        });
        binding.recycleAnimation.setItemAnimator(null); // 取消 recyclerView notifyItemChanged 时的默认动画 解决动画覆盖问题
        binding.recycleAnimation.setLayoutManager(new GridLayoutManager(this,6));
        int spanCount = 6;
        int offsetY = -AdaptScreenUtils.pt2Px(37);
        binding.recycleAnimation.addItemDecoration(new GridSpacingItemDecoration(spanCount, offsetY));

        AnimationAdapter adapter = new AnimationAdapter();
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            data.add(0);
        }
        adapter.setCardStates(data);
        adapter.setRv(binding.recycleAnimation);
        binding.recycleAnimation.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
