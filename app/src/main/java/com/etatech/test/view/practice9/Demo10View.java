package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.etatech.test.R;
import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/11/13
 * Desc:
 */
public class Demo10View extends ViewGroup {
    private Paint paint;
    private Point centerPos;
    private ObjectAnimator ani;
    private Path clip;

    private Bitmap bitmap;
    private int imgWidth;
    private int imgHeight;
    private float offset;
    private float offsetY;
    private float offsetX;
    private float smallWidth;
    private int foldCount = 10;
    private int scale = 2;

    private Matrix matrix;
    private float[] src;
    private float[] dst;

    public void setOffset(float offset) {
        if (offset >= imgWidth || offset <= 0) {
            return;
        }

        this.offset = offset;
        postInvalidate();
    }

    public Demo10View(Context context) {
        super(context);
        init();
    }

    public Demo10View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        clip = new Path();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_lena);
        imgWidth = bitmap.getWidth() * scale;
        imgHeight = bitmap.getHeight() * scale;
        smallWidth = imgWidth / (float) foldCount;
        matrix = new Matrix();
        setWillNotDraw(false); // 设置ViewGroup可绘制
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new Point(w / 2, h / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(centerPos.x - imgWidth / 2, centerPos.y - imgHeight / 2);

        offsetX = offset / foldCount;
        offsetY = (int) Math.sqrt(smallWidth * smallWidth - (smallWidth - offsetX) * (smallWidth - offsetX)) / 2;

        float curWidth = smallWidth - offsetX;
        for (int i = 1; i <= foldCount; i++) {
            src = new float[]{smallWidth * (i - 1), 0,
                    smallWidth * i, 0,
                    smallWidth * i, imgHeight,
                    smallWidth * (i - 1), imgHeight};
            if (i % 2 == 1) {
                dst = new float[]{curWidth * (i - 1), 0,
                        curWidth * i, offsetY,
                        curWidth * i, imgHeight - offsetY,
                        curWidth * (i - 1), imgHeight};

            } else {
                dst = new float[]{curWidth * (i - 1), offsetY,
                        curWidth * i, 0,
                        curWidth * i, imgHeight,
                        curWidth * (i - 1), imgHeight - offsetY};
            }

            clip.reset();
            clip.moveTo(dst[0], dst[1]);
            clip.lineTo(dst[2], dst[3]);
            clip.lineTo(dst[4], dst[5]);
            clip.lineTo(dst[6], dst[7]);
            clip.close();
            canvas.save();
            canvas.clipPath(clip);

            matrix.reset();
            matrix.setPolyToPoly(src, 0, dst, 0, 4);
            matrix.preScale(scale, scale);
            canvas.concat(matrix);
            canvas.drawBitmap(bitmap, 0, 0, paint);

            float[] p = new float[2];
            p[0] = imgWidth;
            p[1] = 0;
            matrix.mapPoints(p);
            canvas.restore();
            int alpha = (int) (255 * offsetX / smallWidth);
            if (alpha <= 0) {
                alpha = 0;
            }
            if (alpha >= 255) {
                alpha = 255;
            }
            paint.setColor(Color.parseColor(String.format("#%02x333333", alpha)));
            if (i % 2 == 1) {
                canvas.drawPath(clip, paint);
            }
            paint.setColor(Color.BLACK);
            paint.setTextSize(Tools.pt2Px(50));
            canvas.drawText("拖动折叠图片", imgWidth + 10, centerPos.y, paint);
        }
    }

    PointF curPos = new PointF();
    PointF lastPos = new PointF();
    float offsetMove = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) { // TouchEvent示例 点击 拖动 抬起 取消
        curPos.set(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastPos.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                setOffset(offsetMove + lastPos.x - curPos.x);
                break;
            case MotionEvent.ACTION_UP:
                offsetMove = lastPos.x - curPos.x;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // startAni();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        endAni();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void startAni() {
        ani = ObjectAnimator.ofFloat(this, "offset", 0, imgWidth / 3f);
        ani.setDuration(3000);
        ani.setRepeatCount(-1);
        ani.setRepeatMode(ValueAnimator.REVERSE);
        ani.start();
    }

    private void endAni() {
        if (ani != null) {
            ani.end();
        }
    }

    private void printMatrix(float[] m, String name) {
        // System.out.println(String.format("Matrix %s", name));
        System.out.print("[");
        for (int i = 0; i < m.length; i++) {
            System.out.print(String.format("\t%.0f,", m[i]));
        }
        System.out.println("]");
    }
}
