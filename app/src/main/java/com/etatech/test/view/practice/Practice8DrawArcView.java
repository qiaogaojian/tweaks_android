package com.etatech.test.view.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;

public class Practice8DrawArcView extends View {

    public Practice8DrawArcView(Context context) {
        super(context);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //        练习内容：使用 canvas.drawArc() 方法画弧形和扇形
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawArc(AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(720), AdaptScreenUtils.pt2Px(500), 240, 100, true, paint);

        canvas.drawArc(AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(720), AdaptScreenUtils.pt2Px(500), 30, 120, false, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(720), AdaptScreenUtils.pt2Px(500), 180, 50, false, paint);
    }
}
