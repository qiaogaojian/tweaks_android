package com.etatech.test.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.etatech.test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by Michael
 * Date:  2019/11/14
 * Func:
 * Android auto Scroll Text
 */
public class ScrollTextView extends SurfaceView implements SurfaceHolder.Callback
{
    private final String TAG = "ScrollTextView";

    // surface Handle onto a raw buffer that is being managed by the screen compositor.
    // providing access and control over this SurfaceView's underlying surface.
    private SurfaceHolder surfaceHolder;

    private Paint   paint      = null;
    private boolean stopScroll = false;

    //Default value
    private int    speed    = 1;
    private String text     = "";
    private float  textSize = 15f;

    private int   viewWidth                 = 0;
    private int   viewHeight                = 0;

    private float textWidth                 = 0f;
    private float textHeight                = 0f;

    private float textX                     = 0f;
    private float textY                     = 0f;

    private float viewWidth_plus_textLength = 0.0f;

    private ScheduledExecutorService scheduledExecutorService;

    /**
     * constructs 1
     *
     * @param context you should know
     */
    public ScrollTextView(Context context) {
        super(context);
    }

    /**
     * constructs 2
     *
     * @param context CONTEXT
     * @param attrs   ATTRS
     */
    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = this.getHolder();  //get The surface holder
        surfaceHolder.addCallback(this);
        paint = new Paint();
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollTextView);
        speed = arr.getInteger(R.styleable.ScrollTextView_speed, speed);
        text = arr.getString(R.styleable.ScrollTextView_text);
        int textColor = arr.getColor(R.styleable.ScrollTextView_text_color, Color.BLACK);
        textSize = arr.getDimension(R.styleable.ScrollTextView_text_size, textSize);

        paint.setColor(textColor);
        paint.setTextSize(textSize);
        setZOrderOnTop(true);  //Control whether the surface view's surface is placed on top of its window.
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        setFocusable(true);

        arr.recycle();
    }

    /**
     * measure text height width
     *
     * @param widthMeasureSpec  widthMeasureSpec
     * @param heightMeasureSpec heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mHeight = getFontHeight(textSize);  //实际的视图高
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        textHeight = viewWidth * (float) Math.tan(Math.toRadians(16));

        // when layout width or height is wrap_content ,should init ScrollTextView Width/Height
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(viewWidth, mHeight);
            viewHeight = mHeight;
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(viewWidth, viewHeight);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(viewWidth, mHeight);
            viewHeight = mHeight;
        }
    }


    /**
     * surfaceChanged
     *
     * @param arg0 arg0
     * @param arg1 arg1
     * @param arg2 arg1
     * @param arg3 arg1
     */
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        Log.d(TAG, "arg0:" + arg0.toString() + "  arg1:" + arg1 + "  arg2:" + arg2 + "  arg3:" + arg3);
    }

    /**
     * surfaceCreated,init a new scroll thread.
     * lockCanvas
     * Draw somthing
     * unlockCanvasAndPost
     *
     * @param holder holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        stopScroll = false;
        // 定时执行任务
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTextThread(), 100, 100, TimeUnit.MILLISECONDS);
        Log.d(TAG, "ScrollTextTextView is created");
    }

    /**
     * surfaceDestroyed
     *
     * @param arg0 SurfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        stopScroll = true;
        scheduledExecutorService.shutdownNow();
        Log.d(TAG, "ScrollTextTextView is destroyed");
    }

    /**
     * text height
     *
     * @param fontSize fontSize
     * @return fontSize`s height
     */
    private int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * set scroll text
     *
     * @param newText scroll text
     */
    public void setText(String newText) {
        stopScroll = false;
        this.text = newText;
        Log.d(TAG, "ScrollTextTextView is set:"+newText);
        measureVarious();
    }

    /**
     * set scroll speed
     *
     * @param speed SCROLL SPEED [0,10] ///// 0?
     */
    public void setSpeed(int speed) {
        if (speed > 10 || speed < 0) {
            throw new IllegalArgumentException("Speed was invalid integer, it must between 0 and 10");
        } else {
            this.speed = speed;
        }
    }

    /**
     * Draw text
     *
     * @param X X
     * @param Y Y
     */
    private synchronized void draw(float X, float Y) {
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.skew((float) Math.tan(Math.toRadians(-16)), 0);
        canvas.rotate(-16, this.getWidth(), this.getHeight());
        canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
        canvas.drawText(text, 300 + X, Y, paint);
        surfaceHolder.unlockCanvasAndPost(canvas);
        System.out.println("fix anr");
    }

    /**
     * measure tex
     */
    private void measureVarious() {
        textWidth = paint.measureText(text);
        if (textWidth > viewWidth) {
            viewWidth_plus_textLength = textWidth;
        } else {
            viewWidth_plus_textLength = viewWidth;
        }
        textX = 0;

        //baseline measure
        FontMetrics fontMetrics = paint.getFontMetrics();
        distance = -fontMetrics.top;
        textY = distance + (viewHeight - textHeight) / 2;
    }

    float distance;

    /**
     * Scroll thread
     */
    class ScrollTextThread implements Runnable
    {
        @Override
        public void run() {
            measureVarious();
            while (!stopScroll) {
                draw(-textX, textY);
                if (textWidth + 300 > viewWidth) {
                    textX += speed * 0.6f;
                    if (textX > viewWidth_plus_textLength) {
                        textX = 0;
                        textY = distance + (viewHeight - textHeight) / 2;
                    }
                }
            }
        }
    }
}