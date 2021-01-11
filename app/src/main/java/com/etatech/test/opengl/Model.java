package com.etatech.test.opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Michael
 * Date:  2021/1/11
 * Desc:
 */
public class Model {
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private short drawOrder[];
    private float vertexs[];
    private float color[];
    private int shaderProgram = -1;

    private int mPositionHandle;
    private int mColorHandle;
    // private int vertexCount;
    private final int COORDS_PER_VERTEX = 3;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public int getShaderProgram() {
        return shaderProgram;
    }

    public Model(float vertexs[], short drawOrder[], float color[], String vscode, String fscode) {
        this.drawOrder = drawOrder;
        this.vertexs = vertexs;
        this.color = color;

        // vertexCount = vertexs.length / COORDS_PER_VERTEX;

        // 初始化顶点缓冲
        ByteBuffer bb = ByteBuffer.allocateDirect(vertexs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexs);
        vertexBuffer.position(0);
        // 初始化索引缓冲
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        loadShader(vscode, fscode);
    }

    public void loadShader(String vscode, String fscode) {
        if (shaderProgram != -1) {
            return;
        }
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vscode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fscode);
        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);
    }

    private int loadShader(int type, String code) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, code);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public void draw() {
        GLES20.glUseProgram(shaderProgram);

        mPositionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        setVec4("vColor",color);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public void setBool(String name, boolean value) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniform1i(location, value ? 1 : 0);
    }

    public void setInt(String name, int value) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniform1i(location, value);
    }

    public void setFloat(String name, float value) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniform1f(location, value);
    }

    public void setVec2(String name, float[] value) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniform2fv(location, 1, value, 0);
    }

    public void setVec2(String name, float x, float y) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniform2f(location, x, y);
    }

    public void setVec3(String name, float[] value) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniform3fv(location, 1, value, 0);
    }

    public void setVec3(String name, float x, float y, float z) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniform3f(location, x, y, z);
    }

    public void setVec4(String name, float[] value) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniform4fv(location, 1, value, 0);
    }

    public void setVec4(String name, float x, float y, float z, float w) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniform4f(location, x, y, z, w);
    }

    public void setMat2(String name, float[] mat) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniformMatrix2fv(location, 1,false, mat, 0);
    }

    public void setMat3(String name, float[] mat) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniformMatrix3fv(location, 1,false, mat, 0);
    }

    public void setMat4(String name, float[] mat) {
        int location = GLES20.glGetUniformLocation(shaderProgram, name);
        GLES20.glUniformMatrix4fv(location, 1,false, mat, 0);
    }
}
