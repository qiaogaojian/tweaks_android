package com.etatech.test.utils;


import android.graphics.Rect;

import com.etatech.test.bean.NodeBean;
import com.etatech.test.bean.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael
 * Date:  2020/9/1
 * Func:
 */
public class DungeonGenerator {
    private List<Rect> roomList;
    private List<NodeBean> nodeList;
    private int width;
    private int height;
    private int roomTryNum;
    private int roomSize;

    public List<NodeBean> getNodeList() {
        return nodeList;
    }

    public void Init(int width, int height, int roomTryNum, int roomSize) {
        this.width = width;
        this.height = height;
        this.roomTryNum = roomTryNum;
        this.roomSize = roomSize;
        roomList = new ArrayList<>();
        nodeList = new ArrayList<>();
        for (int i = 0; i < height * width; i++) {
            NodeBean node = new NodeBean();
            node.setPos(Tools.index2pos(i, 10));
            node.setIndex(i);
            node.setReachSate(-1);
            nodeList.add(node);
        }

        generateRooms();
    }

    private void generateRooms() {
        for (int i = 0; i < roomTryNum; i++) {
            int size = Tools.randomRange(roomSize / 2, roomSize) * 2 + 1;
            int addSize = Tools.randomRange(0, 1 + size / 2);
            int roomWidth = size;
            int roomHeight = size;

            if (Tools.randomRange(0, 2) == 1) {
                roomWidth += addSize;
            } else {
                roomHeight += addSize;
            }

            int posX = Tools.randomRange((width - roomWidth) / 2) * 2 + 1;
            int posY = Tools.randomRange((height - roomHeight) / 2) * 2 + 1;

            Rect room = new Rect(posX, posY, posX + roomWidth, posY + roomHeight);
            boolean isOverlap = false;
            for (int j = 0; j < roomList.size(); j++) {
                if (Tools.isConnect(room, roomList.get(j))) {
                    isOverlap = true;
                    break;
                }
            }
            if (isOverlap) {
                continue;
            }

            roomList.add(room);
            for (int x = room.left; x <= room.right; x++) {
                for (int y = room.top; y <= room.bottom; y++) {
                    Vector2 curPos = new Vector2(x, y);
                    nodeList.get(Tools.pos2index(curPos, width)).setReachSate(1);
                }
            }
        }
    }


}
