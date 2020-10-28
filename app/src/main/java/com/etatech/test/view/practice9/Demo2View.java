package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.OverScroller;

import com.blankj.utilcode.util.AdaptScreenUtils;

/**
 * Created by Michael
 * Date:  2020/10/27
 * Desc:
 */
public class Demo2View extends View {

    private float mLastX = 0;
    private float mCurScale = 0;
    private Point centerPos = new Point(0, 0);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int textSize = AdaptScreenUtils.pt2Px(150);

    private Path rulerPath;
    private int rulerSize = AdaptScreenUtils.pt2Px(200);
    private int sizePerSmall = AdaptScreenUtils.pt2Px(27);

    private int mHalfWidth = 0;                          //一半宽度
    private int mLength = 0;                            // 长度
    private int mMinPositionX = 0;                      // 最小可滑动值
    private int mMaxPositionX = 0;                      // 最大可滑动值
    private OverScroller mOverScroller;                 // 惯性滑动
    private VelocityTracker mVelocityTracker;           // 获取速度
    private int mMinimumVelocity;                       // 惯性最小速度
    private int mMaximumVelocity;                       // 惯性最大速度

    private Demo2MainView parent;

    public Demo2View(Context context, Demo2MainView mainView) {
        super(context);
        parent = mainView;
        init(context);
    }

    public Demo2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mOverScroller = new OverScroller(context);
        // 配置速度
        mVelocityTracker = VelocityTracker.obtain();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();

        mCurScale = parent.getMaxScale() / 2;

        // 第一次进入, 跳转到设定刻度
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                goToScale(mCurScale);
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLength = (parent.getMaxScale() - parent.getMinScale()) * 10 * sizePerSmall;
        centerPos.x = w / 2;
        centerPos.y = h / 2;
        mMinPositionX = 0;
        mMaxPositionX = mLength;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rulerPath = new Path();
        paint.setColor(Color.parseColor("#CCCCCC"));
        paint.setStrokeWidth(AdaptScreenUtils.pt2Px(3));
        rulerPath.moveTo(centerPos.x, centerPos.y - rulerSize / 2);
        rulerPath.lineTo(centerPos.x + parent.getMaxScale() * 10 * sizePerSmall, centerPos.y - rulerSize / 2);
        for (int i = parent.getMinScale(); i <= parent.getMaxScale(); i++) {
            float curX = centerPos.x + sizePerSmall * 10 * (i - parent.getMinScale());
            rulerPath.moveTo(curX, centerPos.y - rulerSize / 2);
            rulerPath.lineTo(curX, centerPos.y + rulerSize / 2);

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(textSize * 0.6f);
            canvas.drawText(i + "",
                    curX - paint.measureText(i + "") / 2,
                    centerPos.y + rulerSize / 2 + paint.getTextSize(),
                    paint);

            for (int j = 1; j < 10 && i < parent.getMaxScale(); j++) {
                rulerPath.moveTo(curX + sizePerSmall * j, centerPos.y - rulerSize / 2);
                rulerPath.lineTo(curX + sizePerSmall * j, centerPos.y);
            }
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#CCCCCC"));
        paint.setStrokeWidth(AdaptScreenUtils.pt2Px(6));
        canvas.drawPath(rulerPath, paint);
    }

    private void goToScale(float scale) {
        scrollTo(scaleToScrollX(scale), 0);
    }

    private int scaleToScrollX(float scale) {
        return (int) (scale * 10 * sizePerSmall);
    }

    private float scrollXToScale(int scrollX) {
        return scrollX / 10f / sizePerSmall;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();

        // 开始速度检测
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mLastX = currentX;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = mLastX - currentX;
                mLastX = currentX;
                scrollBy((int) moveX, 0);
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityX = (int) mVelocityTracker.getXVelocity();
                if (Math.abs(velocityX) > mMinimumVelocity) {
                    fling(-velocityX);
                } else {
                    scrollBackToCurScale();
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
    public void scrollTo(int x, int y) {
        if (x < mMinPositionX) {
            x = mMinPositionX;
        }
        if (x > mMaxPositionX) {
            x = mMaxPositionX;
        }
        if (x != getScrollX()) {
            super.scrollTo(x, y);
        }
        mCurScale = scrollXToScale(x);
        parent.setCurScale(Math.round(mCurScale * 10) / 10f);
    }

    private void fling(int vX) {
        mOverScroller.fling(getScrollX(), 0, vX, 0, mMinPositionX, mMaxPositionX, 0, 0);
        invalidate();
    }

    private void scrollBackToCurScale() {
        mCurScale = Math.round(mCurScale * 10) / 10f;
        mOverScroller.startScroll(getScrollX(), 0, scaleToScrollX(mCurScale) - getScrollX(), 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
            // 滑动结束 自动取整
            if (!mOverScroller.computeScrollOffset() && mCurScale != Math.round(mCurScale * 10) / 10f) {
                scrollBackToCurScale();
            }
            invalidate();
        }
    }
}
