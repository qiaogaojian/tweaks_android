package com.etatech.test.opengl;

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
public class GLRender2Triangle extends MyGLRender {
    private Model model;
    static float vertexs[] = {
            0.0f, 0.5f, 0.0f,   // top
            -0.5f, 0.0f, 0.0f, // bottom left
            0.5f, 0.0f, 0.0f   // bottom right
    };
    private short drawOrder[] = {0, 1, 2};
    private float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";


    @Override
    public void onStart() {
        model = new Model(vertexs, drawOrder, color, vertexShaderCode, fragmentShaderCode);
    }

    @Override
    public void onChange() {

    }

    @Override
    public void onUpdate() {
        model.draw();
    }
}