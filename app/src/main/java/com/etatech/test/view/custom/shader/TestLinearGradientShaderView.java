package com.etatech.test.view.custom.shader;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Michael
 * Date:  2020/6/10
 * Func:
 */
public class TestLinearGradientShaderView extends View {
    private int offset = 0;
    private Paint textPaint;
    private Paint rectPaint;
    private LinearGradient linearGradient;
    // 渐变位置
    private float[] positions = {0.3f, 0.6f, 0.8f};
    // 渐变颜色
    private int[] colors = {Color.parseColor("#3c3c3c"), Color.parseColor("#ffffff"), Color.parseColor("#3c3c3c")};

    public TestLinearGradientShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        final int height = getMeasuredHeight();
        final int width = getMeasuredWidth();

        rectPaint = new Paint();
        textPaint = new Paint();
        textPaint.setTextSize(100);

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(-1000, 1000);
        valueAnimator.setDuration(10000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                linearGradient = new LinearGradient(offset, 300, 1000 + offset, 600, colors, positions, Shader.TileMode.CLAMP);
                textPaint.setShader(linearGradient);
                rectPaint.setShader(linearGradient);
                invalidate();
            }
        });

        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#232323"));
        textPaint.setAlpha(255);
        canvas.drawText("TEST SHADER", 200, 500, textPaint);

        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(3);
        canvas.drawRect(new Rect(offset, 300, 1000 + offset, 600), rectPaint);
    }
}
