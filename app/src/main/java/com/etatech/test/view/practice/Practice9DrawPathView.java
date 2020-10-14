package com.etatech.test.view.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice9DrawPathView extends View {

    public Practice9DrawPathView(Context context) {
        super(context);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawPath() 方法画心形

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        Path heartPath = new Path();
        heartPath.addArc(500,300,700,500,-225,225);
        heartPath.arcTo(700,300,900,500,-180,225,false);
        heartPath.lineTo(700,642);
        heartPath.close();

        Path oriPath = new Path();
        oriPath.addArc(0,0,200,200,-225,225);
        oriPath.arcTo(200,0,400,200,-180,225,false);
        oriPath.lineTo(200,342);
        oriPath.close();

        heartPath.addPath(oriPath);
        canvas.drawPath(heartPath,paint);
    }
}
