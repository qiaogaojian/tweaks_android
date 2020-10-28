package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.blankj.utilcode.util.AdaptScreenUtils;

/**
 * Created by Michael
 * Date:  2020/10/27
 * Desc:
 */
public class Demo2MainView extends ViewGroup {
    private Demo2View ruler;
    private int minScale = 0;
    private int maxScale = 100;
    private int rulerHeight = AdaptScreenUtils.pt2Px(200);  // 尺子高度
    private int rulerSmallWidth = AdaptScreenUtils.pt2Px(27);  // 尺子宽度(0.1 刻度代表的像素值)

    private float curScale = 0;
    private Point centerPos = new Point(0, 0);
    private int rulerSize = AdaptScreenUtils.pt2Px(200);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint text2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int textSize = AdaptScreenUtils.pt2Px(150);

    public void setCurScale(float curScale) {
        this.curScale = curScale;
        postInvalidate();
    }

    public int getMinScale() {
        return minScale;
    }

    public void setMinScale(int minScale) {
        this.minScale = minScale;
    }

    public int getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(int maxScale) {
        this.maxScale = maxScale;
    }

    public int getRulerHeight() {
        return rulerHeight;
    }

    public void setRulerHeight(int rulerHeight) {
        this.rulerHeight = rulerHeight;
    }

    public int getRulerSmallWidth() {
        return rulerSmallWidth;
    }

    public void setRulerSmallWidth(int rulerSmallWidth) {
        this.rulerSmallWidth = rulerSmallWidth;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public Demo2MainView(Context context) {
        super(context);
        init(context);
    }

    public Demo2MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Demo2MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerPos.x = getMeasuredWidth() / 2;
        centerPos.y = getMeasuredHeight() / 2;
        // 要测量子view 不然子view没有onMeasure()回调
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getVisibility() != GONE) {
                measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ruler.layout(0, 0, r - l, b - t);
    }

    private void init(Context context) {
        ruler = new Demo2View(context, this);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ruler.setLayoutParams(layoutParams);
        addView(ruler);

        setWillNotDraw(false); // 设置ViewGroup可绘制
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制重量数字和kg
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.parseColor("#A0DA2D"));
        textPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(curScale + "",
                centerPos.x - textPaint.measureText(curScale + "") / 2,
                centerPos.y - textSize,
                textPaint);
        text2Paint.setTextSize(textSize * 0.3f);
        text2Paint.setColor(Color.parseColor("#A0DA2D"));
        canvas.drawText("kg",
                centerPos.x + 20 + textPaint.measureText(curScale + "") / 2,
                centerPos.y - textSize - textPaint.getTextSize() / 2,
                text2Paint);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        // 最后绘制尺子指示标
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(AdaptScreenUtils.pt2Px(8));
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(centerPos.x, centerPos.y - rulerSize / 2, centerPos.x, centerPos.y + rulerSize / 2, textPaint);
    }
}
