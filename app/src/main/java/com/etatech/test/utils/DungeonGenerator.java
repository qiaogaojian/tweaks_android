package com.etatech.test.utils;

import java.util.Random;

/**
 * Created by Michael
 * Date:  2020/9/1
 * Func:
 */
public class DungeonGenerator {
    private int generateRoomNum;
    private int roomExtraSize;
    private int height;
    private int width;

    private void generateRooms() {

        for (int i = 0; i < generateRoomNum; i++) {
            int size = Tools.randomRange(1, 3 + roomExtraSize) * 2 + 1;

        }
    }
}
