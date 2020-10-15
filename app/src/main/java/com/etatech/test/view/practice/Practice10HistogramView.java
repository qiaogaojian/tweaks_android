package com.etatech.test.view.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;

public class Practice10HistogramView extends View {

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 综合练习
        // 练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        Paint paint = new Paint();

        canvas.drawColor(Color.parseColor("#ff556677"));
        paint.setColor(Color.parseColor("#DDDDDD"));
        canvas.drawLine(AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(700), AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(100), paint);
        canvas.drawLine(AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(700), AdaptScreenUtils.pt2Px(880), AdaptScreenUtils.pt2Px(700), paint);

        paint.setColor(Color.GREEN);
        canvas.drawRect(AdaptScreenUtils.pt2Px(250), AdaptScreenUtils.pt2Px(690), AdaptScreenUtils.pt2Px(330), AdaptScreenUtils.pt2Px(700), paint);
        canvas.drawRect(AdaptScreenUtils.pt2Px(350), AdaptScreenUtils.pt2Px(650), AdaptScreenUtils.pt2Px(430), AdaptScreenUtils.pt2Px(700), paint);
        canvas.drawRect(AdaptScreenUtils.pt2Px(450), AdaptScreenUtils.pt2Px(650), AdaptScreenUtils.pt2Px(530), AdaptScreenUtils.pt2Px(700), paint);
        canvas.drawRect(AdaptScreenUtils.pt2Px(550), AdaptScreenUtils.pt2Px(600), AdaptScreenUtils.pt2Px(630), AdaptScreenUtils.pt2Px(700), paint);
        canvas.drawRect(AdaptScreenUtils.pt2Px(650), AdaptScreenUtils.pt2Px(450), AdaptScreenUtils.pt2Px(730), AdaptScreenUtils.pt2Px(700), paint);
        canvas.drawRect(AdaptScreenUtils.pt2Px(750), AdaptScreenUtils.pt2Px(400), AdaptScreenUtils.pt2Px(830), AdaptScreenUtils.pt2Px(700), paint);
        canvas.drawRect(AdaptScreenUtils.pt2Px(850), AdaptScreenUtils.pt2Px(650), AdaptScreenUtils.pt2Px(930), AdaptScreenUtils.pt2Px(700), paint);


        Paint textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#DDDDDD"));
        textPaint.setTextSize(AdaptScreenUtils.pt2Px(20));
        textPaint.setTextAlign(Paint.Align.CENTER);
        Rect bounds = new Rect();
        textPaint.getTextBounds("Froyo", 0, "Froyo".length(), bounds);

        canvas.drawText("Froyo", AdaptScreenUtils.pt2Px(290), AdaptScreenUtils.pt2Px(700) + bounds.height(), textPaint);
        canvas.drawText("GB", AdaptScreenUtils.pt2Px(390), AdaptScreenUtils.pt2Px(700) + bounds.height(), textPaint);
        canvas.drawText("ICS", AdaptScreenUtils.pt2Px(490), AdaptScreenUtils.pt2Px(700) + bounds.height(), textPaint);
        canvas.drawText("JB", AdaptScreenUtils.pt2Px(590), AdaptScreenUtils.pt2Px(700) + bounds.height(), textPaint);
        canvas.drawText("KitKat", AdaptScreenUtils.pt2Px(690), AdaptScreenUtils.pt2Px(700) + bounds.height(), textPaint);
        canvas.drawText("L", AdaptScreenUtils.pt2Px(790), AdaptScreenUtils.pt2Px(700) + bounds.height(), textPaint);
        canvas.drawText("M", AdaptScreenUtils.pt2Px(890), AdaptScreenUtils.pt2Px(700) + bounds.height(), textPaint);
    }
}
