package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/11/10
 * Desc:
 */
public class Demo5View extends View {
    private Paint paint;
    private Bitmap bitmapBird;
    private Point centerPos;
    private Rect src; // 绘制区域
    private Rect dst; // 显示区域
    private int curFrame;
    private int width;
    private int height;
    private ObjectAnimator frameAni;

    public int getCurFrame() {
        return curFrame;
    }

    public void setCurFrame(int curFrame) {
        this.curFrame = curFrame;
        postInvalidate();
    }

    public Demo5View(Context context) {
        super(context);
        init();
    }

    public Demo5View(Context context, @Nullable AttributeSet attrs) {
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
        bitmapBird = BitmapFactory.decodeResource(getResources(), R.drawable.bird);

        width = bitmapBird.getWidth() / 4;
        height = bitmapBird.getHeight() / 2;
        dst = new Rect(centerPos.x - width, centerPos.y - height, centerPos.x + width, centerPos.y + height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = curFrame % 4;
        int y = curFrame / 4;
        src = new Rect(width * x, height * y, width * (x + 1), height * (y + 1));
        canvas.drawBitmap(bitmapBird, src, dst, paint);
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
        frameAni = ObjectAnimator.ofInt(this, "curFrame", 0, 7);
        frameAni.setRepeatCount(-1);
        frameAni.setRepeatMode(ValueAnimator.RESTART);
        frameAni.setInterpolator(new LinearInterpolator());
        frameAni.setDuration(500);
        frameAni.start();
    }

    private void endAni() {
        frameAni.end();
    }
}
