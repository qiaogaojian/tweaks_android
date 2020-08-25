package com.etatech.test.bean;

/**
 * Created by Michael
 * Date:  2020/8/25
 * Func:
 */
public class PathNodeBean {
    private int f;
    private int g;
    private int h;
    private int reachSate;  // -1 不能走 0 未发现 1 已发现未走 2 已发现已走 3 目的地

    public int getF() {
        return g + h;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getReachSate() {
        return reachSate;
    }

    public void setReachSate(int reachSate) {
        this.reachSate = reachSate;
    }
}
