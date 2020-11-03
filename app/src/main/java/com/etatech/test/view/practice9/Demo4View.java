package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/11/2
 * Desc:
 */
public class Demo4View extends View {
    private Paint paint;
    private Bitmap bitmap;
    private PointF centerPos;

    private Camera camera;
    private int degree = 45;
    private int curDegree = 0;
    private Matrix matrix;

    public void setCurDegree(int curDegree) {
        this.curDegree = curDegree;
        postInvalidate();
    }

    public Demo4View(Context context) {
        super(context);
        init();
    }

    public Demo4View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo4View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        camera = new Camera();
        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.what_the_fuck);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new PointF(w / 2f, h / 2f);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        rotateAni();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ani.end();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        foldImage(canvas);
    }

    private void foldImage(Canvas canvas) {

        canvas.save();
        camera.save();
        matrix.reset();
        camera.rotateX(-degree * (float) Math.sin(Math.toRadians(curDegree)));
        camera.rotateY(-degree * (float) Math.cos(Math.toRadians(curDegree)));
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerPos.x, -centerPos.y);
        matrix.postTranslate(centerPos.x, centerPos.y);
        canvas.concat(matrix);

        canvas.drawBitmap(bitmap, centerPos.x - bitmap.getWidth() / 2, centerPos.y - bitmap.getHeight() / 2, paint);
        canvas.restore();
        paint.setTextSize(66);
        paint.setColor(Color.BLACK);
        canvas.drawText(curDegree + "", 0, centerPos.y, paint);
    }

    ObjectAnimator ani;

    private void rotateAni() {
        ani = ObjectAnimator.ofInt(this, "curDegree", 0, 360);
        ani.setDuration(6000);
        ani.setRepeatCount(-1);
        ani.setRepeatMode(ValueAnimator.RESTART);
        ani.setInterpolator(new LinearInterpolator());
        ani.start();
    }
}
