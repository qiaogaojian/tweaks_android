package com.etatech.test.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/3/10
 * Func:
 */
public class ClipConstraintLayout extends ConstraintLayout {

    private final float   tan16     = 0.2867453857588079f;
    private       Region  region;
    private       Path    path;
    private       int     mViewWidth;
    private       int     mViewHeight;
    private       int     padding;
    private       boolean isPadding = false;


    public ClipConstraintLayout(Context context) {
        this(context, null);
    }

    public ClipConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.ClipConstraintLayout);
        isPadding = arr.getBoolean(R.styleable.ClipConstraintLayout_hasPadding, isPadding);
        path = new Path();
        region = new Region();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        padding = (int) (w * tan16);
        path.moveTo(0, padding);
        path.lineTo(w, 0);
        path.lineTo(w, h - padding);
        path.lineTo(0, h);
        path.lineTo(0, padding);

        region.setPath(path, new Region(-w, -h, w, h));
        if (isPadding) {
            setPadding(0, padding, 0, padding);
        }
    }

    public int getPadding() {
        return padding;
    }

    public int getmViewWidth() {
        return mViewWidth;
    }

    public int getmViewHeight() {
        return mViewHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                if (region.contains(x, y)) {
                    performClick();
                }
                break;
        }
        return true;
    }
}
