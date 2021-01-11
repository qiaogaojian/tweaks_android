package com.etatech.test.view.custom.shader;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/6/10
 * Func:
 */
public class TestLinearGradientShaderView extends View {
    private int offset = 0;
    private Paint rectPaint = new Paint();
    private Paint textPaint = new Paint();
    private LinearGradient linearGradient;
    private int viewHeight;
    private int viewWidth;
    private int textHeight;
    private int textWidth;
    private int textTopPos;
    private int textDownPos;
    private int textLeftPos;
    private int textRightPos;

    private String text;
    private int textSize;
    private int gradientWidth;

    // 渐变位置
    private float[] positions = {0.3f, 0.6f, 0.8f};
    // 渐变颜色
    private int[] colors = {Color.parseColor("#3c3c3c"), Color.parseColor("#ffffff"), Color.parseColor("#3c3c3c")};

    public TestLinearGradientShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.TestLinearGradientShaderView);
        text = arr.getString(R.styleable.TestLinearGradientShaderView_gradient_text);
        textSize = arr.getInteger(R.styleable.TestLinearGradientShaderView_gradient_text_size, 30);
        gradientWidth = arr.getInteger(R.styleable.TestLinearGradientShaderView_gradient_width, (int) textPaint.measureText(text));

        textPaint.setTextSize(textSize);
        arr.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = (int) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
        textWidth = (int) textPaint.measureText(text);
        textTopPos = (int) Math.ceil((viewHeight - textHeight) / 2.0);
        textDownPos = (int) Math.ceil((viewHeight + textHeight) / 2.0);
        textLeftPos = (int) Math.ceil((viewWidth - textWidth) / 2.0);
        textRightPos = (int) Math.ceil((viewWidth + textWidth) / 2.0);

        int leftBound = -gradientWidth;
        int rightBound = viewWidth;

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(leftBound, rightBound);
        valueAnimator.setDuration(10000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                linearGradient = new LinearGradient(
                        offset, textTopPos,
                        gradientWidth + offset, textDownPos,
                        colors, positions, Shader.TileMode.CLAMP);
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
        canvas.drawText(text, textLeftPos, textDownPos, textPaint);

        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(3);
        canvas.drawRect(new Rect(offset, textTopPos,
                gradientWidth + offset, textDownPos), rectPaint);
    }
}
