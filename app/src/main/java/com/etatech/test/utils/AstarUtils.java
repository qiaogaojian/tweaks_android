package com.etatech.test.utils;

import com.etatech.test.bean.PathNodeBean;
import com.etatech.test.bean.Vector2;

import java.util.List;

/**
 * Created by Michael
 * Date:  2020/8/25
 * Func:
 */
public class AstarUtils {

    private static List<PathNodeBean> openList;
    private static List<PathNodeBean> closeList;

    public static Vector2 index2pos(int index, int lengh) {
        Vector2 pos = new Vector2();
        pos.setX(index % lengh + 1);
        pos.setY((int) Math.ceil(index / lengh) + 1);
        return pos;
    }

    public static int pos2index(Vector2 pos, int lengh) {
        return (pos.getY() - 1) * lengh + pos.getX() - 1;
    }

    public static int getPosDistance(Vector2 pos1, Vector2 pos2) {
        int distance = 0;
        distance += Math.abs(pos1.getX() - pos2.getX());
        distance += Math.abs(pos1.getY() - pos2.getY());
        return distance;
    }
}
