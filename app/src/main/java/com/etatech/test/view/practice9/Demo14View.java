package com.etatech.test.view.practice9;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.etatech.test.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael
 * Date:  2020/11/20
 * Desc:
 */
public class Demo14View extends View {

    private Paint paint;
    private Paint paint1;
    private Paint paint2;
    private Point centerPos;
    private ObjectAnimator ani;
    private int radius = Tools.pt2Px(90);
    private int mBigRadius = Tools.pt2Px(200);
    private int strokeWidth = Tools.pt2Px(20);
    private Path mMashPath = new Path();
    private List<RemoteButton> btnList;

    public Demo14View(Context context) {
        super(context);
        init();
    }

    public Demo14View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#4D5266"));
        paint1.setColor(Color.parseColor("#80807B"));
        paint2.setColor(Color.parseColor("#DE9D81"));

        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(strokeWidth);
        centerPos = new Point();

        btnList = new ArrayList<>();

        Path midPath = new Path();
        midPath.addCircle(0, 0, radius, Path.Direction.CW);
        btnList.add(new RemoteButton(midPath));
        for (int i = 0; i < 4; i++) {
            btnList.add(new RemoteButton(getBtnPath(i)));
            calMaskPath(i);
        }
        mMashPath.addCircle(0, 0, radius, Path.Direction.CW);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerPos.set(widthMeasureSpec / 2, heightMeasureSpec / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos.set(w / 2, h / 2);
    }

    private Matrix mMatrix;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(centerPos.x, centerPos.y);
        if (mMatrix == null) {
            mMatrix = new Matrix();
            mMatrix.preTranslate(-centerPos.x, -centerPos.y);
        }

        for (int i = 0; i < btnList.size(); i++) {
            if (btnList.get(i).isClick) {
                canvas.drawPath(btnList.get(i).getPath(), paint2);
            } else {
                canvas.drawPath(btnList.get(i).getPath(), paint);
            }
        }
        canvas.drawPath(mMashPath, paint1);
    }

    private Path getBtnPath(int i) {
        Point posIn = Tools.calCirPos(new Point(0, 0), radius, 315 + 90 * i);
        Point posOut = Tools.calCirPos(new Point(0, 0), mBigRadius, 45 + 90 * i);
        Path path = new Path();
        path.moveTo(posIn.x, posIn.y);
        path.arcTo(-radius, -radius, radius, radius, -45 + 90 * i, 90, false);
        path.lineTo(posOut.x, posOut.y);
        path.arcTo(-mBigRadius, -mBigRadius, mBigRadius, mBigRadius, 45 + 90 * i, -90, false);
        path.close();
        return path;
    }


    private void calMaskPath(int i) {
        Point posIn = Tools.calCirPos(new Point(0, 0), radius, 315 + 90 * i);
        Point posOut = Tools.calCirPos(new Point(0, 0), mBigRadius, 315 + 90 * i);
        mMashPath.moveTo(posIn.x, posIn.y);
        mMashPath.lineTo(posOut.x, posOut.y);
    }

    private float[] points = new float[2];

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        points[0] = event.getX();
        points[1] = event.getY();
        mMatrix.mapPoints(points);   // 平移Canvas坐标后修正点击位置

        Point curPos = new Point((int) points[0], (int) points[1]);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < btnList.size(); i++) {
                    if (btnList.get(i).getRegion().contains(curPos.x, curPos.y)) {
                        btnList.get(i).setClick(true);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                for (int i = 0; i < btnList.size(); i++) {
                    btnList.get(i).setClick(false);
                }
                invalidate();
                break;
        }
        return true;
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

    }

    private void endAni() {
        if (ani != null) {
            ani.end();
        }
    }

    class RemoteButton {
        private Path mPath;
        private Region mRegion;
        private boolean isClick;

        public RemoteButton(Path path) {
            mPath = path;
            mRegion = new Region();
            mRegion.setPath(path, new Region(-mBigRadius, -mBigRadius, mBigRadius, mBigRadius));
            this.isClick = false;
        }

        public Path getPath() {
            return mPath;
        }

        public void setPath(Path path) {
            mPath = path;
        }

        public Region getRegion() {
            return mRegion;
        }

        public void setRegion(Region region) {
            mRegion = region;
        }

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }
    }
}
