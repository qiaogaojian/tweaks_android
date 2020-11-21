package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/11/20
 * Desc:
 */
public class Demo15View extends View {

    private Paint paint;
    private PointF centerPos;
    private ObjectAnimator ani;

    private int radius = Tools.pt2Px(100);
    private PointF circlePos;
    private Region circleRegion;
    private Path circlePath;

    private ScaleGestureDetector mScaleDetector;
    private boolean mIsScaling;

    public Demo15View(Context context) {
        super(context);
        init();
    }

    public Demo15View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#F7F7F0"));
        centerPos = new PointF();
        initScaleGestureDetector();
    }

    private void initScaleGestureDetector() {
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                radius = (int) (radius * detector.getScaleFactor());
                circlePos.set(detector.getFocusX(), detector.getFocusY());
                refreshCirclePath();
                invalidate();
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                canDrag = false;
                mIsScaling = true;
                return super.onScaleBegin(detector);
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                super.onScaleEnd(detector);
                mIsScaling = false;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerPos.set(widthMeasureSpec / 2, heightMeasureSpec / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos.set(w / 2, h / 2);
        circlePos = centerPos;
        circlePath = new Path();
        refreshCirclePath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(circlePos.x, circlePos.y, radius, paint);
    }

    PointF lastPos = new PointF();
    PointF lastCirclePos = new PointF();
    PointF curPos = new PointF();
    private boolean canDrag = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (circleRegion.contains((int) event.getX(), (int) event.getY()) || mIsScaling) {
            mScaleDetector.onTouchEvent(event);
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int i = event.getPointerId(event.getActionIndex());
                if (i == 0 && circleRegion.contains((int) event.getX(), (int) event.getY())) {
                    canDrag = true;
                }
                lastPos.set(event.getX(), event.getY());
                lastCirclePos.set(circlePos.x, circlePos.y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (canDrag) {
                    int index = event.findPointerIndex(0);
                    curPos.set(event.getX(index), event.getY(index));
                    PointF offset = new PointF(curPos.x - lastPos.x, curPos.y - lastPos.y);
                    circlePos.set(lastCirclePos.x + offset.x, lastCirclePos.y + offset.y);
                    refreshCirclePath();
                    invalidate();
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    canDrag = false;
                }
                break;
        }
        return true;
    }

    private void refreshCirclePath() {
        circlePath.reset();
        circlePath.addCircle(circlePos.x, circlePos.y, radius, Path.Direction.CW);
        circleRegion = new Region();
        circleRegion.setPath(circlePath, new Region((int) circlePos.x - radius, (int) circlePos.y - radius, (int) circlePos.x + radius, (int) circlePos.y + radius));

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
