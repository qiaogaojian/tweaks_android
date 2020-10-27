package com.etatech.test.view.practice9;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.blankj.utilcode.util.LogUtils;
import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/10/26
 * Desc:
 */
public class Demo1View extends View {
    private Paint paint;
    private Paint textPaint;
    private Matrix matrix;
    private Bitmap bitmap1;     // 灰手
    private Bitmap bitmap2;     // 橙手
    private Bitmap bitmap3;     // 发光
    private int curNum = 0;     // 偶数:未点赞 奇数:点赞
    private int initNum = 4309;   // 偶数:未点赞 奇数:点赞
    private float scaleUp = 1.f;
    private float scaleDown = 1.f;
    private float lightScale = 1.f;
    private float lightOffset = 0f;
    private float text1Offset = 0f;

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

    public Demo1View(Context context) {
        super(context);
    }

    public Demo1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Demo1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        matrix = new Matrix();

        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        textPaint.setTextSize(50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println(String.format("scale Down: %.2f scale Up: %.2f light scale: %.2f curNum: %d", scaleDown, scaleUp, lightScale, curNum));

        if (curNum % 2 == 0) {
            matrix.reset();
            matrix.postScale(scaleDown, scaleDown, bitmap1.getWidth() / 2, bitmap1.getHeight() + 20);

            canvas.save();
            canvas.concat(matrix);
            canvas.drawBitmap(bitmap1, 0, 30, paint);
            canvas.restore();

            drawNumSplit(canvas, curNum > 0 ? initNum + 1 : initNum, initNum);
            // canvas.drawText(String.valueOf(initNum), bitmap1.getWidth() + 10, bitmap1.getHeight() + 20, textPaint);
        } else {
            matrix.reset();
            matrix.postScale(scaleUp, scaleUp, bitmap1.getWidth() / 2, bitmap1.getHeight() + 20);
            canvas.save();
            canvas.concat(matrix);
            canvas.drawBitmap(bitmap2, 0, 30, paint);
            canvas.restore();

            matrix.reset();
            matrix.preTranslate(0, lightOffset);
            matrix.postScale(lightScale, lightScale, bitmap3.getWidth() / 2, bitmap3.getHeight() / 2);
            canvas.save();
            canvas.concat(matrix);
            canvas.drawBitmap(bitmap3, 5, 0, paint);
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
                canvas.drawText(textAfter.charAt(i) + "", bitmap1.getWidth() + 10 + textPaint.measureText(curText), bitmap1.getHeight() + 20 + text1Offset, textPaint);
            } else {
                canvas.drawText(textAfter.charAt(i) + "", bitmap1.getWidth() + 10 + textPaint.measureText(curText), bitmap1.getHeight() + 20, textPaint);
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
        ObjectAnimator text1DownAni = ObjectAnimator.ofFloat(this, "text1Offset", 0, textPaint.getTextSize());
        ObjectAnimator text1UpAni = ObjectAnimator.ofFloat(this, "text1Offset", -textPaint.getTextSize(), 0);

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
}
