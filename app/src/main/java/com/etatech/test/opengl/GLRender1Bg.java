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
public class GLRender1Bg extends MyGLRender {

    @Override
    public void onStart() {
        bgColor = "#F92671";
    }

    @Override
    public void onChange() {

    }

    @Override
    public void onUpdate() {

    }
}