package com.etatech.test.opengl;

import com.etatech.test.utils.App;
import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2021/1/11
 * Desc:
 */

public class GLRender3Rect extends MyGLRender {
    private Model model;
    static float vertexs[] = {
            -0.5f, 0.5f, 0.0f,      // top left
            -0.5f, -0.5f, 0.0f,     // bottom left
            0.5f, -0.5f, 0.0f,      // bottom right
            0.5f, 0.5f, 0.0f};      // top right

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };
    private float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private String vertexShaderPath = "shader/gl_render3_rect.vert";
    private String fragmentShaderPath = "shader/gl_render3_rect.frag";

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
        model.draw();
    }
}