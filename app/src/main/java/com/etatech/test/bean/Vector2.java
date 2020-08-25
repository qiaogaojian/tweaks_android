package com.etatech.test.bean;

/**
 * Created by Michael
 * Date:  2020/8/25
 * Func:
 */
public class Vector2 {
    private int x;
    private int y;

    public Vector2() {

    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isValid(int lenth) {
        if (x >= 0 && y >= 0 && x < 10 && y < 10)
        {
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
