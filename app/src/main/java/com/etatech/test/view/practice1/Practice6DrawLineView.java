package com.etatech.test.view.practice1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;

public class Practice6DrawLineView extends View {

    public Practice6DrawLineView(Context context) {
        super(context);
    }

    public Practice6DrawLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice6DrawLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawLine() 方法画直线
        Paint paint = new Paint();
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLACK);
        canvas.drawLine(AdaptScreenUtils.pt2Px(360),AdaptScreenUtils.pt2Px(360),AdaptScreenUtils.pt2Px(720),AdaptScreenUtils.pt2Px(720),paint);
    }
}
