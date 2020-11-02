package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/11/2
 * Desc:
 */
public class Demo4View extends View {
    private Paint paint;
    private Bitmap bitmap;
    private PointF centerPos;

    public Demo4View(Context context) {
        super(context);
        init();
    }

    public Demo4View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo4View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPos = new PointF(w / 2f, h / 2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, centerPos.x - bitmap.getWidth(), centerPos.y - bitmap.getHeight(), paint);
    }
}
