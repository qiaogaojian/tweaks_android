package com.etatech.test.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.etatech.test.R;

public class MaskableFrameLayout extends FrameLayout {
    private final float TAN15 = 0.2679491924311227f;
    private boolean isHorizon = false;
    private int sidePadding = 0;
    private Path path = new Path();

    public MaskableFrameLayout(Context context) {
        super(context);
    }

    public MaskableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.MaskLayout);
        sidePadding = arr.getInteger(R.styleable.MaskLayout_side_padding, sidePadding);
        isHorizon = arr.getBoolean(R.styleable.MaskLayout_isHorizon, isHorizon);
        arr.recycle();
    }

    //Drawing
    @Override
    protected void dispatchDraw(Canvas canvas) {
        path.reset();
        int h = getMeasuredHeight();
        int w = getMeasuredWidth();

        if (isHorizon) {
            path.moveTo(h * TAN15, 0);
            path.lineTo(w, 0);
            path.lineTo(w - h * TAN15, h);
            path.lineTo(0, h);
        } else {
            path.moveTo(0, (w - sidePadding) * TAN15);
            path.lineTo(w - sidePadding, 0);
            path.lineTo(w - sidePadding, h - (w - sidePadding) * TAN15);
            path.lineTo(0, h);
        }

        path.close();
        canvas.clipPath(path);

        super.dispatchDraw(canvas);
    }
}
