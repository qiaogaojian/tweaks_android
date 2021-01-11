package com.etatech.test.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Michael
 * Date:  2021/1/11
 * Desc:
 */
public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context, GLSurfaceView.Renderer render) {
        super(context);
        // 创建 opengl es 2.0 context
        setEGLContextClientVersion(2);
        // 设置 GLSurfaceView 渲染器
        setRenderer(render);
    }
}
