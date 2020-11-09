package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/11/7
 * Desc:
 */
public class BezierHeartView extends View {
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

    private float offsetTopY;
    private float offsetX;
    private float offsetBottomY;

    private Path path = new Path();
    private float len = Tools.pt2Px(200);
    private float cLen = len * 0.551915024494f;
    private int pw = Tools.pt2Px(6);
    private float progress = 0;
    private ObjectAnimator ani;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public BezierHeartView(Context context) {
        super(context);
        init();
    }

    public BezierHeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierHeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        ani.end();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new PointF(w / 2.0f, h / 2.0f);

        // 右下
        pointRB1 = new PointF(centerPos.x + 0, centerPos.y + len);
        pointRB2 = new PointF(centerPos.x + cLen, centerPos.y + len);
        pointRB3 = new PointF(centerPos.x + len, centerPos.y + cLen);
        pointRB4 = new PointF(centerPos.x + len, centerPos.y + 0);
        // 右上
        pointRT1 = new PointF(centerPos.x + 0, centerPos.y - len);
        pointRT2 = new PointF(centerPos.x + cLen, centerPos.y - len);
        pointRT3 = new PointF(centerPos.x + len, centerPos.y - cLen);
        pointRT4 = new PointF(centerPos.x + len, centerPos.y - 0);
        // 左下
        pointLB1 = new PointF(centerPos.x - 0, centerPos.y + len);
        pointLB2 = new PointF(centerPos.x - cLen, centerPos.y + len);
        pointLB3 = new PointF(centerPos.x - len, centerPos.y + cLen);
        pointLB4 = new PointF(centerPos.x - len, centerPos.y + 0);
        // 左上
        pointLT1 = new PointF(centerPos.x - 0, centerPos.y - len);
        pointLT2 = new PointF(centerPos.x - cLen, centerPos.y - len);
        pointLT3 = new PointF(centerPos.x - len, centerPos.y - cLen);
        pointLT4 = new PointF(centerPos.x - len, centerPos.y - 0);

        offsetTopY = len * 0.7f;
        offsetX = len * 0.12f;
        offsetBottomY = -len * 0.38f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.reset();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(pw);

        path.reset();
        // 右下
        path.moveTo(pointRB1.x, pointRB1.y);
        path.cubicTo(pointRB2.x, pointRB2.y + offsetBottomY * progress, pointRB3.x - offsetX * progress, pointRB3.y, pointRB4.x, pointRB4.y);
        // 右上
        path.moveTo(pointRT1.x, pointRT1.y + offsetTopY * progress);
        path.cubicTo(pointRT2.x, pointRT2.y, pointRT3.x, pointRT3.y, pointRT4.x, pointRT4.y);
        // 左下
        path.moveTo(pointLB1.x, pointLB1.y);
        path.cubicTo(pointLB2.x, pointLB2.y + offsetBottomY * progress, pointLB3.x + offsetX * progress, pointLB3.y, pointLB4.x, pointLB4.y);
        // 左上
        path.moveTo(pointLT1.x, pointLT1.y + offsetTopY * progress);
        path.cubicTo(pointLT2.x, pointLT2.y, pointLT3.x, pointLT3.y, pointLT4.x, pointLT4.y);
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


    private void startAni() {
        ani = ObjectAnimator.ofFloat(this, "progress", 0, 1f);
        ani.setRepeatMode(ValueAnimator.RESTART);
        ani.setRepeatCount(-1);
        ani.setDuration(6000);
        ani.start();
    }
}
