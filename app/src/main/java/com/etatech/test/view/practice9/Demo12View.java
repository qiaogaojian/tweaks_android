package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;


/**
 * Created by Michael
 * Date:  2020/11/17
 * Desc:
 */
public class Demo12View extends ViewGroup {
    private int mStartPos = 1;
    private float mAngle = 90;

    private Camera mCamera;
    private Matrix mMatrix;
    private Scroller mScroller;

    private int mWidth;
    private int mHeight;
    private float mLastY;
    private int curPosY = -1;

    public Demo12View(Context context) {
        this(context, null);
    }

    public Demo12View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        if (curPosY == -1) {
            scrollTo(0, mStartPos * mHeight);
            curPosY = mStartPos;
        } else {
            scrollTo(0, curPosY * mHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                // 从上到下依次排列子布局
                child.layout(0, childTop, child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
                childTop = childTop + child.getMeasuredHeight();
            }
        }
        for (int i = 0; i < getChildCount(); i++) {
            System.out.println(String.format(" scrollY:%d curPos:%d viewID:%s onLayout", getScrollY(), curPosY, getChildAt(i).getId()));
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            drawScreen(canvas, i, getDrawingTime());
        }
    }

    private void drawScreen(Canvas canvas, int i, long drawingTime) {
        int childY = mHeight * i;           // 当前子View的Y轴位置 以屏幕左上角为基准
        int scrollY = getScrollY();         // 总体滚动的位置 自顶向下增加 以屏幕左上角为基准

        if (childY > scrollY + mHeight) {   // 当前滚动到的子View下方第一个下的所有子View不绘制
            return;
        }
        if (childY < scrollY - mHeight) {   // 当前滚动到的子View上方第一个上的所有子View不绘制
            return;
        }

        float centerX = mWidth / 2;
        float centerY;
        if (childY < scrollY) {             // 当前视图上边界所在子View 以子View底为轴旋转
            centerY = childY + mHeight;
        } else {                            // 当前视图下边界所在子View 和子View顶为轴旋转 和上面是同一个轴
            centerY = childY;
        }

        // 获取视图上边界在单个子View中比例再乘以总旋转角度 获得旋转角度
        float degree = mAngle * (scrollY - childY) / mHeight;  // 确保先乘后除 避免丢失精度
        if (degree > 90 || degree < -90) {
            return;
        }

        canvas.save();
        mCamera.save();

        // mCamera.rotateX(degree);                    // 沿X轴旋转
        mCamera.getMatrix(mMatrix);
        mMatrix.postTranslate(centerX, centerY);    // 移动Camera到视图中心 前乘T * M
        mMatrix.preTranslate(-centerX, -centerY);   // 移动Camera到初始位置 后乘(T * M) * T'
        canvas.concat(mMatrix);

        drawChild(canvas, getChildAt(i), drawingTime);
        mCamera.restore();
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastY = currentY;
                return true;
            case MotionEvent.ACTION_MOVE:
                int realDelta = (int) (mLastY - currentY);
                mLastY = currentY;
                recycleMove(realDelta);
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int scrollY = getScrollY();
                int targetPos = (scrollY + mHeight / 2) / mHeight;
                int dy = targetPos * mHeight - scrollY;
                mScroller.startScroll(0, scrollY, 0, dy);  // 自动滚动到合法位置
                invalidate();
                break;
        }
        return true;
    }

    private void recycleMove(int delta) {
        delta = delta % mHeight;
        if (Math.abs(delta) > mHeight / 4) {
            return;
        }
        scrollBy(0, delta);
        int scrollY = getScrollY();
        if (scrollY < 5 && mStartPos != 0) {
            System.out.println(String.format("addPre() scrollY: %d \n", scrollY));
            addPre();
            // scrollBy(0, -mHeight);
            for (int i = 0; i < getChildCount(); i++) {
                System.out.println(String.format(" scrollY:%d curPos:%d viewID:%s", getScrollY(), curPosY, getChildAt(i).getId()));
            }
        } else if (scrollY > (getChildCount() - 1) * mHeight - 5) {
            System.out.println(String.format("addNext() scrollY: %d \n", scrollY));
            addNext();
            // scrollBy(0, mHeight);
            for (int i = 0; i < getChildCount(); i++) {
                System.out.println(String.format(" scrollY:%d curPos:%d viewID:%s", getScrollY(), curPosY, getChildAt(i).getId()));
            }
        }

    }

    /**
     * 把第一个item移动到最后一个item位置
     */
    private void addNext() {
        curPosY = (curPosY + 1) % getChildCount();
        int childCount = getChildCount();
        View view = getChildAt(0);
        removeViewAt(0);
        addView(view, childCount - 1);
    }

    /**
     * 把最后一个item移动到第一个item位置
     */
    private void addPre() {
        curPosY = ((curPosY - 1) + getChildCount()) % getChildCount();
        int childCount = getChildCount();
        View view = getChildAt(childCount - 1);
        removeViewAt(childCount - 1);
        addView(view, 0);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
