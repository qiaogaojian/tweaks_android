package com.etatech.test.utils;

import com.etatech.test.bean.Vector2;

/**
 * Created by Michael
 * Date:  2020/8/25
 * Func:
 */
public class AstarUtils {

    public static Vector2 index2pos(int index, int lengh) {
        Vector2 pos = new Vector2();
        pos.setX(index % lengh);
        pos.setY((int) Math.ceil((index + 1) / lengh));
        return pos;
    }

    public static int pos2index(Vector2 pos, int lengh) {
        return pos.getY() * lengh + pos.getX();
    }

    public static int getPosDistance(Vector2 pos1, Vector2 pos2) {
        int distance = 0;
        distance += Math.abs(pos1.getX() - pos1.getX());
        distance += Math.abs(pos1.getY() - pos1.getY());
        return distance;
    }
}
