package com.etatech.test.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.etatech.test.R;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.view.custom.FloatingView;

import rx.functions.Action1;

/**
 * Created by Michael
 * Data: 2020/1/23 15:27
 * Desc: 测试悬浮弹窗
 */

public class TestFloat extends FloatingView {

    public static final int       VIEW_WIDTH  = 160;
    public static final int       VIEW_HEIGHT = 160;
    private             ImageView ivImg;
    private             ImageView ivClose;

    public TestFloat(@NonNull Context context) {
        super(context);
    }

    @Override
    protected Object setContentView() {
        return R.layout.float_test;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final View decor, WindowManager.LayoutParams layoutParams) {
        super.onCreate(decor, layoutParams);
        ivImg = ((ImageView) findViewById(R.id.iv_img));
        ivClose = ((ImageView) findViewById(R.id.iv_close));

        ClickUtil.setOnClick(ivImg, new Action1() {
            @Override
            public void call(Object o) {
                ViewGroup.LayoutParams params = decor.getLayoutParams();
                params.width = AdaptScreenUtils.pt2Px(580);
                decor.setLayoutParams(params);
                ivClose.setVisibility(View.VISIBLE);
            }
        });
        ClickUtil.setOnClick(ivClose, new Action1() {
            @Override
            public void call(Object o) {
                dismiss();
            }
        });
        setCanMove(true);
    }

    @Override
    protected int setHeight() {
        return AdaptScreenUtils.pt2Px(VIEW_HEIGHT);
    }

    @Override
    protected int setWidth() {
        return AdaptScreenUtils.pt2Px(VIEW_WIDTH);
    }
}