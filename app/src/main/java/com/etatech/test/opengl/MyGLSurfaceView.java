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
    public MyGLSurfaceView(Context context, MyGLRender render) {
        super(context);
        // 创建 opengl es 2.0 context
        setEGLContextClientVersion(2);
        // 设置 GLSurfaceView 渲染器
        setRenderer(render);
        // 默认关闭自动重绘 当需要重绘时，调用 GLSurfaceView.requestRender()
        if (render.isActiveRenderWhenDirty()){
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        } else {
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }
    }
}
