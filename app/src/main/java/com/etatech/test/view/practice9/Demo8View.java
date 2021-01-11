package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/11/12
 * Desc:
 */
public class Demo8View extends View {
    private Paint paint;
    private Path pathCicleBig;
    private Path pathCicleBigWhite;
    private Path pathCiclesmallT;
    private Path pathCiclesmallB;
    private Path pathRectClip;
    private int bigRadius = Tools.pt2Px(200);
    private Point centerPos;

    private int r;

    public void setR(int r) {
        this.r = r;
        postInvalidate();
    }

    public Demo8View(Context context) {
        super(context);
        init();
    }

    public Demo8View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        pathCicleBig = new Path();
        pathCicleBigWhite = new Path();
        pathCiclesmallT = new Path();
        pathCiclesmallB = new Path();
        pathRectClip = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new Point(w / 2, h / 2);

        pathCicleBig.addCircle(centerPos.x, centerPos.y, bigRadius, Path.Direction.CW);
        pathCicleBigWhite.addCircle(centerPos.x, centerPos.y, bigRadius, Path.Direction.CW);
        pathCiclesmallT.addCircle(centerPos.x, centerPos.y - bigRadius / 2, bigRadius / 2, Path.Direction.CW);
        pathCiclesmallB.addCircle(centerPos.x, centerPos.y + bigRadius / 2, bigRadius / 2, Path.Direction.CW);
        pathRectClip.addRect(centerPos.x, centerPos.y + bigRadius, centerPos.x + bigRadius, centerPos.y - bigRadius, Path.Direction.CW);

        pathCicleBig.op(pathRectClip, Path.Op.INTERSECT);
        pathCicleBig.op(pathCiclesmallT, Path.Op.DIFFERENCE);
        pathCicleBig.op(pathCiclesmallB, Path.Op.UNION);
        pathCicleBig.addCircle(centerPos.x, centerPos.y - bigRadius / 2, bigRadius / 6, Path.Direction.CW);
        pathCicleBigWhite.op(pathCicleBig, Path.Op.DIFFERENCE);
        pathCicleBigWhite.addCircle(centerPos.x, centerPos.y + bigRadius / 2, bigRadius / 6, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.rotate(r, centerPos.x, centerPos.y);
        paint.setColor(Color.BLACK);
        canvas.drawPath(pathCicleBig, paint);
        paint.setColor(Color.WHITE);
        canvas.drawPath(pathCicleBigWhite, paint);
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

    private ObjectAnimator ani;

    private void startAni() {
        ani = ObjectAnimator.ofInt(this, "r", 0, 360);
        ani.setDuration(6000);
        ani.setRepeatCount(-1);
        ani.setInterpolator(new LinearInterpolator());
        ani.setRepeatMode(ValueAnimator.RESTART);
        ani.start();
    }

    private void endAni() {

    }
}
