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
    private Vector2[] checkPos = {
            new Vector2(0, -1),
            new Vector2(0, 1),
            new Vector2(-1, 0),
            new Vector2(1, 0)
    };

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
            node.setTileType(NodeBean.TileType.Wall);
            nodeList.add(node);
        }
    }

    public void generateRoom() {
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
            return;
        }

        roomList.add(room);
        for (int x = room.left; x <= room.right; x++) {
            for (int y = room.top; y <= room.bottom; y++) {
                Vector2 curPos = new Vector2(x, y);
                carve(curPos);
            }
        }
    }

    public void generateRooms() {
        // *2 + 1 是为了获得奇数
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
                    carve(curPos);
                }
            }
        }
    }

    public void generateMaze() {
        for (int y = 1; y <= this.height; y += 2) {
            for (int x = 1; x <= this.width; x += 2) {
                Vector2 pos = new Vector2(x, y);
                if (getTileType(pos) != NodeBean.TileType.Wall) {
                    continue;
                }
                growMaze(pos);
            }
        }
    }

    private void growMaze(Vector2 start) {
        List<Vector2> mazes = new ArrayList<>();
        Vector2 lastPos = new Vector2(0, 0);

        mazes.add(start);
        while (mazes.size() != 0) {
            Vector2 curPos = mazes.get(mazes.size() - 1);

            List<Vector2> findCells = new ArrayList<>();
            for (Vector2 pos : checkPos) {
                if (canCarve(curPos, pos)) {
                    findCells.add(pos);
                }
            }

            if (findCells.isEmpty()) {
                mazes.remove(mazes.size() - 1);
                lastPos = null;
            } else {
                Vector2 nextPos;
                if (findCells.contains(lastPos) && Tools.randomRange(100) > 50) {
                    nextPos = lastPos;
                } else {
                    nextPos = findCells.get(Tools.randomRange(findCells.size()));
                }

                carve(new Vector2(curPos.getX() + nextPos.getX(), curPos.getY() + nextPos.getY()), NodeBean.TileType.Grass);
                carve(new Vector2(curPos.getX() + nextPos.getX() * 2, curPos.getY() + nextPos.getY() * 2), NodeBean.TileType.Grass);

                mazes.add(new Vector2(curPos.getX() + nextPos.getX() * 2, curPos.getY() + nextPos.getY() * 2));
                lastPos = nextPos;
            }
        }
    }

    private boolean canCarve(Vector2 pos, Vector2 checkPos) {
        Vector2 checkPos1 = new Vector2(pos.getX() + checkPos.getX() * 3, pos.getY() + checkPos.getY() * 3);
        Vector2 checkPos2 = new Vector2(pos.getX() + checkPos.getX() * 2, pos.getY() + checkPos.getY() * 2);

        if (checkPos1.isValid(width)
                && nodeList.get(Tools.pos2index(checkPos2, width)).getTileType() == NodeBean.TileType.Wall) {
            return true;
        }
        return false;
    }

    private NodeBean.TileType getTileType(Vector2 pos) {
        int index = Tools.pos2index(pos, this.width);
        return nodeList.get(index).getTileType();
    }

    void carve(Vector2 curPos, NodeBean.TileType type) {
        nodeList.get(Tools.pos2index(curPos, width)).setTileType(NodeBean.TileType.Floor);
    }

    void carve(Vector2 curPos) {
        carve(curPos, NodeBean.TileType.Floor);
    }

}
