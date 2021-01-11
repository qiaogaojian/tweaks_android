package com.etatech.test.view.practice2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;

public class Practice01LinearGradientView extends View {
    Paint normalPaint = new Paint();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice01LinearGradientView(Context context) {
        super(context);
    }

    public Practice01LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice01LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        // 用 Paint.setShader(shader) 设置一个 LinearGradient
        // LinearGradient 的参数：坐标：(100, 100) 到 (500, 500) ；颜色：#E91E63 到 #2196F3
        normalPaint.setShader(new LinearGradient(AdaptScreenUtils.pt2Px(100),
                AdaptScreenUtils.pt2Px(100),
                AdaptScreenUtils.pt2Px(500),
                AdaptScreenUtils.pt2Px(500),
                Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"),
                Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(AdaptScreenUtils.pt2Px(300),
                AdaptScreenUtils.pt2Px(300),
                AdaptScreenUtils.pt2Px(200), normalPaint);
        canvas.drawCircle(AdaptScreenUtils.pt2Px(800),
                AdaptScreenUtils.pt2Px(300),
                AdaptScreenUtils.pt2Px(200), paint);

        // 需要关闭硬件加速才能正常显示 这里起点和终点位置是绝对位置
        Shader shader = new LinearGradient(
                AdaptScreenUtils.pt2Px(360),
                AdaptScreenUtils.pt2Px(700),
                AdaptScreenUtils.pt2Px(720),
                AdaptScreenUtils.pt2Px(700),
                Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        Paint lgPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 不新建的话会影响到上面的paint
        lgPaint.setShader(shader);
        canvas.drawCircle(AdaptScreenUtils.pt2Px(540),
                AdaptScreenUtils.pt2Px(700),
                AdaptScreenUtils.pt2Px(200), lgPaint);
    }
}
