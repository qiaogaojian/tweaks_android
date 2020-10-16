package com.etatech.test.view.practice1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;

public class Practice3DrawRectView extends View {

    public Practice3DrawRectView(Context context) {
        super(context);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 练习内容：使用 canvas.drawRect() 方法画矩形
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(AdaptScreenUtils.pt2Px(300),AdaptScreenUtils.pt2Px(300),AdaptScreenUtils.pt2Px(600),AdaptScreenUtils.pt2Px(500),paint);
    }
}
