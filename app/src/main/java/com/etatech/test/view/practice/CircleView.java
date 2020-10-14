package com.etatech.test.view.practice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/10/13
 * Desc: 自定义view 测试DrawCircle()
 */
public class CircleView extends View {

    Paint paint = new Paint();
    private int radius;
    private int color;

    private int posX;
    private int posY;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.CircleView);
        radius = AdaptScreenUtils.pt2Px(attr.getInteger(R.styleable.CircleView_radius, 100));
        String height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
        color = attr.getColor(R.styleable.CircleView_color, Color.RED);

        paint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getWidth();
        int height = getHeight();

        int[] location = new int[2];
        getLocationOnScreen(location); // 获取View在屏幕中的位置 https://juejin.im/entry/6844903975175602189

        posX = location[0] + width / 2;
        posY = location[1] + height / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(radius, radius, radius, paint);
    }
}
