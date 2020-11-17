package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;


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

    private Camera mCamera;
    private Matrix mMatrix;

    private int mWidth;
    private int mHeight;
    private boolean isAdding = false;
    private int mCurScreen = 1;
    private float mDownY;

    private State mState = State.Normal;

    public enum State {
        Normal, ToPre, ToNext
    }

    public Demo12View(Context context) {
        this(context, null);
    }

    public Demo12View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Demo12View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mCamera = new Camera();
        mMatrix = new Matrix();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        scrollTo(0, mStartScreen * mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.layout(0, childTop, child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
                childTop = childTop + child.getMeasuredHeight();
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
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

        if (scrollY + mHeight < curScreenY) {
            return;
        }
        if (curScreenY < getScrollY() - mHeight) {
            return;
        }

        float centerX = mWidth / 2;
        float centerY = (scrollY > curScreenY) ? curScreenY + mHeight : curScreenY;
        float degree = mAngle * (scrollY - curScreenY) / mHeight;
        if (degree > 90 || degree < -90) {
            return;
        }

        canvas.save();

        mCamera.save();
        mCamera.rotateX(degree);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-centerX, -centerY);
        mMatrix.postTranslate(centerX, centerY);
        canvas.concat(mMatrix);

        drawChild(canvas, getChildAt(i), drawingTime);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                int realDelta = (int) (mDownY - y);
                mDownY = y;
                if (mScroller.isFinished()) {
                    recycleMove(realDelta);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (((getScrollY() + mHeight / 2) / mHeight < mStartScreen)) {
                    mState = State.ToPre;
                } else if (((getScrollY() + mHeight / 2) / mHeight > mStartScreen)) {
                    mState = State.ToNext;
                } else {
                    mState = State.Normal;
                }
                changeByState();

                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    private void addPre() {
        mCurScreen = ((mCurScreen - 1) + getChildCount()) % getChildCount();
        int childCount = getChildCount();
        View view = getChildAt(childCount - 1);
        removeViewAt(childCount - 1);
        addView(view, 0);
    }

    private void addNext() {
        mCurScreen = (mCurScreen + 1) % getChildCount();
        int childCount = getChildCount();
        View view = getChildAt(0);
        removeViewAt(0);
        addView(view, childCount - 1);
    }

    private void changeByState() {
        if (getScrollY() != mHeight) {
            switch (mState) {
                case Normal:
                    toNormalAction();
                    break;
                case ToPre:
                    toPreAction();
                    break;
                case ToNext:
                    toNextAction();
                    break;
            }
            postInvalidate();
        }
    }

    private void toNormalAction() {
        int startY;
        int delta;
        int duration;

        mState = State.Normal;
        startY = getScrollY();
        delta = mHeight * mStartScreen - getScrollY();
        duration = (Math.abs(delta)) * 4;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    private void toPreAction() {
        int startY;
        int delta;
        int duration;

        mState = State.ToPre;
        addPre();
        startY = getScrollY() + mHeight;
        setScrollY(startY);
        delta = -(startY - mStartScreen * mHeight);
        duration = (Math.abs(delta)) * 3;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    private void toNextAction() {
        int startY;
        int delta;
        int duration;

        mState = State.ToNext;
        addNext();
        startY = getScrollY() - mHeight;
        setScrollY(startY);
        delta = mHeight * mStartScreen - startY;
        duration = (Math.abs(delta)) * 3;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    private void recycleMove(int delta) {
        delta = delta % mHeight;
        delta = (int) (delta / resistance);
        if (Math.abs(delta) > mHeight / 4) {
            return;
        }
        scrollBy(0, delta);

        int scrollY = getScrollY();
        if (scrollY < 5 && mStartScreen != 0) {
            addPre();
            scrollBy(0, mHeight);
        } else if (scrollY > (getChildCount() - 1) * mHeight - 5) {
            addNext();
            scrollBy(0, -mHeight);
        }
    }
}
