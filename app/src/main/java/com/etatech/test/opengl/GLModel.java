package com.etatech.test.opengl;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

/**
 * Created by Michael
 * Date:  2021/1/11
 * Desc:
 */

public class GLModel {
    @StringRes
    public int titleRes;
    public MyGLSurfaceView glView;

    public GLModel(@StringRes int titleRes, MyGLSurfaceView glView) {
        this.titleRes = titleRes;
        this.glView = glView;
    }
}