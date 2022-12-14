package com.etatech.test.view.practice1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;

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
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);

        Path heartPath = new Path();
        heartPath.addArc(AdaptScreenUtils.pt2Px(500),AdaptScreenUtils.pt2Px(300),AdaptScreenUtils.pt2Px(700),AdaptScreenUtils.pt2Px(500),-225, 225);
        heartPath.arcTo(AdaptScreenUtils.pt2Px(700),AdaptScreenUtils.pt2Px(300),AdaptScreenUtils.pt2Px(900),AdaptScreenUtils.pt2Px(500),-180,225,false);
        heartPath.lineTo(AdaptScreenUtils.pt2Px(700),AdaptScreenUtils.pt2Px(642));
        heartPath.close();

        Path oriPath = new Path();
        oriPath.addArc(AdaptScreenUtils.pt2Px(0),AdaptScreenUtils.pt2Px(0),AdaptScreenUtils.pt2Px(200),AdaptScreenUtils.pt2Px(200),-225,225);
        oriPath.arcTo(AdaptScreenUtils.pt2Px(200),AdaptScreenUtils.pt2Px(0),AdaptScreenUtils.pt2Px(400),AdaptScreenUtils.pt2Px(200),-180,225,false);
        oriPath.lineTo(AdaptScreenUtils.pt2Px(200),AdaptScreenUtils.pt2Px(342));  // 200 * (sin(45°)+cos(45°)) + 200
        oriPath.close();

        heartPath.addPath(oriPath);
        canvas.drawPath(heartPath,paint);
    }
}
