package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
 * Date:  2020/11/6
 * Desc:
 */
public class Bezier2View extends View {
    private Paint paint;
    private PointF centerPos;
    private PointF point1;
    private PointF point2;
    private PointF point3;
    private PointF pointLeft;
    private PointF pointMid;
    private PointF pointRight;
    private Path path = new Path();
    private int bl = Tools.pt2Px(100);
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

    public Bezier2View(Context context) {
        super(context);
        init();
    }

    public Bezier2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Bezier2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
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
        centerPos = new PointF(w / 2.0f, h / 2.0f + 100);
        point1 = new PointF(centerPos.x - bl * 2, centerPos.y);
        point2 = new PointF(centerPos.x, centerPos.y - bl * 3);
        point3 = new PointF(centerPos.x + bl * 2, centerPos.y);
        pointLeft = new PointF();
        pointRight = new PointF();
        pointMid = new PointF();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        point2.set(event.getX(), event.getY());
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float offsetLeftX = point2.x - point1.x;
        float offsetLeftY = point2.y - point1.y;
        float offsetRightX = point3.x - point2.x;
        float offsetRightY = point3.y - point2.y;
        pointLeft.set(point1.x + offsetLeftX * progress, point1.y + offsetLeftY * progress);
        pointRight.set(point2.x + offsetRightX * progress, point2.y + offsetRightY * progress);
        float offsetMidX = pointRight.x - pointLeft.x;
        float offsetMidY = pointRight.y - pointLeft.y;
        pointMid.set(pointLeft.x + offsetMidX * progress, pointLeft.y + offsetMidY * progress);

        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(pw);

        canvas.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
        canvas.drawLine(point2.x, point2.y, point3.x, point3.y, paint);

        canvas.drawRect(point1.x - pw, point1.y - pw, point1.x + pw, point1.y + pw, paint);
        canvas.drawRect(point2.x - pw, point2.y - pw, point2.x + pw, point2.y + pw, paint);
        canvas.drawRect(point3.x - pw, point3.y - pw, point3.x + pw, point3.y + pw, paint);

        paint.setColor(Color.RED);
        path.reset();  // Path 要记得 reset 初始化
        path.moveTo(point1.x, point1.y);
        path.quadTo(pointLeft.x, pointLeft.y, pointMid.x, pointMid.y);
        canvas.drawPath(path, paint);

        paint.setColor(Color.GREEN);
        canvas.drawLine(pointLeft.x, pointLeft.y, pointRight.x, pointRight.y, paint);
        canvas.drawCircle(pointLeft.x, pointLeft.y, pw, paint);
        canvas.drawCircle(pointRight.x, pointRight.y, pw, paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(pointMid.x, pointMid.y, pw * 1.5f, paint);


    }


    private void startAni() {
        ani = ObjectAnimator.ofFloat(this, "progress", 0, 1f);
        ani.setRepeatMode(ValueAnimator.RESTART);
        ani.setRepeatCount(-1);
        ani.setDuration(3000);
        ani.start();
    }
}
