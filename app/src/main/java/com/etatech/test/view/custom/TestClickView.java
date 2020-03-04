package com.etatech.test.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by Michael
 * Date:  2020/3/2
 * Func:
 */
public class TestClickView extends CustomView {
    private Region region;
    private Path   path;

    public TestClickView(Context context) {
        super(context);
    }

    public TestClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDeafultPaint.setColor(Color.RED);

        path = new Path();
        region = new Region();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.addCircle(w / 2, h / 2, w / 4, Path.Direction.CW);
        region.setPath(path, new Region(-w, -h, w, h));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                if (region.contains(x, y)) {
                    performClick();
                }
                break;
        }
        return true;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, mDeafultPaint);
    }
}
