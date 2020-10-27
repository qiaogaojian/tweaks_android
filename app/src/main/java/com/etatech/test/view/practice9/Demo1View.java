package com.etatech.test.view.practice9;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.etatech.test.BuildConfig;
import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/10/26
 * Desc:
 */
public class Demo1View extends View {
    private Paint paint;
    private Paint bgPaint;
    private Paint textPaint;
    private Matrix matrix;
    private Bitmap bitmap1;     // 灰手
    private Bitmap bitmap2;     // 橙手
    private Bitmap bitmap3;     // 发光
    private ThumbAlign align;
    private int initNum = 0;    // 偶数:未点赞 奇数:点赞
    private int curNum = 0;     // 偶数:未点赞 奇数:点赞
    private float scaleUp = 1.f;
    private float scaleDown = 1.f;
    private float lightScale = 1.f;
    private float lightOffset = 0f;
    private float text1Offset = 0f;
    private Point topLeftPos;
    private Point centerPos;


    Path path = new Path();

    public float getScaleUp() {
        return scaleUp;
    }

    public void setScaleUp(float scaleUp) {
        this.scaleUp = scaleUp;
        postInvalidate();
    }

    public float getScaleDown() {
        return scaleDown;
    }

    public void setScaleDown(float scaleDown) {
        this.scaleDown = scaleDown;
        postInvalidate();
    }

    public float getLightScale() {
        return lightScale;
    }

    public void setLightScale(float lightScale) {
        this.lightScale = lightScale;
        postInvalidate();
    }

    public float getLightOffset() {
        return lightOffset;
    }

    public void setLightOffset(float lightOffset) {
        this.lightOffset = lightOffset;
        postInvalidate();
    }

    public float getText1Offset() {
        return text1Offset;
    }

    public void setText1Offset(float text1Offset) {
        this.text1Offset = text1Offset;
        postInvalidate();
    }

    public void setInitNum(int num) {
        initNum = num;
        postInvalidate();
    }

    public Demo1View(Context context) {
        super(context);
    }

