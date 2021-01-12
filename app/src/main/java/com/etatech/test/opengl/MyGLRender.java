package com.etatech.test.opengl;

import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

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

    protected final float[] mMVPMatrix = new float[]{
            1f,0f,0f,0f,
            0f,1f,0f,0f,
            0f,0f,1f,0f,
            0f,0f,0f,1f
    };
    protected final float[] mModelMatrix = new float[]{
            1f,0f,0f,0f,
            0f,1f,0f,0f,
            0f,0f,1f,0f,
            0f,0f,0f,1f
    };
    protected final float[] mViewMatrix = new float[]{
            1f,0f,0f,0f,
            0f,1f,0f,0f,
            0f,0f,1f,0f,
            0f,0f,0f,1f
    };
    protected final float[] mProjectionMatrix = new float[]{
            1f,0f,0f,0f,
            0f,1f,0f,0f,
            0f,0f,1f,0f,
            0f,0f,0f,1f
    };

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
        // 模型矩阵
        Matrix.scaleM(mModelMatrix, 0, 1.2f, 1.2f, 1.2f);
        // 视图矩阵
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0, 1.0f, 0f);
        // 投影矩阵
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1f,100f); // near的值为1 其他值会变形

        // Matrix.multiplyMV(identityMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);      // MV
        Matrix.multiplyMV(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);  // MVP

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
