package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/10/26
 * Desc:
 */
public class Demo1View extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap1;     // 灰手
    private Bitmap bitmap2;     // 橙手
    private Bitmap bitmap3;     // 发光
    private int curNum = 0;     // 偶数:未点赞 奇数:点赞
    private int initNum = 99;   // 偶数:未点赞 奇数:点赞

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
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        textPaint.setTextSize(50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (curNum % 2 == 0) {
            canvas.drawBitmap(bitmap1, 0, 30, paint);
            canvas.drawText(String.valueOf(initNum), bitmap1.getWidth() + 10, bitmap1.getHeight() + 20, textPaint);
        } else {
            canvas.drawBitmap(bitmap2, 0, 30, paint);
            canvas.drawBitmap(bitmap3, 5, 0, paint);
            canvas.drawText(String.valueOf(initNum + 1), bitmap1.getWidth() + 10, bitmap1.getHeight() + 20, textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            curNum++;
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
