package com.etatech.test.view.practice3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice13GetTextBoundsView extends View {
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text1 = "A";
    String text2 = "a";
    String text3 = "J";
    String text4 = "j";
    String text5 = "Â";
    String text6 = "â";
    int top = 200;
    int bottom = 400;

    public Practice13GetTextBoundsView(Context context) {
        super(context);
    }

    public Practice13GetTextBoundsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice13GetTextBoundsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20);
        paint1.setColor(Color.parseColor("#E91E63"));
        paint2.setTextSize(160);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(50, top, getWidth() - 50, bottom, paint1);

        // 使用 Paint.getTextBounds() 计算出文字的显示区域
        // 然后计算出文字的绘制位置，从而让文字上下居中
        // 这种居中算法的优点是，可以让文字精准地居中，分毫不差
        Rect rect1 = new Rect();
        Rect rect2 = new Rect();
        Rect rect3 = new Rect();
        Rect rect4 = new Rect();
        Rect rect5 = new Rect();
        Rect rect6 = new Rect();
        paint2.getTextBounds(text1, 0, 1, rect1);
        paint2.getTextBounds(text2, 0, 1, rect2);
        paint2.getTextBounds(text3, 0, 1, rect3);
        paint2.getTextBounds(text4, 0, 1, rect4);
        paint2.getTextBounds(text5, 0, 1, rect5);
        paint2.getTextBounds(text6, 0, 1, rect6);
        int offset1 = -(rect1.top + rect1.bottom) / 2;
        int offset2 = -(rect2.top + rect2.bottom) / 2;
        int offset3 = -(rect3.top + rect3.bottom) / 2;
        int offset4 = -(rect4.top + rect4.bottom) / 2;
        int offset5 = -(rect5.top + rect5.bottom) / 2;
        int offset6 = -(rect6.top + rect6.bottom) / 2;

        // 基线偏移而不是底部偏移 所以要减去底部
        int height1 = rect1.height() / 2 - rect1.bottom;
        int height2 = rect2.height() / 2 - rect2.bottom;
        int height3 = rect3.height() / 2 - rect3.bottom;
        int height4 = rect4.height() / 2 - rect4.bottom;
        int height5 = rect5.height() / 2 - rect5.bottom;
        int height6 = rect6.height() / 2 - rect6.bottom;

        int middle = (top + bottom) / 2;
        canvas.drawText(text1, 100, middle + offset1, paint2);
        canvas.drawText(text2, 200, middle + offset2, paint2);
        canvas.drawText(text3, 300, middle + offset3, paint2);
        canvas.drawText(text4, 400, middle + offset4, paint2);
        canvas.drawText(text5, 500, middle + offset5, paint2);
        canvas.drawText(text6, 600, middle + offset6, paint2);
    }
}