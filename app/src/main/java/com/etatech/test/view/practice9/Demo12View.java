package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;

import com.etatech.test.R;
import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/11/14
 * Desc:
 */
public class Demo12View extends ViewGroup {
    private int mStartScreen = 1;
    private float resistance = 1.8f;
    private Scroller mScroller;
    private float mAngle = 90;
    private boolean isCan3D = true;

    private Context mContext;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private Camera mCamera;
    private Matrix mMatrix;

    private Paint paint;
    private Point centerPos;
    private ObjectAnimator ani;

    private int mWidth;
    private int mHeight;
    private static final int standerSpeed = 2000;
    private static final int filingSpeed = 800;
    private int addCount;
    private int alreadyAdd = 0;
    private boolean isAdding = false;
    private int mCurScreen = 1;
    private float mDownX, mDownY, mTempY;
    private boolean isSliding = false;

    public Demo12View(Context context) {
        super(context);
        init(context);
    }

    public Demo12View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        centerPos = new Point();

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mCamera = new Camera();
        mMatrix = new Matrix();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerPos.set(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getVisibility() != GONE) {
                measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            }
        }
        scrollTo(0, mStartScreen * mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.layout(0, childTop, child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
                childTop = childTop + child.getMeasuredHeight();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos.set(w / 2, h / 2);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!isAdding) {
            for (int i = 0; i < getChildCount(); i++) {
                drawScreen(canvas, i, getDrawingTime());
            }
        } else {
            isAdding = false;
            super.dispatchDraw(canvas);
        }
    }

    private void drawScreen(Canvas canvas, int i, long drawingTime) {
        int curScreenY = mHeight * i;
        int scrollY = getScrollY();

        float centerX = mWidth / 2;
        float centerY = (scrollY > curScreenY) ? curScreenY + mHeight : curScreenY;
        float degree = mAngle * (scrollY - curScreenY) / mHeight;

        canvas.save();
        mCamera.save();

        mCamera.rotateX(degree);
        mCamera.getMatrix(mMatrix);
        mMatrix.preTranslate(-centerX, -centerY);
        mMatrix.postTranslate(centerX, centerY);
        canvas.concat(mMatrix);

        drawChild(canvas, getChildAt(i), getDrawingTime());

        mCamera.restore();
        canvas.restore();
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
