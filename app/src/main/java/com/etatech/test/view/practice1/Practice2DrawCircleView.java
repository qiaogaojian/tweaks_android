package com.etatech.test.view.practice1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;

public class Practice2DrawCircleView extends View {

    public Practice2DrawCircleView(Context context) {
        super(context);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 练习内容：使用 canvas.drawCircle() 方法画圆
        // 一共四个圆：1.实心圆 2.空心圆 3.蓝色实心圆 4.线宽为 20 的空心圆
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(AdaptScreenUtils.pt2Px(300), AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(150), paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawCircle(AdaptScreenUtils.pt2Px(800), AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(150), paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(AdaptScreenUtils.pt2Px(300), AdaptScreenUtils.pt2Px(600), AdaptScreenUtils.pt2Px(150), paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(AdaptScreenUtils.pt2Px(50));
        paint.setColor(Color.BLACK);
        canvas.drawCircle(AdaptScreenUtils.pt2Px(800), AdaptScreenUtils.pt2Px(600), AdaptScreenUtils.pt2Px(150), paint);
    }
}
