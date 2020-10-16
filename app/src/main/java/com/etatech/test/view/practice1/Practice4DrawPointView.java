package com.etatech.test.view.practice1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;

public class Practice4DrawPointView extends View {

    public Practice4DrawPointView(Context context) {
        super(context);
    }

    public Practice4DrawPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice4DrawPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawPoint() 方法画点
//        一个圆点，一个方点
//        圆点和方点的切换使用 paint.setStrokeCap(cap)：`ROUND` 是圆点，`BUTT` 或 `SQUARE` 是方点

        Paint paint = new Paint();

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(AdaptScreenUtils.pt2Px(100));
        canvas.drawPoint(AdaptScreenUtils.pt2Px(300),AdaptScreenUtils.pt2Px(300),paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(AdaptScreenUtils.pt2Px(100));
        canvas.drawPoint(AdaptScreenUtils.pt2Px(600),AdaptScreenUtils.pt2Px(300),paint);
    }
}
