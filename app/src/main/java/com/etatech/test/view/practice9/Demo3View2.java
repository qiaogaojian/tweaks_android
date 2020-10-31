package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.etatech.test.utils.Tools;
import com.facebook.common.logging.FLog;

/**
 * Created by Michael
 * Date:  2020/10/31
 * Desc:
 */
public class Demo3View2 extends View {
    private Paint paint;
    private Point centerPos;
    private int radius = Tools.pt2Px(300);
    private int strokeWidth = Tools.pt2Px(50);
    private int bgOffset = Tools.pt2Px(10);
    private int curRotation = 300;
    private int bgNum = 5;

    private Demo3MainView parent;

    public Demo3View2(Context context, Demo3MainView mainView) {
        super(context);
        parent = mainView;
        init();
    }

    public Demo3View2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo3View2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new Point(w / 2, h / 2);
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < bgNum; i++) {
            drawBgCircle(canvas, bgOffset * i, i + 1);
        }

        paint.setColor(Color.parseColor("#FFFBFBFE"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        Point curPosLight = calCirPos(centerPos, radius, curRotation);
        Point curPosDark = calCirPos(centerPos, radius, curRotation + 180);
        Shader shader = new LinearGradient(
                curPosLight.x, curPosLight.y,
                curPosDark.x, curPosDark.y,
                Color.parseColor("#FFFBFBFE"),
                Color.parseColor("#66FBFBFE"),
                Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // canvas.drawCircle(centerPos.x, centerPos.y, radius, paint);
    }

    private void drawBgCircle(Canvas canvas, int offset, int curBgNum) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(bgOffset);

        Point bgCenter = calCirPos(centerPos, offset * 2, curRotation);
        Point gradientStartPos = calCirPos(centerPos, offset, curRotation);
        Point gradientEndPos = calCirPos(centerPos, radius, curRotation);
        Shader shader = new LinearGradient(
                gradientStartPos.x, gradientStartPos.y,
                gradientEndPos.x, gradientEndPos.y,
                Color.parseColor("#00FBFBFE"),
                Color.parseColor("#20FBFBFE"),
                Shader.TileMode.CLAMP);
        Shader shader2 = new RadialGradient(
                gradientEndPos.x, gradientEndPos.y,
                radius * (1.1f - curBgNum / bgNum),
                Color.parseColor("#FFFFFFFF"),
                Color.parseColor("#00FBFFFF"),
                Shader.TileMode.CLAMP
        );
        paint.setShader(new ComposeShader(shader, shader2, PorterDuff.Mode.DST_IN));
        canvas.drawCircle(bgCenter.x, bgCenter.y, radius-offset, paint);
        float arcRotation = 90 * (1.1f - curBgNum / (float) bgNum);
        // canvas.drawArc(
        //         bgCenter.x - (radius - offset), bgCenter.y - (radius - offset),
        //         bgCenter.x + (radius - offset), bgCenter.y + (radius - offset),
        //         curRotation - arcRotation,
        //         arcRotation * 2,
        //         false,
        //         paint);
    }

    private Point calCirPos(Point center, float radius, int angle) {
        int posX1 = center.x + (int) (radius * Math.cos(Math.toRadians(angle)));
        int posY1 = center.y + (int) (radius * Math.sin(Math.toRadians(angle)));
        return new Point(posX1, posY1);
    }
}
