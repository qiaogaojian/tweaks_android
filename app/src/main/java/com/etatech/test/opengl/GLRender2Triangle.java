package com.etatech.test.opengl;

import com.etatech.test.utils.App;
import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2021/1/11
 * Desc:
 */
public class GLRender2Triangle extends MyGLRender implements MyGLSurfaceView.RotateListener {
    private Model model;
    static float vertexs[] = {
            0.0f, 0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };
    private short drawOrder[] = {0, 1, 2};
    private float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
    private String vertexShaderPath = "shader/gl_render2_triangle.vert";
    private String fragmentShaderPath = "shader/gl_render2_triangle.frag";
    @Override
    public void onAwake() {
        switchRenderWhenDirtyMode(false);
    }

    @Override
    public void onStart() {
        String vertexShaderCode = Tools.readFile(App.getInstance().getAssets(), vertexShaderPath);
        String fragmentShaderCode = Tools.readFile(App.getInstance().getAssets(), fragmentShaderPath);
        model = new Model(vertexs, drawOrder, color, vertexShaderCode, fragmentShaderCode);
    }

    @Override
    public void onChange() {

    }

    @Override
    public void onUpdate() {
        model.setMat4("mvp", mMvpMatrix);
        model.draw();
    }

    @Override
    public void onRotate(float angle) {
        System.out.println("Angle: " + angle);
        rotate(angle);
    }
}