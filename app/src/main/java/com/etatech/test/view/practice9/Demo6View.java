package com.etatech.test.view.practice9;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.etatech.test.R;
import com.etatech.test.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael
 * Date:  2020/11/11
 * Desc:
 */
public class Demo6View extends View {
    private Paint paint;
    private Paint helperPaint;
    private Point centerPos;
    private Bitmap bitmapLeaf;
    private Bitmap bitmapFrame;
    private Bitmap bitmapFan;
    private int frameWidth;
    private int frameHeight;
    private int fanWidth;
    private int fanHeight;
    private int leafWidth;
    private int leafHeight;
    private Rect srcFrame;
    private Rect dstFrame;
    private Rect srcFan;
    private Rect dstFan;
    private Rect srcLeaf;
    private Rect dstLeaf;
    private int fanOffset = Tools.pt2Px(4);

    private ObjectAnimator ani;
    private float progress;
    private List<Integer> periodList;
    private List<Float> speedList;
    private List<LerpPosition> posList;
    private int leafCount = 10;
    private Path path;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public Demo6View(Context context) {
        super(context);
        init();
    }

    public Demo6View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        helperPaint = new Paint();
        bitmapLeaf = BitmapFactory.decodeResource(getResources(), R.drawable.loading_leaf);
        bitmapFrame = BitmapFactory.decodeResource(getResources(), R.drawable.loading_frame);
        bitmapFan = BitmapFactory.decodeResource(getResources(), R.drawable.loading_fan);

        frameWidth = bitmapFrame.getWidth();
        frameHeight = bitmapFrame.getHeight();
        fanWidth = bitmapFan.getWidth();
        fanHeight = bitmapFan.getHeight();
        leafWidth = bitmapLeaf.getWidth();
        leafHeight = bitmapLeaf.getHeight();

        srcFrame = new Rect();
        dstFrame = new Rect();
        srcFan = new Rect();
        dstFan = new Rect();
        srcLeaf = new Rect();
        dstLeaf = new Rect();
        periodList = new ArrayList<>();
        speedList = new ArrayList<>();
        posList = new ArrayList<>();
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new Point(w / 2, h / 2);
        Point base = new Point(centerPos.x + frameWidth - leafWidth, centerPos.y - leafHeight);
        for (int i = 0; i < leafCount; i++) {
            posList.add(new LerpPosition(base, i % 2 == 0));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textSize = Tools.pt2Px(36);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.MONOSPACE);
        String persent = String.format("%.0f", progress * 100) + '%';
        canvas.drawText(persent, centerPos.x - paint.measureText(persent) / 2, centerPos.y + frameHeight + textSize, paint);

        path.reset();
        path.moveTo(centerPos.x + frameWidth - frameHeight, centerPos.y - frameHeight);
        path.arcTo(centerPos.x + frameWidth - frameHeight * 2,
                centerPos.y - frameHeight,
                centerPos.x + frameWidth,
                centerPos.y + frameHeight, -90, 180, false);
        path.lineTo(centerPos.x - frameWidth + frameHeight, centerPos.y + frameHeight);
        path.arcTo(centerPos.x - frameWidth,
                centerPos.y - frameHeight,
                centerPos.x - frameWidth + frameHeight * 2,
                centerPos.y + frameHeight, 90, 180, false);
        path.lineTo(centerPos.x + frameWidth - frameHeight, centerPos.y - frameHeight);
        path.close();
        canvas.clipPath(path);

        srcLeaf.set(0, 0, leafWidth, leafHeight);
        dstLeaf.set(0, 0, leafWidth * 2, leafHeight * 2);
        for (int i = 0; i < leafCount; i++) {
            RectF curLeaf = posList.get(i).getCurPos();
            canvas.save();
            canvas.translate(curLeaf.left + leafWidth, curLeaf.top + leafHeight);
            canvas.rotate(posList.get(i).getCurRotation());
            canvas.drawBitmap(bitmapLeaf, srcLeaf, dstLeaf, paint);
            canvas.restore();
        }

