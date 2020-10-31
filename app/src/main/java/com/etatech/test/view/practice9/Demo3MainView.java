package com.etatech.test.view.practice9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/10/30
 * Desc:
 */
public class Demo3MainView extends ViewGroup {
    private Paint paint;
    private Bitmap bgBitmap;
    private int width;
    private int height;
    private Matrix matrix = new Matrix();

    private Demo3View view1;
    private Demo3View2 view2;

    public Demo3MainView(Context context) {
        super(context);
        init(context);
    }

    public Demo3MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Demo3MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_step_law);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view1 = new Demo3View(context, this);
        view1.setLayoutParams(layoutParams);
        addView(view1);
        view2 = new Demo3View2(context, this);
        view2.setLayoutParams(layoutParams);
        addView(view2);

        setWillNotDraw(false); // 设置ViewGroup可绘制
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getVisibility() != GONE) {
                measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        view1.layout(0, 0, r - l, b - t);
        view2.layout(0, 0, r - l, b - t);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.reset();
        float scale = (float) width / bgBitmap.getWidth();
        matrix.postScale(scale, scale);
        canvas.drawBitmap(bgBitmap, matrix, paint); // 控制图片的大小
    }
}
