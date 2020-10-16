package com.etatech.test.view.practice1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;

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
        canvas.drawArc(AdaptScreenUtils.pt2Px(300), AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(700), AdaptScreenUtils.pt2Px(600), 0, 3, true, paint);

        paint.setColor(Color.parseColor("#FA961E"));
        canvas.drawArc(AdaptScreenUtils.pt2Px(300), AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(700), AdaptScreenUtils.pt2Px(600), 5, 10, true, paint);

        paint.setColor(Color.parseColor("#E7DA73"));
        canvas.drawArc(AdaptScreenUtils.pt2Px(300), AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(700), AdaptScreenUtils.pt2Px(600), 17, 10, true, paint);

        paint.setColor(Color.parseColor("#A0DA2D"));
        canvas.drawArc(AdaptScreenUtils.pt2Px(300), AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(700), AdaptScreenUtils.pt2Px(600), 29, 50, true, paint);

        paint.setColor(Color.parseColor("#2DE2A6"));
        canvas.drawArc(AdaptScreenUtils.pt2Px(300), AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(700), AdaptScreenUtils.pt2Px(600), 81, 99, true, paint);

        paint.setColor(Color.parseColor("#65D9EF"));
        canvas.drawArc(AdaptScreenUtils.pt2Px(300), AdaptScreenUtils.pt2Px(200), AdaptScreenUtils.pt2Px(700), AdaptScreenUtils.pt2Px(600), 300, 58, true, paint);

        paint.setColor(Color.parseColor("#AE81FF"));
        canvas.drawArc(AdaptScreenUtils.pt2Px(280), AdaptScreenUtils.pt2Px(180), AdaptScreenUtils.pt2Px(680), AdaptScreenUtils.pt2Px(580), 180, 120, true, paint);

        // 画线 写字
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(AdaptScreenUtils.pt2Px(20));

        paint.setStrokeWidth(AdaptScreenUtils.pt2Px(5));
        paint.setColor(Color.parseColor("#999999"));
        canvas.drawLine(AdaptScreenUtils.pt2Px(700), AdaptScreenUtils.pt2Px(400), AdaptScreenUtils.pt2Px(800), AdaptScreenUtils.pt2Px(400), paint);
        canvas.drawText("Froyo", AdaptScreenUtils.pt2Px(820), AdaptScreenUtils.pt2Px(400), textPaint);

        Rect pos = calcuCirclePos(AdaptScreenUtils.pt2Px(500), AdaptScreenUtils.pt2Px(400), AdaptScreenUtils.pt2Px(200), 10, AdaptScreenUtils.pt2Px(50));
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, pos.right + AdaptScreenUtils.pt2Px(25), pos.bottom + AdaptScreenUtils.pt2Px(25), paint);
        canvas.drawLine(pos.right + AdaptScreenUtils.pt2Px(25), pos.bottom + AdaptScreenUtils.pt2Px(25), AdaptScreenUtils.pt2Px(800), pos.bottom + AdaptScreenUtils.pt2Px(25), paint);
        canvas.drawText("Gingerbread", AdaptScreenUtils.pt2Px(800), pos.bottom + AdaptScreenUtils.pt2Px(25), textPaint);

        pos = calcuCirclePos(AdaptScreenUtils.pt2Px(500), AdaptScreenUtils.pt2Px(400), AdaptScreenUtils.pt2Px(200), 22, AdaptScreenUtils.pt2Px(50));
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, pos.right + AdaptScreenUtils.pt2Px(25), pos.bottom + AdaptScreenUtils.pt2Px(25), paint);
        canvas.drawLine(pos.right + AdaptScreenUtils.pt2Px(25), pos.bottom + AdaptScreenUtils.pt2Px(25), AdaptScreenUtils.pt2Px(800), pos.bottom + AdaptScreenUtils.pt2Px(25), paint);
        canvas.drawText("Ice Cream Sandwich", AdaptScreenUtils.pt2Px(800), pos.bottom + AdaptScreenUtils.pt2Px(25), textPaint);

        pos = calcuCirclePos(AdaptScreenUtils.pt2Px(500), AdaptScreenUtils.pt2Px(400), AdaptScreenUtils.pt2Px(200), 54, AdaptScreenUtils.pt2Px(50));
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, AdaptScreenUtils.pt2Px(800), pos.bottom, paint);
        canvas.drawText("Jelly Bean", AdaptScreenUtils.pt2Px(800), pos.bottom, textPaint);

        pos = calcuCirclePos(AdaptScreenUtils.pt2Px(500), AdaptScreenUtils.pt2Px(400), AdaptScreenUtils.pt2Px(200), 330, AdaptScreenUtils.pt2Px(50));
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, AdaptScreenUtils.pt2Px(800), pos.bottom, paint);
        canvas.drawText("Lollipop", AdaptScreenUtils.pt2Px(800), pos.bottom , textPaint);

        textPaint.setTextAlign(Paint.Align.RIGHT);

        pos = calcuCirclePos(AdaptScreenUtils.pt2Px(500), AdaptScreenUtils.pt2Px(400), AdaptScreenUtils.pt2Px(200), 130, AdaptScreenUtils.pt2Px(50));
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, AdaptScreenUtils.pt2Px(200), pos.bottom, paint);
        canvas.drawText("KitKat", AdaptScreenUtils.pt2Px(180), pos.bottom, textPaint);

        pos = calcuCirclePos(AdaptScreenUtils.pt2Px(480), AdaptScreenUtils.pt2Px(380), AdaptScreenUtils.pt2Px(200), 240, AdaptScreenUtils.pt2Px(50));
        canvas.drawLine(pos.left, pos.top, pos.right, pos.bottom, paint);
        canvas.drawLine(pos.right, pos.bottom, AdaptScreenUtils.pt2Px(200), pos.bottom, paint);
        canvas.drawText("Marshmallow", AdaptScreenUtils.pt2Px(180), pos.bottom, textPaint);

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(AdaptScreenUtils.pt2Px(50));
        canvas.drawText("饼图", AdaptScreenUtils.pt2Px(500), AdaptScreenUtils.pt2Px(900), textPaint);

    }

    private Rect calcuCirclePos(int centerX, int centerY, int radius, int angle, int offset) {
        int posX1 = centerX + (int) (radius * Math.cos(Math.toRadians(angle)));
        int posY1 = centerY + (int) (radius * Math.sin(Math.toRadians(angle)));
        int posX2 = centerX + (int) ((radius + offset) * Math.cos(Math.toRadians(angle)));
        int posY2 = centerY + (int) ((radius + offset) * Math.sin(Math.toRadians(angle)));
        return new Rect(posX1, posY1, posX2, posY2);
    }
}