    public Demo1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.Demo1View);
        initNum = attr.getInteger(R.styleable.Demo1View_number, 0);

        align = ThumbAlign.values()[attr.getInt(R.styleable.Demo1View_align, 0)];
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        matrix = new Matrix();
        topLeftPos = new Point(0, 0);
        centerPos = new Point(0, 0);

        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        textPaint.setTextSize(50);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = 0;
        int h = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                w = bitmap1.getWidth() + 20 + (int) textPaint.measureText(String.valueOf(initNum));
                break;
            case MeasureSpec.EXACTLY:
                w = getMeasuredWidth();
                break;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                h = bitmap1.getHeight() + 50;
                break;
            case MeasureSpec.EXACTLY:
                h = getMeasuredHeight();
                break;
        }

        resolveSize(w, widthMeasureSpec);
        resolveSize(h, heightMeasureSpec);

        centerPos.x = w / 2;
        centerPos.y = h / 2;
        if (align == ThumbAlign.left) {
            topLeftPos.x = 0;
        } else {
            topLeftPos.x = centerPos.x - (bitmap1.getWidth() + 20 + (int) textPaint.measureText(String.valueOf(initNum))) / 2;
        }
        topLeftPos.y = centerPos.y - (bitmap1.getHeight() + 50) / 2;

        path.moveTo(0, centerPos.y);
        path.lineTo(centerPos.x * 2, centerPos.y);
        path.moveTo(centerPos.x, 0);
        path.lineTo(centerPos.x, centerPos.y * 2);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println(String.format("scale Down: %.2f scale Up: %.2f light scale: %.2f curNum: %d", scaleDown, scaleUp, lightScale, curNum));
        // 辅助绘制
        if (BuildConfig.DEBUG) {
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setPathEffect(new DashPathEffect(new float[]{10, 9}, 0));
            canvas.drawPath(path, paint);
        }
        bgPaint.setColor(Color.parseColor("#66A0DA2D"));
        canvas.drawRoundRect(0, 0, centerPos.x * 2, centerPos.y * 2, 15, 15, bgPaint);

        if (curNum % 2 == 0) {
            matrix.reset();
            matrix.postScale(scaleDown, scaleDown, bitmap1.getWidth() / 2 + topLeftPos.x, bitmap1.getHeight() + 20 + topLeftPos.y);

            canvas.save();
            canvas.concat(matrix);
            canvas.drawBitmap(bitmap1, 0 + topLeftPos.x, 30 + topLeftPos.y, paint);
            canvas.restore();

            drawNumSplit(canvas, curNum > 0 ? initNum + 1 : initNum, initNum);
            // canvas.drawText(String.valueOf(initNum), bitmap1.getWidth() + 10, bitmap1.getHeight() + 20, textPaint);
        } else {
            matrix.reset();
            matrix.postScale(scaleUp, scaleUp, bitmap1.getWidth() / 2 + topLeftPos.x, bitmap1.getHeight() + 20 + topLeftPos.y);
            canvas.save();
            canvas.concat(matrix);
            canvas.drawBitmap(bitmap2, 0 + topLeftPos.x, 30 + topLeftPos.y, paint);
            canvas.restore();

            matrix.reset();
            matrix.preTranslate(0, lightOffset);
            matrix.postScale(lightScale, lightScale, bitmap3.getWidth() / 2 + topLeftPos.x, bitmap3.getHeight() / 2 + topLeftPos.y);
            canvas.save();
            canvas.concat(matrix);
            canvas.drawBitmap(bitmap3, 5 + topLeftPos.x, 0 + topLeftPos.y, paint);
            canvas.restore();

            drawNumSplit(canvas, initNum, initNum + 1);
            // canvas.drawText(String.valueOf(initNum + 1), bitmap1.getWidth() + 10, bitmap1.getHeight() + 20, textPaint);
        }
    }

    private void drawNumSplit(Canvas canvas, int numBefore, int num) {
        String textBefore = String.valueOf(numBefore);
        String textAfter = String.valueOf(num);
        String curText = "";
        for (int i = 0; i < textAfter.length(); i++) {
            if (textBefore.length() < textAfter.length()) {  // after +1 进位 首位为1, before用9补首位用来统一判断
                textBefore = '9' + textBefore;
            }

            if (textBefore.charAt(i) != textAfter.charAt(i)) {
                canvas.drawText(textAfter.charAt(i) + "",
                        bitmap1.getWidth() + 10 + textPaint.measureText(curText) + topLeftPos.x,
                        bitmap1.getHeight() + 20 + text1Offset + topLeftPos.y,
                        textPaint);
            } else {
                canvas.drawText(textAfter.charAt(i) + "",
                        bitmap1.getWidth() + 10 + textPaint.measureText(curText) + topLeftPos.x,
                        bitmap1.getHeight() + 20 + topLeftPos.y,
                        textPaint);
            }

            curText += textAfter.charAt(i);
        }
    }

    private void showAni() {
        System.out.println(String.format("scale Down: %.2f scale Up: %.2f curNum: %d  showAni()", scaleDown, scaleUp, curNum));
        AnimatorSet aniSet = new AnimatorSet();
        ObjectAnimator downAni = ObjectAnimator.ofFloat(this, "scaleDown", 1.0f, 0.8f);
        ObjectAnimator upAni = ObjectAnimator.ofFloat(this, "scaleUp", 0.8f, 1.0f);
        ObjectAnimator lightUpAni = ObjectAnimator.ofFloat(this, "lightScale", 0.3f, 1f);
        ObjectAnimator lightTransAni = ObjectAnimator.ofFloat(this, "lightOffset", 60f, 0f);
        ObjectAnimator text1DownAni = ObjectAnimator.ofFloat(this, "text1Offset", 0, -textPaint.getTextSize());
        ObjectAnimator text1UpAni = ObjectAnimator.ofFloat(this, "text1Offset", textPaint.getTextSize(), 0);

        aniSet.play(upAni).with(lightUpAni).with(lightTransAni).with(text1UpAni).after(downAni).after(text1DownAni);
        upAni.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                curNum++;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        aniSet.setDuration(100);
        aniSet.setInterpolator(new OvershootInterpolator());
        aniSet.start();
    }

    private void hideAni() {
        System.out.println(String.format("scale Down: %.2f scale Up: %.2f curNum: %d  hideAni()", scaleDown, scaleUp, curNum));
        AnimatorSet aniSet = new AnimatorSet();
        ObjectAnimator downAni = ObjectAnimator.ofFloat(this, "scaleUp", 1.0f, 0.8f);
        ObjectAnimator upAni = ObjectAnimator.ofFloat(this, "scaleDown", 0.8f, 1.0f);
        ObjectAnimator lightDownAni = ObjectAnimator.ofFloat(this, "lightScale", 1f, 0.3f);
        ObjectAnimator lightTransAni = ObjectAnimator.ofFloat(this, "lightOffset", 0f, 60f);
        ObjectAnimator text1DownAni = ObjectAnimator.ofFloat(this, "text1Offset", 0, textPaint.getTextSize());
        ObjectAnimator text1UpAni = ObjectAnimator.ofFloat(this, "text1Offset", -textPaint.getTextSize(), 0);

        aniSet.play(downAni).with(lightDownAni).with(lightTransAni).with(text1DownAni).before(upAni).before(text1UpAni);
        upAni.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                curNum++;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        aniSet.setDuration(100);
        aniSet.setInterpolator(new OvershootInterpolator());
        aniSet.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (curNum % 2 == 0) {
                showAni();
            } else {
                hideAni();
            }
        }
        return super.onTouchEvent(event);
    }

    public enum ThumbAlign {
        left,
        center
    }
}
