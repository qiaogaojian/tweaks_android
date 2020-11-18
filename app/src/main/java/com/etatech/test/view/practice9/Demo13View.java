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
public class Demo13View extends ViewGroup {
    private int mCurPosX = 1;
    private int mCurPosY = 1;
    private float mAngle = 90;
    private int mOrient = 1;  // 1 横向 2 竖向

    private Camera mCamera;
    private Matrix mMatrix;
    private Scroller mScroller;

    private int mWidth;
    private int mHeight;
    private float mLastX;
    private float mLastY;

    public Demo13View(Context context) {
        this(context, null);
    }

    public Demo13View(Context context, @Nullable AttributeSet attrs) {
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
        if (mOrient == 1) {
            scrollTo(mCurPosY * mWidth, 0);
        } else {
            scrollTo(0, mCurPosX * mHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = 0;
        int childLeft = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                if (mOrient == 1) {
                    // 从上到下依次排列子布局
                    child.layout(childLeft, 0, childLeft + child.getMeasuredWidth(), child.getMeasuredHeight());
                    childLeft = childLeft + child.getMeasuredWidth();
                } else {
                    // 从上到下依次排列子布局
                    child.layout(0, childTop, child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
                    childTop = childTop + child.getMeasuredHeight();
                }
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            if (mOrient == 1) {
                drawChildX(canvas, i, getDrawingTime());
            } else {
                drawChildY(canvas, i, getDrawingTime());
            }
        }
    }

    private void drawChildY(Canvas canvas, int i, long drawingTime) {
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

        mCamera.rotateX(degree);                    // 沿X轴旋转
        mCamera.getMatrix(mMatrix);
        mMatrix.postTranslate(centerX, centerY);    // 移动Camera到视图中心 前乘T * M
        mMatrix.preTranslate(-centerX, -centerY);   // 移动Camera到初始位置 后乘(T * M) * T'
        canvas.concat(mMatrix);

        drawChild(canvas, getChildAt(i), drawingTime);
        mCamera.restore();
        canvas.restore();
    }

    private void drawChildX(Canvas canvas, int i, long drawingTime) {
        int childX = mWidth * i;
        int scrollX = getScrollX();

        if (childX > scrollX + mWidth) {
            return;
        }
        if (childX < scrollX - mWidth) {
            return;
        }

        float centerX;
        float centerY = mHeight / 2;

        if (childX < scrollX) {
            centerX = childX + mWidth;
        } else {
            centerX = childX;
        }

        float degree = mAngle * (scrollX - childX) / mWidth;
        if (degree > 90 || degree < -90) {
            return;
        }

        canvas.save();
        mCamera.save();

        mCamera.rotateY(-degree);
        mCamera.getMatrix(mMatrix);
        mMatrix.postTranslate(centerX, centerY);
        mMatrix.preTranslate(-centerX, -centerY);
        canvas.concat(mMatrix);

        drawChild(canvas, getChildAt(i), drawingTime);
        mCamera.restore();
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                mLastX = currentX;
                mLastY = currentY;
                return true;
            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) (mLastX - currentX);
                int offsetY = (int) (mLastY - currentY);
                mLastX = currentX;
                mLastY = currentY;
                if (mOrient == 1) {
                    scrollBy(offsetX, 0);                         // 跟随手指滚动
                } else {
                    scrollBy(0, offsetY);                         // 跟随手指滚动
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mOrient == 1) {
                    int scrollX = getScrollX();
                    if (scrollX >= (getChildCount() - 1) * mWidth) {
                        scrollX = (getChildCount() - 1) * mWidth;
                    }
                    mCurPosX = (scrollX + mWidth / 2) / mWidth;
                    int dX = mCurPosX * mWidth - scrollX;
                    mScroller.startScroll(scrollX, 0, dX, 0);  // 自动滚动到合法位置
                } else {
                    int scrollY = getScrollY();
                    mCurPosY = (scrollY + mHeight / 2) / mHeight;
                    int dY = mCurPosY * mHeight - scrollY;
                    mScroller.startScroll(0, scrollY, 0, dY);  // 自动滚动到合法位置
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
