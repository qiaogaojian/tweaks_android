package com.etatech.test.view.custom.floatmenu;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael
 * Date:  2020/2/3
 * Func:
 */
public class FloatMenuView extends View {
    private Paint mPaint;//画笔
    private int mMenuBackgroundColor = -1;//菜单Item的背景颜色

    private int mItemWidth = dip2px(50);//菜单Item的宽度
    private int mItemHeight = dip2px(60);//菜单Item的高度
    private int mIconWidth = dip2px(40);//菜单Item的宽度
    private int mIconHeight = dip2px(40);//菜单Item的高度

    private int mItemLeft = 0;//菜单Item左边的默认偏移值，这里是0
    private boolean isLeft;

    private List<FloatItem> mItemList = new ArrayList<>();//菜单Item列表的内容
    private List<RectF> mItemRectList = new ArrayList<>();//菜单Item所占用位置的记录，用于判断点击事件

    private FloatMenuView.OnMenuClickListener mOnMenuClickListener;//菜单项的点击事件回调

    private ObjectAnimator mAlphaAnim;//消失关闭动画的透明值

    public void setItemList(List<FloatItem> itemList) {
        mItemList = itemList;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public void setMenuBackgroundColor(int mMenuBackgroundColor) {
        this.mMenuBackgroundColor = mMenuBackgroundColor;
    }

    public FloatMenuView(Context context) {
        super(context);
    }

    public FloatMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mItemWidth * mItemList.size(), mItemHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        drawFloatItems(canvas);
    }

    private void drawFloatItems(Canvas canvas) {
        mItemRectList.clear();
        for (int i = 0; i < mItemList.size(); i++) {
            canvas.save();
            // icon 背景色
            mPaint.setColor(mMenuBackgroundColor);
            float cx = (mItemLeft + i * mItemWidth) + mItemWidth / 2;//x中心点
            float cy = mItemHeight / 2;//y中心点
            float radius = mItemWidth / 2;//半径
            canvas.drawCircle(cx, cy, radius, mPaint);

            mItemRectList.add(new RectF(mItemLeft + i * mItemWidth, 0, mItemLeft + mItemWidth + i * mItemWidth, mItemHeight));
            drawIcon(canvas, i);
        }
        canvas.restore();
    }

    private void drawIcon(Canvas canvas, int position) {
        FloatItem floatItem = mItemList.get(position);

        if (floatItem.icon != null) {
            float centryX = mItemLeft + mItemWidth / 2 + (mItemWidth) * position;//计算每一个item的中心点x的坐标值
            float centryY = mItemHeight / 2;//计算每一个item的中心点的y坐标值
            float paddH = (mItemHeight - mIconHeight) / 2;//总高度减去文字的高度，减去icon高度，再除以2就是上下的padding
            float paddW = (mItemWidth - mIconWidth) / 2;
            if (isLeft) {
                paddW *= -1;
            }
            float left = centryX - mIconWidth / 2 + paddW;//计算icon的左坐标值 中心点往左移宽度的四分之一
            float right = centryX + mIconWidth / 2 + paddW;
            float top = centryY - mItemHeight / 2 + paddH;//计算icon的上坐标值
            float bottom = top + mIconHeight;//剩下的高度空间用于画文字

            //画icon
            mPaint.setColor(0xFFFFFFFF);
            canvas.drawBitmap(floatItem.icon, null, new RectF(left, top, right, bottom), mPaint);
        }
    }

    public void invalidateView() {
        if (mItemList.size() == 0) {
            return;
        }
        invalidate();
    }

    public void dismiss() {
        if (!mAlphaAnim.isRunning()) {
            mAlphaAnim.start();
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == GONE) {
            if (mOnMenuClickListener != null) {
                mOnMenuClickListener.dismiss();
            }
        }
        super.onWindowVisibilityChanged(visibility);
    }

    public void setOnMenuClickListener(FloatMenuView.OnMenuClickListener onMenuClickListener) {
        this.mOnMenuClickListener = onMenuClickListener;
    }

    public interface OnMenuClickListener {
        void onItemClick(int position, String title);

        void dismiss();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < mItemRectList.size(); i++) {
                    if (mOnMenuClickListener != null && isPointInRect(new PointF(event.getX(), event.getY()), mItemRectList.get(i))) {
                        mOnMenuClickListener.onItemClick(i, mItemList.get(i).title);
                        return true;
                    }
                }
                dismiss();
        }
        return false;
    }

    private boolean isPointInRect(PointF pointF, RectF targetRect) {
        return pointF.x >= targetRect.left && pointF.x <= targetRect.right && pointF.y >= targetRect.top && pointF.y <= targetRect.bottom;
    }

    public static class Builder {

        private Context mActivity;
        private List<FloatItem> mFloatItems = new ArrayList<>();
        private int mMenuBackgroundColor = -1;
        private boolean isLeft = false;

        public FloatMenuView.Builder setMenuBackgroundColor(int mMenuBackgroundColor) {
            this.mMenuBackgroundColor = mMenuBackgroundColor;
            return this;
        }

        public FloatMenuView.Builder setFloatItems(List<FloatItem> floatItems) {
            this.mFloatItems = floatItems;
            return this;
        }

        public Builder(Context activity) {
            mActivity = activity;
        }

        public FloatMenuView.Builder addItem(FloatItem floatItem) {
            mFloatItems.add(floatItem);
            return this;
        }

        public FloatMenuView.Builder addItems(List<FloatItem> list) {
            mFloatItems.addAll(list);
            return this;
        }

        public FloatMenuView.Builder setLeft(boolean left) {
            isLeft = left;
            return this;
        }

        public FloatMenuView create() {
            FloatMenuView floatMenuView = new FloatMenuView(mActivity);
            floatMenuView.setItemList(mFloatItems);
            floatMenuView.setLeft(isLeft);
            floatMenuView.invalidateView();
            floatMenuView.setMenuBackgroundColor(mMenuBackgroundColor);
            return floatMenuView;
        }
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

