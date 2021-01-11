package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.etatech.test.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael
 * Date:  2020/11/11
 * Desc:
 */
public class Demo7View extends View {
    private Paint paintBg;
    private Paint paintFg;
    private Point centerPos;
    private float len = Tools.pt2Px(50);
    private List<Integer> starList;

    public Demo7View(Context context) {
        super(context);
        init();
    }

    public Demo7View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintBg = new Paint();
        paintFg = new Paint();
        starList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            starList.add(Tools.randomRange(1, 5));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new Point(w / 2, h / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBgLine(canvas);

        paintFg.setColor(Color.parseColor("#CC333333"));
        Path path = new Path();

        for (int i = 0; i < starList.size(); i++) {
            PointF starPos = new PointF(centerPos.x + len * starList.get(i) * (float) Math.cos(Math.PI / 3f * i),
                    centerPos.y + len * starList.get(i) * (float) Math.sin(Math.PI / 3f * i));
            if (i == 0) {
                path.moveTo(starPos.x, starPos.y);
            } else {
                path.lineTo(starPos.x, starPos.y);
            }
        }
        path.close();
        canvas.drawPath(path, paintFg);
    }

    private void drawBgLine(Canvas canvas) {
        paintBg.setStyle(Paint.Style.STROKE);
        paintBg.setStrokeWidth(Tools.pt2Px(3));
        Path path = new Path();

        for (int i = 1; i <= 5; i++) {
            path.moveTo(centerPos.x + len * i * (float) Math.cos(Math.PI / 3f),
                    centerPos.y + len * i * (float) Math.sin(Math.PI / 3f));
            for (int j = 1; j <= 6; j++) {
                path.lineTo(centerPos.x + len * i * (float) Math.cos(Math.PI / 3f * j),
                        centerPos.y + len * i * (float) Math.sin(Math.PI / 3f * j));
            }
            path.close();

            for (int j = 0; j < 6; j++) {
                path.moveTo(centerPos.x, centerPos.y);
                path.lineTo(centerPos.x + len * 5 * (float) Math.cos(Math.PI / 3f * j),
                        centerPos.y + len * 5 * (float) Math.sin(Math.PI / 3f * j));
                path.close();
            }
        }

        canvas.drawPath(path, paintBg);
    }
}
