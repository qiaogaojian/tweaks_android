package com.etatech.test.view.practice9;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;

import com.etatech.test.R;
import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/10/30
 * Desc:
 */
public class Demo3MainView extends ViewGroup {
    private Paint paint;
    private Bitmap bgBitmap;
    private int width;
    private int height;
    private Matrix matrix = new Matrix();

    private Demo3View view1;
    private int stepNum = 0;

    private Demo3View2 view2;
    private int smallRadius = Tools.pt2Px(250);
    private int circleWidth1 = Tools.pt2Px(9);
    private int circleWidth2 = Tools.pt2Px(6);

    private Demo3View3 view3;

    public int getStepNum() {
        return stepNum;
    }

    public float getStepProgress() {
        return stepNum / 1000f;
    }

    public void setStepNum(float stepNum) {
        this.stepNum = (int) stepNum;
        view1.postInvalidate();
    }

    public int getSmallRadius() {
        return smallRadius;
    }

    public void setSmallRadius(int smallRadius) {
        this.smallRadius = smallRadius;
    }

    public int getCircleWidth1() {
        return circleWidth1;
    }

    public void setCircleWidth1(int circleWidth1) {
        this.circleWidth1 = circleWidth1;
    }

    public int getCircleWidth2() {
        return circleWidth2;
    }

    public void setCircleWidth2(int circleWidth2) {
        this.circleWidth2 = circleWidth2;
    }

    public Demo3MainView(Context context) {
        super(context);
        init(context);
    }

    public Demo3MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Demo3MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_step_law);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view1 = new Demo3View(context, this);
        view1.setLayoutParams(layoutParams);
        addView(view1);
        view2 = new Demo3View2(context, this);
        view2.setLayoutParams(layoutParams);
        addView(view2);
        view3 = new Demo3View3(context, this);
        view3.setLayoutParams(layoutParams);
        addView(view3);

        setWillNotDraw(false); // 设置ViewGroup可绘制

        view2.setVisibility(GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view3.setVisibility(INVISIBLE);
                view2.setVisibility(VISIBLE);
                AnimatorSet aniSet = new AnimatorSet();
                ObjectAnimator ani = ObjectAnimator.ofFloat(view2, "scaleX", 1.2f, 1f);
                ObjectAnimator ani2 = ObjectAnimator.ofFloat(view2, "scaleY", 1.2f, 1f);
                aniSet.playTogether(ani, ani2);
                aniSet.setInterpolator(new AnticipateOvershootInterpolator());
                aniSet.setDuration(500);
                ani.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        stepRotation();
                        view2.setDrawArc(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view2.setDrawArc(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                aniSet.start();
            }
        }, 300000);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getVisibility() != GONE) {
                measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        view1.layout(0, 0, r - l, b - t);
        view2.layout(0, 0, r - l, b - t);
        view3.layout(0, 0, r - l, b - t);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.reset();
        float scale = (float) width / bgBitmap.getWidth();
        matrix.postScale(scale, scale);
        canvas.drawBitmap(bgBitmap, matrix, paint); // 控制图片的大小
    }

    private void stepRotation() {
        Keyframe frame1 = Keyframe.ofFloat(0, 0);
        Keyframe frame2 = Keyframe.ofFloat(0.5f, 1000);
        Keyframe frame3 = Keyframe.ofFloat(1, 666);
        // 属性的类型一定要对, 默认是float, keyframe不会检查类型
        PropertyValuesHolder property = PropertyValuesHolder.ofKeyframe("stepNum", frame1, frame2, frame3);
        ObjectAnimator.ofPropertyValuesHolder(this, property).setDuration(3000).start();
    }
}
