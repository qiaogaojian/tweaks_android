package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestSvgBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

/**
 * Created by Michael
 * Date:  2020/2/13
 * Func: SVG pathData详解 参考文档:https://blog.csdn.net/zwlove5280/article/details/73196543
 */
public class TestSvgActivity extends BaseActivity<ActivityTestSvgBinding> {
    private boolean isMenu = true; // true menu false back

    @Override
    public ActivityTestSvgBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_svg);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnStart, new Action1() {
            @Override
            public void call(Object o) {
                if (isMenu) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(TestSvgActivity.this, R.drawable.menu));
                        ((AnimatedVectorDrawable) binding.ivMenu.getDrawable()).start();
                    } else {
                        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(TestSvgActivity.this, R.drawable.ic_back));
                    }
                    binding.btnStart.setText("Back");
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(TestSvgActivity.this, R.drawable.back));
                        ((AnimatedVectorDrawable) binding.ivMenu.getDrawable()).start();
                    } else {
                        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(TestSvgActivity.this, R.drawable.ic_menu));
                    }
                    binding.btnStart.setText("Menu");
                }
                isMenu = !isMenu;
            }
        });

        ClickUtil.setOnClick(binding.ivMenu, new Action1() {
            @Override
            public void call(Object o) {
                if (isMenu) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(TestSvgActivity.this, R.drawable.menu));
                        ((AnimatedVectorDrawable) binding.ivMenu.getDrawable()).start();
                    } else {
                        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(TestSvgActivity.this, R.drawable.ic_back));
                    }
                    binding.btnStart.setText("Back");
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(TestSvgActivity.this, R.drawable.back));
                        ((AnimatedVectorDrawable) binding.ivMenu.getDrawable()).start();
                    } else {
                        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(TestSvgActivity.this, R.drawable.ic_menu));
                    }
                    binding.btnStart.setText("Menu");
                }
                isMenu = !isMenu;
            }
        });
    }
}
