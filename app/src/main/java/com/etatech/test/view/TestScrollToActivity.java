package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestScrollToBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class TestScrollToActivity extends BaseActivity<ActivityTestScrollToBinding> {
    public final int LEFT_TO_RIGHT = 1;
    public final int RIGHT_TO_LEFT = 2;
    private int scrollDirection = 1;    //滚动方向
    private int viewWidth;              //View总宽度
    private int screenWidth;            //屏幕宽度
    private float currentX;             //当前x坐标

    Thread thread = new Thread() {
        @Override
        public void run() {
            switch (scrollDirection) {
                case LEFT_TO_RIGHT:
                    binding.mainLayout.scrollTo((int) currentX, 0);
                    currentX -= 2;
                    if (-currentX >= screenWidth) {
                        binding.mainLayout.scrollTo(viewWidth, 0);
                        currentX = viewWidth;
                    }
                    break;
                case RIGHT_TO_LEFT:
                    binding.mainLayout.scrollTo((int) currentX, 0);
                    currentX = currentX + 2;
                    if (currentX >= viewWidth - AdaptScreenUtils.pt2Px(1080)) {
                        currentX = 0;
                        return;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public ActivityTestScrollToBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_scroll_to);
    }

    @Override
    public void init() {
        viewWidth = binding.mainLayout.getMeasuredWidth();

        ClickUtil.setFastClick(binding.btnTestScrollto, new Action1() {
            @Override
            public void call(Object o) {
                binding.mainLayout.scrollTo((int) currentX, 0);
                currentX = currentX + 30;
                if (currentX >= viewWidth + AdaptScreenUtils.pt2Px(1080)) {
                    currentX = 0;
                }
            }
        });
    }


}
