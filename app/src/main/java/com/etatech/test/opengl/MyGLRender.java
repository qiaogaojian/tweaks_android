package com.etatech.test.opengl;

import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.etatech.test.utils.Tools;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Michael
 * Date:  2021/1/11
 * Desc:
 */
public abstract class MyGLRender implements GLSurfaceView.Renderer {
    // 背景色
    protected String bgColor = "#0080807B";
    // 宽高比
    protected float ratio = 0;
    private boolean activeRenderWhenDirty = true;

    public boolean isActiveRenderWhenDirty() {
        return activeRenderWhenDirty;
    }

    public void switchRenderWhenDirtyMode(boolean activeRenderWhenDirty) {
        this.activeRenderWhenDirty = activeRenderWhenDirty;
    }

    public MyGLRender() {
        onAwake();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        onStart();
        float[] color = Tools.hex2Color(bgColor);
        GLES20.glClearColor(color[0], color[1], color[2], color[3]);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        ratio = (float) width / height;
        onChange();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        onUpdate();
    }

    public abstract void onAwake();

    public abstract void onStart();

    public abstract void onChange();

    public abstract void onUpdate();
}
