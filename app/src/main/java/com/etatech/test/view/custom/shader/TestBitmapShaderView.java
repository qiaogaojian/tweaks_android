package com.etatech.test.view.custom.shader;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/6/10
 * Func:
 */

public class TestBitmapShaderView extends View {
    private Paint bitmapPaint;

    public TestBitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmapPaint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        bitmapPaint.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bitmapPaint.setTextSize(200);
//        bitmapPaint.setStyle(Paint.Style.FILL);
        bitmapPaint.setAlpha(255);
        canvas.drawText("TEST BITMAP", 100, 500, bitmapPaint);
    }
}
