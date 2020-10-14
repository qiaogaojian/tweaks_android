package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw1.R;

public class Practice11PieChartView extends View {

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //        综合练习
        //        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        Paint paint = new Paint();
        // 画饼
        paint.setColor(Color.parseColor("#F92671"));
        canvas.drawArc(400, 200, 1000, 800, 0, 3, true, paint);

        paint.setColor(Color.parseColor("#FA961E"));
        canvas.drawArc(400, 200, 1000, 800, 5, 10, true, paint);

        paint.setColor(Color.parseColor("#E7DA73"));
        canvas.drawArc(400, 200, 1000, 800, 17, 10, true, paint);

        paint.setColor(Color.parseColor("#A0DA2D"));
        canvas.drawArc(400, 200, 1000, 800, 29, 50, true, paint);

        paint.setColor(Color.parseColor("#2DE2A6"));
        canvas.drawArc(400, 200, 1000, 800, 81, 99, true, paint);

        paint.setColor(Color.parseColor("#65D9EF"));
        canvas.drawArc(400, 200, 1000, 800, 300, 58, true, paint);

        paint.setColor(Color.parseColor("#AE81FF"));
        canvas.drawArc(380, 180, 980, 780, 180, 120, true, paint);

        // 画线 写字
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);

        paint.setStrokeWidth(5);
        paint.setColor(Color.parseColor("#999999"));
        canvas.drawLine(1000, 500, 1100, 500, paint);
        canvas.drawText("Froyo", 1120, 500, textPaint);

        Rect pos = calcuCirclePos(700, 500, 300, 10, 50);
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, pos.right + 25, pos.bottom + 25, paint);
        canvas.drawLine(pos.right + 25, pos.bottom + 25, 1100, pos.bottom + 25, paint);
        canvas.drawText("Gingerbread", 1120, pos.bottom + 25, textPaint);

        pos = calcuCirclePos(700, 500, 300, 22, 50);
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, pos.right + 25, pos.bottom + 25, paint);
        canvas.drawLine(pos.right + 25, pos.bottom + 25, 1100, pos.bottom + 25, paint);
        canvas.drawText("Ice Cream Sandwich", 1120, pos.bottom + 25, textPaint);

        pos = calcuCirclePos(700, 500, 300, 54, 50);
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, 1100, pos.bottom, paint);
        canvas.drawText("Jelly Bean", 1120, pos.bottom, textPaint);

        pos = calcuCirclePos(700, 500, 300, 330, 50);
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, 1100, pos.bottom, paint);
        canvas.drawText("Lollipop", 1120, pos.bottom , textPaint);

        textPaint.setTextAlign(Paint.Align.RIGHT);

        pos = calcuCirclePos(700, 500, 300, 130, 50);
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, 300, pos.bottom, paint);
        canvas.drawText("KitKat", 280, pos.bottom, textPaint);

        pos = calcuCirclePos(680, 480, 300, 240, 50);
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, 300, pos.bottom, paint);
        canvas.drawText("Marshmallow", 280, pos.bottom, textPaint);

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(50);
        canvas.drawText("饼图", 700, 1000, textPaint);

    }

    private Rect calcuCirclePos(int centerX, int centerY, int radius, int angle, int offset) {
        int posX1 = centerX + (int) (radius * Math.cos(Math.toRadians(angle)));
        int posY1 = centerY + (int) (radius * Math.sin(Math.toRadians(angle)));
        int posX2 = centerX + (int) ((radius + offset) * Math.cos(Math.toRadians(angle)));
        int posY2 = centerY + (int) ((radius + offset) * Math.sin(Math.toRadians(angle)));
        return new Rect(posX1, posY1, posX2, posY2);
    }
}
