package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.etatech.test.R;
import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/10/30
 * Desc:
 */
public class Demo3View extends View {

    private Paint textPaint;
    private int stepNum = 666;
    private int distanceNum = 1700;
    private int calorieNum = 36000;
    private int bigTextSize = Tools.pt2Px(120);
    private int smallTextSize = Tools.pt2Px(36);

    private Bitmap bitmapWatch;
    private int watchHeight;
    private int watchWidth;

    private Point centerPos;
    private Demo3MainView parent;

    public Demo3View(Context context, Demo3MainView mainView) {
        super(context);
        this.parent = mainView;
        init();
    }

    public Demo3View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo3View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textPaint = new Paint();

        bitmapWatch = BitmapFactory.decodeResource(getResources(), R.drawable.icon_headview_watch);
        watchHeight = bitmapWatch.getHeight();
        watchWidth = bitmapWatch.getWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new Point(w / 2, h / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        textPaint.setTextSize(bigTextSize);
        textPaint.setColor(Color.parseColor("#FFFBFBFE"));
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(stepNum + "", centerPos.x, centerPos.y, textPaint);

        textPaint.setTextSize(smallTextSize);
        textPaint.setColor(Color.parseColor("#CCFBFBFE"));
        textPaint.setTextAlign(Paint.Align.CENTER);
        String detail = String.format("%.1f km | %d kcal", distanceNum / 1000.0f, calorieNum / 1000);
        canvas.drawText(detail, centerPos.x, centerPos.y + smallTextSize * 1.5f, textPaint);

        textPaint.setColor(Color.parseColor("#FFFBFBFE"));
        canvas.drawBitmap(bitmapWatch,
                centerPos.x - watchWidth / 2,
                centerPos.y + smallTextSize * 3,
                textPaint
        );
    }

}
