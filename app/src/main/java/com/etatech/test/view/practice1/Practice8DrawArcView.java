package com.etatech.test.view.practice1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
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

        // 练习内容：使用 canvas.drawArc() 方法画弧形和扇形
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawArc(AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(720), AdaptScreenUtils.pt2Px(500), 240, 100, true, paint);

        canvas.drawArc(AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(720), AdaptScreenUtils.pt2Px(500), 30, 120, false, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(360), AdaptScreenUtils.pt2Px(720), AdaptScreenUtils.pt2Px(500), 180, 50, false, paint);

        paint.setStyle(Paint.Style.STROKE);
        Path oriPath = new Path();
        int diameter = 300;
        oriPath.addArc(AdaptScreenUtils.pt2Px(0), AdaptScreenUtils.pt2Px(0), AdaptScreenUtils.pt2Px(diameter), AdaptScreenUtils.pt2Px(diameter), -225, 225);
        oriPath.arcTo(AdaptScreenUtils.pt2Px(diameter), AdaptScreenUtils.pt2Px(0), AdaptScreenUtils.pt2Px(diameter * 2), AdaptScreenUtils.pt2Px(diameter), -180, 225, false);
        oriPath.lineTo(AdaptScreenUtils.pt2Px(diameter), AdaptScreenUtils.pt2Px((int)calPosY(diameter)));  // 200 * (sin(45°)+cos(45°)) + 200
        oriPath.close();
        canvas.drawPath(oriPath, paint);
    }

    private double calPosY(int dia) {
        double posY = dia / 2.0 * (Math.sin(Math.toRadians(45)) + Math.cos(Math.toRadians(45))) + dia;
        return posY;
    }
}
