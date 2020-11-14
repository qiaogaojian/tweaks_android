package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.etatech.test.R;

import java.util.ArrayList;

/**
 * Created by Michael
 * Date:  2020/11/13
 * Desc:
 */
public class Demo11View extends View {
    private Paint paint;
    private Point centerPos;
    private ObjectAnimator ani;

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
    private float[] src = new float[8];
    private float[] dst = new float[8];
    private ArrayList<Bitmap> bitmapArr = new ArrayList<>();

    public void setOffset(float offset) {
        this.offset = offset;
        postInvalidate();
    }

    public Demo11View(Context context) {
        super(context);
        init();
    }

    public Demo11View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_lena);
        imgWidth = bitmap.getWidth();
        imgHeight = bitmap.getHeight();
        smallWidth = imgWidth / (float) foldCount;
        matrix = new Matrix();
        for (int i = 0; i < foldCount; i++) {
            bitmapArr.add(Bitmap.createBitmap(bitmap, (int) (smallWidth * i), 0, (int) Math.ceil(smallWidth), imgHeight));
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
        //        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.save();
        canvas.translate(centerPos.x - imgWidth * scale / 2f, centerPos.y - imgHeight * scale / 2f);

        offsetX = offset / foldCount;
        offsetY = (int) Math.sqrt(smallWidth * smallWidth - (smallWidth - offsetX) * (smallWidth - offsetX)) * 2;

        float curWidth = smallWidth - offsetX;
        for (int i = 1; i <= foldCount; i++) {
            boolean single = i % 2 == 1;
            if (single) {
                src[0] = 0;
                src[1] = 0;
                src[2] = smallWidth;
                src[3] = 0;
                src[4] = smallWidth;
                src[5] = imgHeight;
                src[6] = 0;
                src[7] = imgHeight;
                dst[0] = 0;
                dst[1] = 0;
                dst[2] = scale * curWidth;
                dst[3] = scale * offsetY;
                dst[4] = scale * curWidth;
                dst[5] = scale * imgHeight - scale * offsetY;
                dst[6] = 0;
                dst[7] = scale * imgHeight;
            } else {
                src[0] = -smallWidth;
                src[1] = 0;
                src[2] = 0;
                src[3] = 0;
                src[4] = 0;
                src[5] = imgHeight;
                src[6] = -smallWidth;
                src[7] = imgHeight;
                dst[0] = scale * -curWidth;
                dst[1] = scale * offsetY;
                dst[2] = 0;
                dst[3] = 0;
                dst[4] = 0;
                dst[5] = scale * imgHeight;
                dst[6] = scale * -curWidth;
                dst[7] = scale * imgHeight - scale * offsetY;

            }
            canvas.save();
            matrix.reset();
            matrix.setPolyToPoly(src, 0, dst, 0, 4);
            canvas.translate(scale * curWidth * (single ? (i - 1) : i), 0);
            canvas.concat(matrix);
            canvas.drawBitmap(bitmapArr.get(i - 1), (single ? 0 : -smallWidth), 0, paint);
            canvas.restore();

        }
        canvas.restore();
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
}
