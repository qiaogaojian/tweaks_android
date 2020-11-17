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
    private int mMinimumVelocity;                           // 惯性最小速度
    private int mMaximumVelocity;                           // 惯性最大速度
    private Camera mCamera;
    private Matrix mMatrix;

    private Paint paint;
    private ObjectAnimator ani;

    private int mWidth;
    private int mHeight;
    private static final int standerSpeed = 2000;
    private static final int flingSpeed = 800;
    private int addCount;
    private int alreadyAdd = 0;
    private boolean isAdding = false;
    private int mCurScreen = 1;
    private float mDownX, mDownY, mTempY;
    private boolean isSliding = false;

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
        this.mContext = context;
        init(mContext);
    }

    private void init(Context context) {
        paint = new Paint();

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mCamera = new Camera();
        mMatrix = new Matrix();
        mScroller = new Scroller(context);

        mVelocityTracker = VelocityTracker.obtain();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isSliding = false;
                mDownX = x;
                mTempY = mDownY = y;
                if (!mScroller.isFinished()) {
                    //当上一次滑动没有结束时，再次点击，强制滑动在点击位置结束
                    mScroller.setFinalY(mScroller.getCurrY());
                    mScroller.abortAnimation();
                    scrollTo(0, getScrollY());
                    isSliding = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isSliding) {
                    isSliding = isCanSliding(ev);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSliding;
    }


    public boolean isCanSliding(MotionEvent ev) {
        float moveX;
        float moveY;
        moveX = ev.getX();
        mTempY = moveY = ev.getY();
        if (Math.abs(moveY - mDownX) > mTouchSlop && (Math.abs(moveY - mDownY) > (Math.abs(moveX - mDownX)))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(event);
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isSliding) {
                    int realDelta = (int) (mDownY - y);
                    mDownY = y;
                    if (mScroller.isFinished()) {
                        recycleMove(realDelta);
                    }
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isSliding) {
                    isSliding = false;
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    float yVelocity = mVelocityTracker.getYVelocity();
                    if (yVelocity > standerSpeed || ((getScrollY() + mHeight / 2) / mHeight < mStartScreen)) {
                        mState = State.ToPre;
                    } else if (yVelocity < -standerSpeed || ((getScrollY() + mHeight / 2) / mHeight > mStartScreen)) {
                        mState = State.ToNext;
                    } else {
                        mState = State.Normal;
                    }
                    changeByState(yVelocity);
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            if (mState == State.ToPre) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY() + mHeight * alreadyAdd);
                if (getScrollY() < (mHeight + 2) && addCount > 0) {
                    isAdding = true;
                    addPre();
                    alreadyAdd++;
                    addCount--;
                }
            } else if (mState == State.ToNext) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY() - mHeight * alreadyAdd);
                if (getScrollY() > (mHeight) && addCount > 0) {
                    isAdding = true;
                    addNext();
                    alreadyAdd++;
                    addCount--;
                }
            } else {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            }
            postInvalidate();
        }

        if (mScroller.isFinished()) {
            alreadyAdd = 0;
            addCount = 0;
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

    private void toNormalAction() {
        int startY;
        int delta;
        int duration;

        mState = State.Normal;
        addCount = 0;
        startY = getScrollY();
        delta = mHeight * mStartScreen - getScrollY();
        duration = (Math.abs(delta)) * 4;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    private void toPreAction(float yVelocity) {
        int startY;
        int delta;
        int duration;

        mState = State.ToPre;
        addPre();
        int flingSpeedCount = (yVelocity - standerSpeed) > 0 ? (int) (yVelocity - standerSpeed) : 0;
        addCount = flingSpeedCount / flingSpeed + 1;
        startY = getScrollY() + mHeight;
        setScrollY(startY);
        delta = -(startY - mStartScreen * mHeight) - (addCount - 1) * mHeight;
        duration = (Math.abs(delta)) * 3;
        mScroller.startScroll(0, startY, 0, delta, duration);
        addCount--;
    }

    private void toNextAction(float yVelocity) {
        int startY;
        int delta;
        int duration;

        mState = State.ToNext;
        addNext();
        int flingSpeedCount = (Math.abs(yVelocity) - standerSpeed) > 0 ?
                (int) (Math.abs(yVelocity) - standerSpeed) : 0;
        addCount = flingSpeedCount / flingSpeed + 1;
        startY = getScrollY() - mHeight;
        setScrollY(startY);
        delta = mHeight * mStartScreen - startY + (addCount - 1) * mHeight;
        duration = (Math.abs(delta)) * 3;
        mScroller.startScroll(0, startY, 0, delta, duration);
        addCount--;
    }

    private void changeByState(float yVelocity) {
        alreadyAdd = 0;
        if (getScrollY() != mHeight) {
            switch (mState) {
                case Normal:
                    toNormalAction();
                    break;
                case ToPre:
                    toPreAction(yVelocity);
                    break;
                case ToNext:
                    toNextAction(yVelocity);
                    break;
            }
            postInvalidate();
        }
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
