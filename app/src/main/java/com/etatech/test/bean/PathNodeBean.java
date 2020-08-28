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
    private Vector2 pos;
    private int index;
    private int reachSate;  // -1 不能走 0 未发现 1 已发现未走 2 已发现已走 3 目的地
    private PathNodeBean parent;
    private boolean isPath;
    private boolean isEnd;  // 是否终点

    public int getF() {
        return g + h;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void calF() {
        this.f = g + h;
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

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getReachSate() {
        return reachSate;
    }

    public void setReachSate(int reachSate) {
        this.reachSate = reachSate;
    }

    public PathNodeBean getParent() {
        return parent;
    }

    public void setParent(PathNodeBean parent) {
        this.parent = parent;
    }

    public boolean isPath() {
        return isPath;
    }

    public void setPath(boolean path) {
        isPath = path;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean findNode() {
        if (this.reachSate == 0 || this.reachSate == 3) {
            this.reachSate = 1;
            return true;
        }
        return false;
    }

    public boolean checkNode() {
        if (this.reachSate == -1) {
            return false;
        }
        if (!this.pos.isValid(10)) {
            return false;
        }
        return true;
    }
}