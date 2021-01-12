package com.etatech.test.opengl;

import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.etatech.test.utils.App;
import com.etatech.test.utils.RxBus;
import com.etatech.test.utils.Tools;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Michael
 * Date:  2021/1/11
 * Desc:
 */
public class GLRender2Triangle extends MyGLRender {
    private Model model;
    static float vertexs[] = {
            0.0f, 0.5f, 0.0f,   // top
            -0.5f, 0.0f, 0.0f, // bottom left
            0.5f, 0.0f, 0.0f   // bottom right
    };
    private short drawOrder[] = {0, 1, 2};
    private float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
    private String vertexShaderPath = "shader/gl_render2_triangle.vert";
    private String fragmentShaderPath = "shader/gl_render2_triangle.frag";

    private final float[] identityMatrix = new float[]{
            1f,0f,0f,0f,
            0f,1f,0f,0f,
            0f,0f,1f,0f,
            0f,0f,0f,1f
    };
    private final float[] mMVPMatrix = new float[]{
            1f,0f,0f,0f,
            0f,1f,0f,0f,
            0f,0f,1f,0f,
            0f,0f,0f,1f
    };
    private final float[] mModelMatrix = new float[]{
            1f,0f,0f,0f,
            0f,1f,0f,0f,
            0f,0f,1f,0f,
            0f,0f,0f,1f
    };
    private final float[] mViewMatrix = new float[]{
            1f,0f,0f,0f,
            0f,1f,0f,0f,
            0f,0f,1f,0f,
            0f,0f,0f,1f
    };
    private final float[] mProjectionMatrix = new float[]{
            1f,0f,0f,0f,
            0f,1f,0f,0f,
            0f,0f,1f,0f,
            0f,0f,0f,1f
    };

    @Override
    public void onAwake() {

    }

    @Override
    public void onStart() {
        String vertexShaderCode = Tools.readFile(App.getInstance().getAssets(), vertexShaderPath);
        String fragmentShaderCode = Tools.readFile(App.getInstance().getAssets(), fragmentShaderPath);
        model = new Model(vertexs, drawOrder, color, vertexShaderCode, fragmentShaderCode);
    }

    @Override
    public void onChange() {
        // 模型矩阵
        Matrix.scaleM(mModelMatrix, 0, 1.2f, 1.2f, 1.2f);
        // 视图矩阵
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -10, 0f, 0f, 0f, 0, 1.0f, 0f);
        // 投影矩阵
        Matrix.frustumM(mProjectionMatrix, 0, -1 / ratio, 1 / ratio, -1, 1, 0.1f, 10);

        Matrix.multiplyMV(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);      // MV
        Matrix.multiplyMV(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // MVP
    }

    @Override
    public void onUpdate() {
        model.setMat4("mvp",mModelMatrix);
        model.draw();
        switchRenderWhenDirtyMode(false);
    }
}