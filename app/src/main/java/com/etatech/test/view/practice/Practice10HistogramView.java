package com.etatech.test.view.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

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

        //        综合练习
        //        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        Paint paint = new Paint();

        canvas.drawColor(Color.parseColor("#ff556677"));
        paint.setColor(Color.parseColor("#DDDDDD"));
        canvas.drawLine(100, 800, 100, 100, paint);
        canvas.drawLine(100, 800, 1200, 800, paint);

        paint.setColor(Color.GREEN);
        canvas.drawRect(150, 790, 250, 800, paint);
        canvas.drawRect(300, 750, 400, 800, paint);
        canvas.drawRect(450, 750, 550, 800, paint);
        canvas.drawRect(600, 500, 700, 800, paint);
        canvas.drawRect(750, 350, 850, 800, paint);
        canvas.drawRect(900, 300, 1000, 800, paint);
        canvas.drawRect(1050, 550, 1150, 800, paint);


        Paint textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#DDDDDD"));
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Rect bounds = new Rect();
        textPaint.getTextBounds("Froyo", 0, "Froyo".length(), bounds);

        canvas.drawText("Froyo", 200, 800 + bounds.height(), textPaint);
        canvas.drawText("GB", 350, 800 + bounds.height(), textPaint);
        canvas.drawText("ICS", 500, 800 + bounds.height(), textPaint);
        canvas.drawText("JB", 650, 800 + bounds.height(), textPaint);
        canvas.drawText("KitKat", 800, 800 + bounds.height(), textPaint);
        canvas.drawText("L", 950, 800 + bounds.height(), textPaint);
        canvas.drawText("M", 1100, 800 + bounds.height(), textPaint);
    }
}
