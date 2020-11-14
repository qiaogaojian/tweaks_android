package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Michael
 * Date:  2020/11/14
 * Desc:
 */
public class Demo12View extends View {

    private Paint paint;
    private Point centerPos;
    private ObjectAnimator ani;

    public Demo12View(Context context) {
        super(context);
        init();
    }

    public Demo12View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new Point(w / 2, h / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAni();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        endAni();
    }

    private void startAni() {

    }

    private void endAni() {
        if (ani != null) {
            ani.end();
        }
    }


}
