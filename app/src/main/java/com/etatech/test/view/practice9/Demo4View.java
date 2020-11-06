package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
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
    private Paint helperPaint;
    private Bitmap bitmap;
    private PointF centerPos;

    private Camera camera;
    private Matrix matrix;
    private int degree = 60;
    private int curDegree = 0;
    Path path = new Path();

    public void setCurDegree(int curDegree) {
        this.curDegree = curDegree;
        invalidate();
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
        helperPaint = new Paint();
        helperPaint.setStyle(Paint.Style.STROKE);
        helperPaint.setStrokeWidth(5);
        helperPaint.setColor(Color.BLACK);
        helperPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
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
        drawBg(canvas);
    }

    private void foldImage(Canvas canvas) {

        canvas.save();
        camera.save();

        canvas.translate(centerPos.x, centerPos.y);
        canvas.rotate(curDegree);

        canvas.drawLine(-centerPos.x, 0, centerPos.x, 0, paint);
        canvas.clipRect(-centerPos.x, 0, centerPos.x, centerPos.y);

        camera.rotateX(degree);
        camera.applyToCanvas(canvas);

        canvas.rotate(-curDegree);
        canvas.translate(-centerPos.x, -centerPos.y);

        canvas.drawBitmap(bitmap, centerPos.x - bitmap.getWidth() / 2, centerPos.y - bitmap.getHeight() / 2, paint);
        drawCoord(canvas);

        camera.restore();
        canvas.restore();


        // 原图参考数据
        // canvas.restore();
        // paint.setTextSize(66);
        // paint.setColor(Color.BLACK);
        // canvas.drawText(curDegree + "", 0, centerPos.y, paint);
        // canvas.drawBitmap(bitmap, centerPos.x * 2 - bitmap.getWidth() - 30, centerPos.y - bitmap.getHeight() / 2, paint);
    }

    private void drawBg(Canvas canvas) {

        canvas.save();
        camera.save();

        canvas.translate(centerPos.x, centerPos.y);
        canvas.rotate(curDegree);

        canvas.clipRect(-centerPos.x, -centerPos.y, centerPos.x, 0);

        // camera.rotateX(-degree);
        // camera.applyToCanvas(canvas);

        canvas.rotate(-curDegree);
        canvas.translate(-centerPos.x, -centerPos.y);

        canvas.drawBitmap(bitmap, centerPos.x - bitmap.getWidth() / 2, centerPos.y - bitmap.getHeight() / 2, paint);
        drawCoord(canvas);

        camera.restore();
        canvas.restore();
    }

    ObjectAnimator ani;

    private void rotateAni() {
        ani = ObjectAnimator.ofInt(this, "curDegree", 0, 360);
        ani.setDuration(60000);
        ani.setRepeatCount(-1);
        ani.setRepeatMode(ValueAnimator.RESTART);
        ani.setInterpolator(new LinearInterpolator());
        ani.start();
    }

    private void drawCoord(Canvas canvas) {
        path.reset();
        path.moveTo(0, 0);
        path.lineTo(centerPos.x * 2, 0);
        path.lineTo(centerPos.x * 2, centerPos.y * 2);
        path.lineTo(0, centerPos.y * 2);
        path.close();

        path.moveTo(centerPos.x, 0);
        path.lineTo(centerPos.x, centerPos.y * 2);
        path.moveTo(0, centerPos.y);
        path.lineTo(centerPos.x * 2, centerPos.y);

        path.moveTo(centerPos.x - bitmap.getWidth() / 2, centerPos.y - bitmap.getWidth() / 2);
        path.lineTo(centerPos.x + bitmap.getWidth() / 2, centerPos.y - bitmap.getWidth() / 2);
        path.lineTo(centerPos.x + bitmap.getWidth() / 2, centerPos.y + bitmap.getWidth() / 2);
        path.lineTo(centerPos.x - bitmap.getWidth() / 2, centerPos.y + bitmap.getWidth() / 2);
        path.close();

        canvas.drawText("X", centerPos.x + bitmap.getWidth() + 200, centerPos.y, paint);

        canvas.drawPath(path, helperPaint);
    }
}
