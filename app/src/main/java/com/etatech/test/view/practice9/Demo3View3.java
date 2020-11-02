package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.etatech.test.utils.Tools;

import java.util.List;
import java.util.Random;

/**
 * Created by Michael
 * Date:  2020/11/2
 * Desc:
 */
public class Demo3View3 extends View {
    private Paint paint;
    private Paint smallCirclePaint;
    private Path circlePath;

    private Random random;
    private Demo3MainView parent;
    private Point centerPos;
    private Point basePos;

    private float curRotaion = 0;

    public void setCurRotaion(float curRotaion) {
        this.curRotaion = curRotaion;
        postInvalidate();
    }

    @Override
    public void setRotation(float rotation) {
        super.setRotation(rotation);
        // postInvalidate();
    }

    public Demo3View3(Context context, Demo3MainView mainView) {
        super(context);
        parent = mainView;
        init(context);
    }

    public Demo3View3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Demo3View3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPos = new Point(0, 0);
        basePos = new Point(0, 0);
        random = new Random();

        startAni();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new Point(w / 2, h / 2);
        hasInitCircle = false;

        circlePath = new Path();
        for (int i = 0; i < 8; i++) {
            float randomOffsetX = Tools.pt2Px(random.nextInt(32) - 16);
            float randomOffsetY = Tools.pt2Px(random.nextInt(32) - 16);
            circlePath.addCircle(centerPos.x + randomOffsetX, centerPos.y + randomOffsetY, parent.getSmallRadius(), Path.Direction.CW);
        }
    }

    private float radiusOffset;
    private LerpPosition lerpPos;
    private LerpPosition[] posList = new LerpPosition[50];

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        canvas.scale(-1, 1, centerPos.x, centerPos.y);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#FFFDFDFE"));
        basePos = Tools.calCirPos(centerPos, parent.getSmallRadius(), curRotaion - 3);
        // canvas.drawCircle(basePos.x, basePos.y, parent.getCircleWidth1(), paint);

        smallCirclePaint.setColor(Color.parseColor("#FFFDFDFE"));
        // for (int i = 0; i < 3; i++) {
        final float startArc = 30 + 8;
        RadialGradient gradient = new RadialGradient(
                basePos.x, basePos.y,
                parent.getCircleWidth1() * 10,
                Color.parseColor("#FFFDFDFE"),
                Color.parseColor("#33FDFDFE"),
                Shader.TileMode.CLAMP);
        smallCirclePaint.setShader(gradient);

        // ValueAnimator transAni = ValueAnimator.ofObject(new PointFEvaluator(), new PointF(basePos.x, basePos.y), new PointF(temPos.x, temPos.y)).setDuration(1000);
        // transAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        //     @Override
        //     public void onAnimationUpdate(ValueAnimator animation) {
        //         PointF offset = (PointF) animation.getAnimatedValue();
        //         canvas.drawCircle(offset.x, offset.y, parent.getCircleWidth1(), smallCirclePaint);
        //         postInvalidate();
        //     }
        // });
        // transAni.setRepeatCount(-1);
        // transAni.setInterpolator(new LinearInterpolator());
        // transAni.start();

        for (int i = 0; i < posList.length; i++) {
            if (posList[i] == null || posList[i].isFinished()) {
                posList[i] = new LerpPosition(new PointF(basePos.x, basePos.y), startArc, 60);
            }
            PointF offset = (PointF) posList[i].getCurValue();
            canvas.drawCircle(offset.x, offset.y, parent.getCircleWidth1(), smallCirclePaint);
        }

        postInvalidate();

        // }

        // if (hasInitCircle)
        //     return;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(parent.getCircleWidth2()*0.6f);
        Shader sweepShader = new SweepGradient(
                centerPos.x, centerPos.y,
                new int[]{Color.parseColor("#CCFDFDFE"), Color.parseColor("#00FBFFFF")},
                new float[]{0, 0.96f}
        );
        paint.setShader(sweepShader);
        canvas.drawPath(circlePath, paint);
        // for (int i = 0; i < 6; i++) {
        //     float randomOffsetX = Tools.pt2Px(random.nextInt(30) - 15);
        //     float randomOffsetY = Tools.pt2Px(random.nextInt(30) - 15);
        //     canvas.drawCircle(centerPos.x + randomOffsetX, centerPos.y + randomOffsetY, parent.getSmallRadius(), paint);
        // }
        hasInitCircle = true;
    }

    private boolean hasInitCircle = false;

    private void startAni() {
        ObjectAnimator ani = ObjectAnimator.ofFloat(this, "rotation", -360f, 0f);
        ani.setDuration(2000);
        ani.setInterpolator(new LinearInterpolator());
        ani.setRepeatCount(-1);
        ani.start();
    }


    public class LerpPosition {
        PointF value1;
        Point value2;
        PointF value3;
        float v;
        private int count = 0;

        public LerpPosition(PointF value1, float startArc, float v) {
            radiusOffset = random.nextFloat() * (float) Math.PI * parent.getSmallRadius() * 2f * (startArc / 360f) / 3;
            this.value1 = value1;
            float radius = random.nextInt(100) >= 50 ? parent.getSmallRadius() + radiusOffset : parent.getSmallRadius() - radiusOffset;
            this.value2 = Tools.calCirPos(centerPos, radius, startArc);
            this.v = v;
            this.count = random.nextInt((int) v);
            value3 = new PointF((value2.x - value1.x) / v, (value2.y - value1.y) / v);
        }


        public PointF getCurValue() {
            count++;
            return new PointF(value1.x + count * value3.x, value1.y + count * value3.y);
        }

        public boolean isFinished() {
            return count >= v;
        }
    }
}
