package com.etatech.test.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Michael
 * Date:  2021/1/11
 * Desc:
 */
public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context, MyGLRender render) {
        super(context);

        if (render instanceof RotateListener){
            mRotateListener = (RotateListener)render;
        }

        // 创建 opengl es 2.0 context
        setEGLContextClientVersion(2);
        // 设置 GLSurfaceView 渲染器
        setRenderer(render);
        // 默认关闭自动重绘 当需要重绘时，调用 GLSurfaceView.requestRender()
        if (render.isActiveRenderWhenDirty()) {
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        } else {
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }
    }

    private final float TOUCH_SCALE_FACTOR = 360f/300;
    private float curAngle = 0;

    private float mPreviousX;
    private float mPreviousY;
    private RotateListener mRotateListener;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreviousX = x;
                mPreviousY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }

                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }
                curAngle = dy;
                if (mRotateListener!=null){
                    mRotateListener.onRotate(curAngle);
                }
                break;
        }
        return true;
    }

    public interface RotateListener {
        void onRotate(float angle);
    }
}
