package com.etatech.test.opengl;

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

    protected final float[] mViewMatrix = new float[16];
    protected final float[] mProjectionMatrix = new float[16];
    protected final float[] mVPMatrix = new float[16];
    protected float[] mMvpMatrix = new float[16];
    protected float[] mRotationMatrix = new float[16];
    protected float[] mScaleMatrix = new float[16];
    protected float[] mTransMatrix = new float[16];

    private float angle = 0;

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

    public void rotate(float angle){
        this.angle = angle;
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
        // 在这里以高为基准适配宽
        ratio = (float) width / height;

        // 投影矩阵
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

        onChange();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mMvpMatrix = new float[16];

        // 视图矩阵
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // VP矩阵
        Matrix.multiplyMM(mVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // 模型矩阵
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, 1.0f);

        // MVP矩阵
        Matrix.multiplyMM(mMvpMatrix, 0, mVPMatrix, 0, mRotationMatrix, 0);

        onUpdate();
    }

    public abstract void onAwake();

    public abstract void onStart();

    public abstract void onChange();

    public abstract void onUpdate();
}
