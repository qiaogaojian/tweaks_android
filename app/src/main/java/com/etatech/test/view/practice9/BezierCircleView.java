package com.etatech.test.view.practice9;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/11/7
 * Desc:
 */
public class BezierCircleView extends View {
    private Paint paint;
    private PointF centerPos;
    private PointF pointRB1;
    private PointF pointRB2;
    private PointF pointRB3;
    private PointF pointRB4;

    private PointF pointRT1;
    private PointF pointRT2;
    private PointF pointRT3;
    private PointF pointRT4;

    private PointF pointLB1;
    private PointF pointLB2;
    private PointF pointLB3;
    private PointF pointLB4;

    private PointF pointLT1;
    private PointF pointLT2;
    private PointF pointLT3;
    private PointF pointLT4;

    private float offsetX;
    private float offsetY;
    private float offsetBottomY;

    private Path path = new Path();
    private float len = Tools.pt2Px(100);
    private float cLen = len * 0.551915024494f;
    private int pw = Tools.pt2Px(6);
    private float progress = 0;
    private float progressLeft = 0;
    private float progressTrans = 0;

    private ObjectAnimator ani1;
    private ObjectAnimator aniLeft;
    private ObjectAnimator ani2;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        // if (progress < -0.1f || progress > 1.1f) {
        //     return;
        // }
        // if (progress / 0.5f > 1) {
        //     this.progress = 1 - (progress / 0.5f - 1) - 0.1f;
        //     this.progressLeft = 1 - (progress / 0.5f - 1) - 0.1f;
        // } else {
        //     this.progress = progress / 0.5f - 0.1f;
        //     this.progressLeft = progress / 0.5f - 0.1f;
        // }
        // this.progressTrans = progress;
        postInvalidate();
    }

    public float getProgressLeft() {
        return progressLeft;
    }

    public void setProgressLeft(float progressLeft) {
        this.progressLeft = progressLeft;
        postInvalidate();
    }

    public float getProgressTrans() {
        return progressTrans;
    }

    public void setProgressTrans(float progressTrans) {
        this.progressTrans = progressTrans;
    }

    public BezierCircleView(Context context) {
        super(context);
        init();
    }

    public BezierCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        path = new Path();
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new PointF(w / 2.0f, h / 2.0f);

        // 右上
        pointRT1 = new PointF(len + 0, centerPos.y - len);
        pointRT2 = new PointF(len + cLen, centerPos.y - len);

        pointRT3 = new PointF(len + len, centerPos.y - cLen);
        pointRT4 = new PointF(len + len, centerPos.y - 0);
        // 右下
        pointRB1 = new PointF(len + 0, centerPos.y + len);
        pointRB2 = new PointF(len + cLen, centerPos.y + len);

        pointRB3 = new PointF(len + len, centerPos.y + cLen);
        pointRB4 = new PointF(len + len, centerPos.y + 0);
        // 左上
        pointLT1 = new PointF(len - 0, centerPos.y - len);
        pointLT2 = new PointF(len - cLen, centerPos.y - len);

        pointLT3 = new PointF(len - len, centerPos.y - cLen);
        pointLT4 = new PointF(len - len, centerPos.y - 0);
        // 左下
        pointLB1 = new PointF(len - 0, centerPos.y + len);
        pointLB2 = new PointF(len - cLen, centerPos.y + len);

        pointLB3 = new PointF(len - len, centerPos.y + cLen);
        pointLB4 = new PointF(len - len, centerPos.y + 0);

        offsetY = cLen * 0.5f;
        offsetX = len * 2;
        offsetBottomY = -len * 0.38f;
    }

    private void updatePos() {
        PointF temPos = new PointF(len + (centerPos.x - len) * 2 * progressTrans, centerPos.y);

        // 右上
        pointRT1 = new PointF(temPos.x + 0, temPos.y - len);
        pointRT2 = new PointF(temPos.x + cLen, temPos.y - len);
        pointRT3 = new PointF(temPos.x + len, temPos.y - cLen);
        pointRT4 = new PointF(temPos.x + len, temPos.y - 0);
        // 右下
        pointRB1 = new PointF(temPos.x + 0, temPos.y + len);
        pointRB2 = new PointF(temPos.x + cLen, temPos.y + len);
        pointRB3 = new PointF(temPos.x + len, temPos.y + cLen);
        pointRB4 = new PointF(temPos.x + len, temPos.y + 0);
        // 左上
        pointLT1 = new PointF(temPos.x - 0, temPos.y - len);
        pointLT2 = new PointF(temPos.x - cLen, temPos.y - len);
        pointLT3 = new PointF(temPos.x - len, temPos.y - cLen);
        pointLT4 = new PointF(temPos.x - len, temPos.y - 0);
        // 左下
        pointLB1 = new PointF(temPos.x - 0, temPos.y + len);
        pointLB2 = new PointF(temPos.x - cLen, temPos.y + len);
        pointLB3 = new PointF(temPos.x - len, temPos.y + cLen);
        pointLB4 = new PointF(temPos.x - len, temPos.y + 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.reset();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        updatePos();

        canvas.drawCircle(len, centerPos.y, len, paint);
        canvas.drawCircle(centerPos.x * 2 - len, centerPos.y, len, paint);

        path.reset();
        // 右上
        path.moveTo(pointRT1.x, pointRT1.y);
        path.cubicTo(pointRT2.x, pointRT2.y, pointRT3.x + offsetX * progress, pointRT3.y, pointRT4.x + offsetX * progress, pointRT4.y);

        // 右下
        path.cubicTo(pointRB3.x + offsetX * progress, pointRB3.y, pointRB2.x, pointRB2.y, pointRB1.x, pointRB1.y);

        // 左下
        path.cubicTo(pointLB2.x, pointLB2.y, pointLB3.x - offsetX * progressLeft, pointLB3.y, pointLB4.x - offsetX * progressLeft, pointLB4.y);

        // 左上
        path.cubicTo(pointLT3.x - offsetX * progressLeft, pointLT3.y, pointLT2.x, pointLT2.y, pointLT1.x, pointLT1.y);

        canvas.drawPath(path, paint);

        paint.setColor(Color.BLACK);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        path.reset();
        path.moveTo(0, centerPos.y);
        path.lineTo(centerPos.x * 2, centerPos.y);
        path.moveTo(centerPos.x, 0);
        path.lineTo(centerPos.x, centerPos.y * 2);
        canvas.drawPath(path, paint);
    }

    private int lastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        setProgress((x - len) / (centerPos.x - len) / 2.0f);
        return true;
    }

    private void startAni() {
        Keyframe frame1 = Keyframe.ofFloat(0, 0);
        Keyframe frame2 = Keyframe.ofFloat(0.1f, -0.1f);
        Keyframe frame3 = Keyframe.ofFloat(0.5f, 1f);
        Keyframe frame4 = Keyframe.ofFloat(0.9f, -0.1f);
        Keyframe frame5 = Keyframe.ofFloat(1f, 0);

        PropertyValuesHolder proper = PropertyValuesHolder.ofKeyframe("progress", frame1, frame2, frame3, frame4, frame5);
        ani1 = ObjectAnimator.ofPropertyValuesHolder(this, proper);
        ani1.setDuration(2000);
        ani1.setRepeatCount(-1);
        ani1.setRepeatMode(ValueAnimator.REVERSE);
        ani1.start();

        Keyframe frame21 = Keyframe.ofFloat(0, 0);
        Keyframe frame22 = Keyframe.ofFloat(0.1f, -0.1f);
        Keyframe frame23 = Keyframe.ofFloat(0.5f, 1f);
        Keyframe frame24 = Keyframe.ofFloat(0.9f, -0.1f);
        Keyframe frame25 = Keyframe.ofFloat(1f, 0);

        PropertyValuesHolder proper2 = PropertyValuesHolder.ofKeyframe("progressLeft", frame21, frame22, frame23, frame24, frame25);
        aniLeft = ObjectAnimator.ofPropertyValuesHolder(this, proper2);
        aniLeft.setDuration(2000);
        aniLeft.setRepeatCount(-1);
        aniLeft.setRepeatMode(ValueAnimator.REVERSE);
        aniLeft.start();

        ani2 = ObjectAnimator.ofFloat(this, "progressTrans", 0, 1f);
        ani2.setDuration(2000);
        ani2.setRepeatCount(-1);
        ani2.setRepeatMode(ValueAnimator.REVERSE);
        ani2.start();
    }

    private void endAni() {
        ani1.end();
        aniLeft.end();
        ani2.end();
    }
}