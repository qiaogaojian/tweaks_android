package com.etatech.test.view.practice9;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/11/12
 * Desc:
 */
public class Demo9View extends View {
    private Paint paint;
    private Point centerPos;
    private ObjectAnimator aniOut;
    private ObjectAnimator aniIn;

    private Path pathIn;
    private Path pathOut;
    private Path pathDst;

    private int smallRadius = Tools.pt2Px(50);
    private int bigRadius = smallRadius * 2;
    private int strokeWith = Tools.pt2Px(12);
    private float maxLength;

    private float progress;
    private float progressIn;

    private int curState = 3; // 1 开始搜索 2 搜索中 3 完成搜索

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setProgressIn(float progressIn) {
        this.progressIn = progressIn;
        postInvalidate();
    }

    public Demo9View(Context context) {
        super(context);
        init();
    }

    public Demo9View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        pathIn = new Path();
        pathOut = new Path();
        pathDst = new Path();

        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(strokeWith);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new Point(w / 2, h / 2);

        pathIn.moveTo(centerPos.x + smallRadius * (float) Math.sin(Math.toRadians(45)),
                centerPos.y + smallRadius * (float) Math.cos(Math.toRadians(45)));
        pathIn.addArc(centerPos.x - smallRadius, centerPos.y - smallRadius,
                centerPos.x + smallRadius, centerPos.y + smallRadius, 45, 359.99f);
        pathIn.lineTo(centerPos.x + bigRadius * (float) Math.sin(Math.toRadians(45)),
                centerPos.y + bigRadius * (float) Math.cos(Math.toRadians(45)));

        pathOut.moveTo(centerPos.x + bigRadius * (float) Math.sin(Math.toRadians(45)),
                centerPos.y + bigRadius * (float) Math.cos(Math.toRadians(45)));
        pathOut.addArc(centerPos.x - bigRadius, centerPos.y - bigRadius,
                centerPos.x + bigRadius, centerPos.y + bigRadius, 45, 359.99f);

        maxLength = 2 * (float) Math.PI * bigRadius * 60 / 360;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pathDst.reset();
        if (curState == 2) {
            PathMeasure pathMeasure = new PathMeasure(pathOut, false);
            pathMeasure.getSegment(progress * 2 * (float) Math.PI * bigRadius,
                    progress * 2 * (float) Math.PI * bigRadius + maxLength * getLenProgress(progress),
                    pathDst, true);
        } else {
            PathMeasure pathMeasure = new PathMeasure(pathIn, false);
            pathMeasure.getSegment(progressIn * (2 * (float) Math.PI * smallRadius + smallRadius),
                    (2 * (float) Math.PI * smallRadius + smallRadius),
                    pathDst, true);
        }
        canvas.drawPath(pathDst, paint);
    }

    private float getLenProgress(float progress) {
        if (progress > 0.5f) {
            return 1 - progress;
        }
        return progress;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAni();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        endAni();
    }

    private void startAni() {
        aniOut = ObjectAnimator.ofFloat(this, "progress", 0, 1f);
        aniOut.setDuration(2000);
        aniOut.setRepeatCount(1);
        aniOut.setRepeatMode(ValueAnimator.RESTART);
        aniOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                aniIn.start();
                LogUtils.d("搜索完成");
                curState = 3;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        aniIn = ObjectAnimator.ofFloat(this, "progressIn", 1f, 0);
        aniIn.setDuration(2000);
        aniIn.setRepeatMode(ValueAnimator.REVERSE);
        aniIn.setRepeatCount(1);
        aniIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                aniOut.start();
                LogUtils.d("搜索加载中");
                curState = 2;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                LogUtils.d("开始搜索");
                curState = 1;
            }
        });
        aniIn.start();
    }

    private void endAni() {
        aniOut.end();
    }
}
