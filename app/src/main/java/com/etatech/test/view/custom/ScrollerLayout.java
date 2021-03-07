package com.etatech.test.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.etatech.test.R;
import com.etatech.test.view.practice9.Demo1View;

/**
 * Created by Michael
 * Date:  2020/11/17
 * Desc:
 */
public class ScrollerLayout extends ViewGroup {
    private Scroller mScroller; // 用于完成滚动操作的实例
    private int mTouchSlop;     // 判定拖动的最小像素
    private float mXDown;       // 按下的坐标
    private float mYDown;       // 按下的坐标
    private float mXMove;       // 拖动时的坐标
    private float mYMove;       // 拖动时的坐标
    private float mXLastMoe;    // 拖动开始时的坐标
    private float mYLastMoe;    // 拖动开始时的坐标
    private int leftBorder;     // 滚动的左边界
    private int topBorder;      // 滚动的上边界
    private int rightBorder;    // 滚动的右边界
    private int downBorder;     // 滚动的下边界

    private int orientation;

    public ScrollerLayout(Context context) {
        this(context, null);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollerLayout);
        orientation = attr.getInt(R.styleable.ScrollerLayout_orientation, 0);
        init(context);
    }

    private void init(Context context) {
        // 第一步，创建Scroller的实例
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            int curWidth = 0, curHeight = 0, toWidth = 0, toHeight = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);

                toWidth = curWidth + childView.getMeasuredWidth();
                toHeight = curHeight + childView.getMeasuredHeight();

                if (orientation == 0) {
                    childView.layout(curWidth, 0, toWidth, childView.getMeasuredHeight());
                } else {
                    childView.layout(0, curHeight, childView.getMeasuredWidth(), toHeight);
                }

                curWidth += childView.getMeasuredWidth();
                curHeight += childView.getMeasuredHeight();
            }

            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(childCount - 1).getRight();
            topBorder = getChildAt(0).getTop();
            downBorder = getChildAt(childCount - 1).getBottom();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (orientation == 0) {
                    mXMove = event.getRawX();
                    int scrolldX = (int) (mXLastMoe - mXMove);
                    if (getScaleX() + scrolldX < leftBorder) {
                        scrollTo(leftBorder, 0);
                        return true;
                    } else if (getScaleX() + getWidth() + scrolldX > rightBorder) {
                        scrollTo(rightBorder - getWidth(), 0);
                        return true;
                    }

                    scrollBy(scrolldX, 0);
                    mXLastMoe = mXDown;
                } else {
                    mYMove = event.getRawY();
                    int scrolldY = (int) (mYLastMoe - mYMove);
                    if (getScaleY() + scrolldY < topBorder) {
                        scrollTo(topBorder, 0);
                        return true;
                    } else if (getScaleY() + getHeight() + scrolldY > downBorder) {
                        scrollTo(downBorder - getHeight(), 0);
                        return true;
                    }

                    scrollBy(scrolldY, 0);
                    mYLastMoe = mYDown;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (orientation == 0) {
                    // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                    int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                    int dx = targetIndex * getWidth() - getScrollX();
                    // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                    // 如果当前事件是ACTION_UP时，说明用户手指抬起来了，但是目前很有可能用户只是将布局拖动到了中间，
                    // 我们不可能让布局就这么停留在中间的位置，因此接下来就需要借助Scroller来完成后续的滚动操作。首先,
                    // 这里我们先根据当前的滚动位置来计算布局应该继续滚动到哪一个子控件的页面，然后计算出距离该页面还需
                    // 滚动多少距离。接下来我们就该进行上述步骤中的第二步操作，调用startScroll()方法来初始化滚动数据并
                    // 刷新界面。startScroll()方法接收四个参数，第一个参数是滚动开始时X的坐标，第二个参数是滚动开始时
                    // Y的坐标，第三个参数是横向滚动的距离，正值表示向左滚动，第四个参数是纵向滚动的距离，正值表示向上
                    // 滚动。紧接着调用invalidate()方法来刷新界面。
                    mScroller.startScroll(getScrollX(), 0, dx, 0);
                    invalidate();
                } else {
                    int targetIndex = (getScrollY() + getHeight() / 2) / getHeight();
                    int dy = targetIndex * getHeight() - getScrollY();
                    mScroller.startScroll(getScrollY(), 0, dy, 0);
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (orientation == 0) {
                    mXDown = ev.getRawX();
                    mXLastMoe = mXDown;
                } else {
                    mYDown = ev.getRawY();
                    mYLastMoe = mYDown;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (orientation == 0) {
                    mXMove = ev.getRawX();
                    float diff = Math.abs(mXMove - mXDown);
                    mXLastMoe = mXMove;
                    // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                    if (diff > mTouchSlop) {
                        return true;
                    }
                } else {
                    mYMove = ev.getRawY();
                    float diff = Math.abs(mYMove - mYDown);
                    mYLastMoe = mYMove;
                    if (diff > mTouchSlop) {
                        return true;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