        paint.setColor(Color.parseColor("#FFFDB80F"));
        canvas.drawRect(centerPos.x - frameWidth,
                centerPos.y - frameHeight,
                centerPos.x - frameWidth + frameWidth * 2 * progress,
                centerPos.y + frameHeight,
                paint);

        helperPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerPos.x + frameWidth - frameHeight, centerPos.y, frameHeight, helperPaint);
        helperPaint.setColor(Color.parseColor("#FFCCCCCC"));
        canvas.drawCircle(centerPos.x + frameWidth - frameHeight, centerPos.y, frameHeight - Tools.pt2Px(10), helperPaint);

        srcFan.set(0, 0, fanWidth, fanHeight);
        dstFan.set(centerPos.x + frameWidth - fanWidth * 2 - fanOffset * 2,
                centerPos.y - fanHeight,
                centerPos.x + frameWidth - fanOffset * 2,
                centerPos.y + fanHeight
        );
        canvas.save();
        canvas.rotate(360 * progress * 10, dstFan.centerX(), dstFan.centerY());
        canvas.drawBitmap(bitmapFan, srcFan, dstFan, paint);
        canvas.restore();

        helperPaint.setStyle(Paint.Style.STROKE);
        helperPaint.setStrokeWidth(Tools.pt2Px(20));
        helperPaint.setColor(Color.parseColor("#FFFCE195"));
        canvas.drawPath(path, helperPaint);

        postInvalidate();
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

    private void startAni() {
        ani = ObjectAnimator.ofFloat(this, "progress", 0, 1f);
        ani.setDuration(5000);
        ani.setRepeatMode(ValueAnimator.RESTART);
        ani.setRepeatCount(-1);
        ani.setInterpolator(new LinearInterpolator());
        ani.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                for (int i = 0; i < posList.size(); i++) {
                    posList.get(i).reset();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                for (int i = 0; i < posList.size(); i++) {
                    posList.get(i).reset();
                }
            }
        });
        ani.start();
    }

    private void endAni() {
        ani.end();
    }

    private int getLeafOffsetY(boolean isSin, int period) {
        int res;
        if (isSin) {
            res = (int) (Math.sin(progress * frameWidth * 2 / period) * fanHeight);
        } else {
            res = (int) (Math.cos(progress * frameWidth * 2 / period) * fanHeight);
        }
        return res - fanHeight / 2;
    }

    private int getLeafOffsetX(int leafIndex) {
        int res = (int) (Math.round(frameWidth * 2 * progress) * speedList.get(leafIndex));
        res = (int) (res - speedList.get(leafIndex) * frameWidth);
        return res;
    }

    public class LerpPosition {
        private Point base;
        private RectF target;
        private float offsetX;
        private float offsetY;
        private int frameNum;
        private Boolean isSin;
        private int period;
        private int delay;
        private int startRotation;
        private int randomHeight;

        public LerpPosition(Point base, Boolean isSin) {
            this.base = base;
            this.target = new RectF();
            this.isSin = isSin;

            offsetX = Tools.randomRange(100, 300) / 100f;
            period = Tools.randomRange(frameHeight, frameHeight * 2);
            delay = Tools.randomRange(100) - 30;
            startRotation = Tools.randomRange(360);
            randomHeight = Tools.randomRange(fanHeight / 3, fanHeight);
        }

        public RectF getCurPos() {
            if (isFinished()) {
                frameNum = 0;
                delay = Tools.randomRange(100) - 30;
            }

            float posX = base.x - offsetX * (frameNum - delay);
            frameNum++;

            if (isSin) {
                offsetY = (int) (Math.sin(posX / period) * randomHeight);
            } else {
                offsetY = (int) (Math.cos(posX / period) * randomHeight);
            }
            float posY = base.y + offsetY;
            target.set(posX, posY, posX + leafWidth * 2, posY + leafHeight * 2);
            return target;
        }

        private void reset() {
            frameNum = 0;
        }

        public int getCurRotation() {
            return startRotation + frameNum;
        }

        public boolean isFinished() {
            return target.left < centerPos.x - frameWidth;
        }
    }
}
