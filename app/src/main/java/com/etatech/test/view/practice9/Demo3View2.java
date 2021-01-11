package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import androidx.annotation.Nullable;
import androidx.core.app.NavUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.etatech.test.utils.Tools;

import static com.etatech.test.utils.Tools.calCirPos;

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
    private float curRotation = 300;
    private int bgNum = 5;

    private Demo3MainView parent;

    public void setCurRotation(float curRotation) {
        this.curRotation = curRotation;
        postInvalidate();
    }

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
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        rotationAni();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.reset();
        paint.setAntiAlias(true);
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
        canvas.drawCircle(centerPos.x, centerPos.y, radius, paint);

        if (isDrawArc) {

            float progressArc = 360 * parent.getStepProgress();
            paint.setStyle(Paint.Style.FILL);
            paint.setShader(null);
            Point curPos = calCirPos(centerPos, parent.getSmallRadius(), progressArc);
            canvas.drawCircle(curPos.x, curPos.y, parent.getCircleWidth1() * 2, paint);

            paint.reset();
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#FFFBFBFE"));
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(parent.getCircleWidth1());
            canvas.drawArc(
                    centerPos.x - parent.getSmallRadius(), centerPos.y - parent.getSmallRadius(),
                    centerPos.x + parent.getSmallRadius(), centerPos.y + parent.getSmallRadius(),
                    0,
                    progressArc,
                    false,
                    paint
            );
            paint.setStrokeWidth(parent.getCircleWidth2());
            paint.setPathEffect(new DashPathEffect(new float[]{parent.getCircleWidth2() / 2.0f, parent.getCircleWidth2() * 2}, 0));
            canvas.drawArc(
                    centerPos.x - parent.getSmallRadius(), centerPos.y - parent.getSmallRadius(),
                    centerPos.x + parent.getSmallRadius(), centerPos.y + parent.getSmallRadius(),
                    progressArc,
                    360 - progressArc,
                    false,
                    paint
            );
        }
    }

    private boolean isDrawArc = false;

    public void setDrawArc(boolean drawArc) {
        isDrawArc = drawArc;
        postInvalidate();
    }

    private void drawBgCircle(Canvas canvas, int offset, int curBgNum) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        Point bgCenter = calCirPos(centerPos, offset * 2, curRotation);
        Point gradientStartPos = calCirPos(centerPos, offset, curRotation);
        Point gradientEndPos = calCirPos(centerPos, radius, curRotation);
        Shader shader = new LinearGradient(
                gradientStartPos.x, gradientStartPos.y,
                gradientEndPos.x, gradientEndPos.y,
                Color.parseColor("#00FBFBFE"),
                Color.parseColor("#33FBFBFE"),
                Shader.TileMode.CLAMP);
        Shader shader2 = new RadialGradient(
                gradientEndPos.x, gradientEndPos.y,
                radius * (1.1f - curBgNum / bgNum),
                Color.parseColor("#FFFFFFFF"),
                Color.parseColor("#00FBFFFF"),
                Shader.TileMode.CLAMP
        );
        paint.setShader(new ComposeShader(shader, shader2, PorterDuff.Mode.DST_IN));
        canvas.drawCircle(bgCenter.x, bgCenter.y, radius - offset, paint);
        float arcRotation = 90 * (1.1f - curBgNum / (float) bgNum);
        // canvas.drawArc(
        //         bgCenter.x - (radius - offset), bgCenter.y - (radius - offset),
        //         bgCenter.x + (radius - offset), bgCenter.y + (radius - offset),
        //         curRotation - arcRotation,
        //         arcRotation * 2,
        //         false,
        //         paint);
    }


    private void rotationAni() {
        ObjectAnimator ani = ObjectAnimator.ofFloat(this, "curRotation", 0, 360);
        ani.setRepeatCount(-1);
        ani.setDuration(9000);
        ani.setInterpolator(new LinearInterpolator());
        ani.start();
    }
}
