package com.etatech.test.utils;


import android.graphics.Rect;

import com.etatech.test.bean.NodeBean;
import com.etatech.test.bean.Vector2;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Michael
 * Date:  2020/9/1
 * Func:
 */
public class DungeonGenerator {
    private List<Rect> roomList;
    private List<Vector2> mazeList;
    private List<NodeBean> nodeList;
    private int[][] _regions;
    private int currentRegionIndex = 0;
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
        mazeList = new ArrayList<>();
        nodeList = new ArrayList<>();
        _regions = new int[height][width];
        for (int i = 0; i < height * width; i++) {
            NodeBean node = new NodeBean();
            node.setPos(Tools.index2pos(i, 10));
            node.setIndex(i);
            node.setTileType(NodeBean.TileType.Wall);
            nodeList.add(node);
        }
    }

    public void generateRooms() {
        for (int i = 0; i < roomTryNum; i++) {
            generateRoom();
        }
    }

    public void generateRoom() {
        int size = Tools.randomRange(1, 3 + roomSize) * 2 + 1;
        int addSize = Tools.randomRange(0, 1 + size / 2) * 2;
        int roomWidth = size;
        int roomHeight = size;

        if (Tools.randomRange(0, 2) == 1) {
            roomWidth += addSize;
        } else {
            roomHeight += addSize;
        }

        int posX = Tools.randomRange(1, (width - roomWidth) / 2 + 1) * 2;
        int posY = Tools.randomRange(1, (height - roomHeight) / 2 + 1) * 2;

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
        startRegion();
        for (int x = posX; x < posX + roomWidth; x++) {
            for (int y = posY; y < posY + roomHeight; y++) {
                Vector2 curPos = new Vector2(x, y);
                carve(curPos);
            }
        }
    }

    public int generateRoomsData() {
        // *2 + 1 是为了获得奇数
        for (int i = 0; i < roomTryNum; i++) {
            int size = Tools.randomRange(1, 3 + roomSize) * 2 + 1;
            int addSize = Tools.randomRange(0, 1 + size / 2) * 2;
            int roomWidth = size;
            int roomHeight = size;

            if (Tools.randomRange(0, 2) == 1) {
                roomWidth += addSize;
            } else {
                roomHeight += addSize;
            }

            int posX = Tools.randomRange(1, (width - roomWidth) / 2 + 1) * 2;
            int posY = Tools.randomRange(1, (height - roomHeight) / 2 + 1) * 2;

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
        }
        return roomList.size();
    }

    public void carveRoom(int i) {
        Rect room = roomList.get(i);
        startRegion();
        for (int x = room.left; x < room.right; x++) {
            for (int y = room.top; y < room.bottom; y++) {
                Vector2 curPos = new Vector2(x, y);
                carve(curPos);
            }
        }
    }

    public void generateMaze() {
        for (int y = 2; y <= this.height; y += 2) {
            for (int x = 2; x <= this.width; x += 2) {
                Vector2 pos = new Vector2(x, y);
                if (getTileType(pos) != NodeBean.TileType.Wall) {
                    continue;
                }
                growMaze(pos);
            }
        }
        for (int i = 0; i < mazeList.size(); i++) {
            carve(mazeList.get(i), NodeBean.TileType.Grass);
        }
    }

    public int generateMazes() {
        for (int y = 2; y <= this.height; y += 2) {
            for (int x = 2; x <= this.width; x += 2) {
                Vector2 pos = new Vector2(x, y);
                if (getTileType(pos) != NodeBean.TileType.Wall) {
                    continue;
                }
                growMaze(pos);
            }
        }
        return mazeList.size();
    }

    public void carveMaze(int i) {
        Vector2 maze = mazeList.get(i);
        carve(maze, NodeBean.TileType.Grass);
    }

    private void growMaze(Vector2 start) {
        List<Vector2> mazes = new ArrayList<>();
        Vector2 lastPos = new Vector2(0, 0);
        startRegion();
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

                mazeList.add(new Vector2(curPos.getX() + nextPos.getX(), curPos.getY() + nextPos.getY()));
                mazeList.add(new Vector2(curPos.getX() + nextPos.getX() * 2, curPos.getY() + nextPos.getY() * 2));
                carve(new Vector2(curPos.getX() + nextPos.getX(), curPos.getY() + nextPos.getY()), NodeBean.TileType.Maze);
                carve(new Vector2(curPos.getX() + nextPos.getX() * 2, curPos.getY() + nextPos.getY() * 2), NodeBean.TileType.Maze);

                mazes.add(new Vector2(curPos.getX() + nextPos.getX() * 2, curPos.getY() + nextPos.getY() * 2));
                lastPos = nextPos;
            }
        }
    }

    public void connectRegions() {
        HashMap<Vector2, List<Integer>> connectRegions = new HashMap<>();
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                if (!isWall(new Vector2(i, j)))
                {
                    List<Integer> regions = new ArrayList<>();
                    for (Vector2 pos : checkPos) {
                        Vector2 curPos = new Vector2(pos.getX() + i, pos.getY() + j);
                        if (curPos.isValid(width)) {
                            int region = _regions[curPos.getX()][curPos.getY()];
                            if (region != 0) {
                                regions.add(region);
                            }
                        }
                    }

                    if (regions.size() >= 2) {
                        connectRegions.put(new Vector2(i, j), regions);
                    }
                }
            }
        }

        List<Vector2> connectors = new ArrayList<>();
        for (Vector2 pos : connectRegions.keySet()) {
            connectors.add(pos);
        }

        List<Integer> merged = new ArrayList<>();
        List<Integer> openRegions = new ArrayList<>();
        for (int i = 0; i < currentRegionIndex; i++) {
            merged.add(i);
            openRegions.add(i);
        }

        while (openRegions.size() > 1) {
            Vector2 connector = connectors.get(Tools.randomRange(connectors.size()));
            addJunction(connector);

            List<Integer> sources = connectRegions.get(connector);
            for (int i = 1; i < sources.size(); i++) {
                sources.set(i, merged.get(sources.get(i)));
            }

            int dest = sources.get(0);
            sources.remove(0);

            for (int i = 1; i < currentRegionIndex; i++) {
                if (sources.contains(merged.get(i))) ;
                {
                    merged.set(i, dest);
                }
            }

            List<Integer> temList = new ArrayList<>();
            for (Integer source : sources) {
                for (int i = 0; i < openRegions.size(); i++) {
                    if (openRegions.get(i) != source) {
                        temList.add(source);
                    }
                }
            }
            openRegions = temList;

            List<Vector2> temConnectors = new ArrayList<>();
            for (int i = 0; i < connectors.size(); i++) {
                Vector2 pos = connectors.get(i);
                if (!WhetherRemove(merged, connectRegions, connector, pos)) {
                    temConnectors.add(connectors.get(i));
                }
            }
            connectors = temConnectors;
        }

    }

    private void startRegion() {
        currentRegionIndex++;
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

    private boolean isWall(Vector2 pos) {
        NodeBean.TileType curTile = nodeList.get(Tools.pos2index(pos, width)).getTileType();
        if (curTile.equals(NodeBean.TileType.Wall)) {
            return true;
        } else {
            return false;
        }
    }

    private NodeBean.TileType getTileType(Vector2 pos) {
        int index = Tools.pos2index(pos, this.width);
        return nodeList.get(index).getTileType();
    }

    void carve(Vector2 curPos, NodeBean.TileType type) {
        nodeList.get(Tools.pos2index(curPos, width)).setTileType(type);
        _regions[curPos.getX()][curPos.getY()] = currentRegionIndex;
    }

    void carve(Vector2 curPos) {
        carve(curPos, NodeBean.TileType.Floor);
    }

    private void addJunction(Vector2 pos) {
        nodeList.get(Tools.pos2index(pos, width)).setTileType(NodeBean.TileType.OpenDoor);
    }

    private boolean WhetherRemove(List<Integer> merged, HashMap<Vector2, List<Integer>> connectorRegions, Vector2 connector, Vector2 pos) {
        if (new Vector2(connector.getX() - pos.getX(), connector.getY() - pos.getY()).SqrValue() < 1.5f) {
            return true;
        }

        List<Integer> tempList = connectorRegions.get(pos);
        for (int i = 0; i < tempList.size(); i++) {
            tempList.set(i, merged.get(tempList.get(i)));
        }

        HashSet<Integer> set = new HashSet<>(tempList);
        if (set.size() > 1) {
            return false;
        }
        return true;
    }

}
