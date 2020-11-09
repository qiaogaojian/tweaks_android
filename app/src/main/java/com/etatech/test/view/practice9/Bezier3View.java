package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/11/6
 * Desc:
 */
public class Bezier3View extends View {
    private Paint paint;
    private PointF centerPos;
    private PointF point1;
    private PointF point2;
    private PointF point3;
    private PointF point4;
    private PointF point2Left;
    private PointF point2Mid;
    private PointF point2Right;
    private PointF point3Left;
    private PointF point3Right;
    private PointF point4Mid;
    private Path path = new Path();
    private int bl = Tools.pt2Px(100);
    private int pw = Tools.pt2Px(6);
    private float progress = 0;
    private ObjectAnimator ani;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public Bezier3View(Context context) {
        super(context);
        init();
    }

    public Bezier3View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Bezier3View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAni();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ani.end();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new PointF(w / 2.0f, h / 2.0f + 100);
        point1 = new PointF(centerPos.x - bl * 2, centerPos.y);
        point2 = new PointF(centerPos.x - bl * 2, centerPos.y - bl * 3);
        point3 = new PointF(centerPos.x + bl * 2, centerPos.y - bl * 3);
        point4 = new PointF(centerPos.x + bl * 2, centerPos.y);
        point2Left = new PointF();
        point2Right = new PointF();
        point2Mid = new PointF();
        point3Left = new PointF();
        point3Right = new PointF();
        point4Mid = new PointF();
    }

    private int lastX;
    private int lastY;
    private int curPoint;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;

                if (Math.sqrt(Math.pow(event.getX() - point2.x, 2)
                        + Math.pow(event.getY() - point2.y, 2)) < 30) {
                    curPoint = 2;
                } else if (Math.sqrt(Math.pow(event.getX() - point3.x, 2)
                        + Math.pow(event.getY() - point3.y, 2)) < 30) {
                    curPoint = 3;
                } else {
                    curPoint = 0;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;

                switch (curPoint) {
                    case 0:
                        // 点击距离节点太远
                        break;
                    case 2:
                        if (lastX + offsetX < 10
                                || lastX + offsetX > centerPos.x * 2
                                || lastY + offsetY < 0
                                || lastY + offsetY > (centerPos.y - 100) * 2) {
                            break;
                        }
                        point2.set(lastX + offsetX, lastY + offsetY);
                        System.out.println(String.format("point2X: %d offsetX: %d point2Y: %d offsetY:%d", (int) point2.x, offsetX, (int) point2.y, offsetY));
                        break;
                    case 3:
                        if (lastX + offsetX < 10
                                || lastX + offsetX > centerPos.x * 2
                                || lastY + offsetY < 0
                                || lastY + offsetY > (centerPos.y - 100) * 2) {
                            break;
                        }
                        point3.set(lastX + offsetX, lastY + offsetY);
                        System.out.println(String.format("point3X: %d offsetX: %d point3Y: %d offsetY:%d", (int) point2.x, offsetX, (int) point2.y, offsetY));
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float offsetLeftX = point2.x - point1.x;
        float offsetLeftY = point2.y - point1.y;
        float offsetMidX = point3.x - point2.x;
        float offsetMidY = point3.y - point2.y;
        float offsetRightX = point4.x - point3.x;
        float offsetRightY = point4.y - point3.y;
        point2Left.set(point1.x + offsetLeftX * progress, point1.y + offsetLeftY * progress);
        point2Mid.set(point2.x + offsetMidX * progress, point2.y + offsetMidY * progress);
        point2Right.set(point3.x + offsetRightX * progress, point3.y + offsetRightY * progress);

        float offset3LeftX = point2Mid.x - point2Left.x;
        float offset3LeftY = point2Mid.y - point2Left.y;
        float offset3RightX = point2Right.x - point2Mid.x;
        float offset3RightY = point2Right.y - point2Mid.y;
        point3Left.set(point2Left.x + offset3LeftX * progress, point2Left.y + offset3LeftY * progress);
        point3Right.set(point2Mid.x + offset3RightX * progress, point2Mid.y + offset3RightY * progress);

        float offset4MidX = point3Right.x - point3Left.x;
        float offset4MidY = point3Right.y - point3Left.y;
        point4Mid.set(point3Left.x + offset4MidX * progress, point3Left.y + offset4MidY * progress);


        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(pw);

        canvas.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
        canvas.drawLine(point2.x, point2.y, point3.x, point3.y, paint);
        canvas.drawLine(point3.x, point3.y, point4.x, point4.y, paint);

        canvas.drawRect(point1.x - pw, point1.y - pw, point1.x + pw, point1.y + pw, paint);
        canvas.drawRect(point2.x - pw, point2.y - pw, point2.x + pw, point2.y + pw, paint);
        canvas.drawRect(point3.x - pw, point3.y - pw, point3.x + pw, point3.y + pw, paint);
        canvas.drawRect(point4.x - pw, point4.y - pw, point4.x + pw, point4.y + pw, paint);

        paint.setColor(Color.RED);
        path.reset();  // Path 要记得 reset 初始化
        path.moveTo(point1.x, point1.y);
        path.cubicTo(point2Left.x, point2Left.y, point3Left.x, point3Left.y, point4Mid.x, point4Mid.y);
        canvas.drawPath(path, paint);

        paint.setColor(Color.GREEN);
        canvas.drawLine(point2Left.x, point2Left.y, point2Mid.x, point2Mid.y, paint);
        canvas.drawLine(point2Mid.x, point2Mid.y, point2Right.x, point2Right.y, paint);
        canvas.drawLine(point3Left.x, point3Left.y, point3Right.x, point3Right.y, paint);

        canvas.drawCircle(point2Left.x, point2Left.y, pw, paint);
        canvas.drawCircle(point2Mid.x, point2Mid.y, pw, paint);
        canvas.drawCircle(point2Right.x, point2Right.y, pw, paint);
        canvas.drawCircle(point3Left.x, point3Left.y, pw, paint);
        canvas.drawCircle(point3Right.x, point3Right.y, pw, paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(point4Mid.x, point4Mid.y, pw * 1.5f, paint);
    }


    private void startAni() {
        ani = ObjectAnimator.ofFloat(this, "progress", 0, 1f);
        ani.setRepeatMode(ValueAnimator.RESTART);
        ani.setRepeatCount(-1);
        ani.setDuration(3000);
        ani.start();
    }
}
