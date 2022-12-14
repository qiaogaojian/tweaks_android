package com.etatech.test.utils;


import android.graphics.Rect;

import com.etatech.test.bean.NodeBean;
import com.etatech.test.bean.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.crypto.Cipher;

/**
 * Created by Michael
 * Date:  2020/9/1
 * Func: 生成随机地图
 * 1. 生成房间
 * 根据设置长宽生成大地图
 * 随机生成奇数长宽的矩形
 * 在大地图范围内随机一个位置
 * 判断矩形边界是否越界 是否和已有矩形重叠
 * 把符合条件的矩形放入大地图
 * <p>
 * 2. 生成迷宫路径
 * <p>
 * 3. 连接路径和房间
 * 4. 移除无效路径
 * 5. 平整路径
 */
public class DungeonGenerator {
    private List<Rect> roomList;
    private List<Vector2> mazeList;
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

    private int[][] regionMarkArray;    // 标记区域二维数组(房间和单个迷宫)
    private int regionMarkIndex = -1;    // 当前区域标记id

    public List<NodeBean> getNodeList() {
        for (int i = 0; i < regionMarkArray.length; i++) {
            for (int j = 0; j < regionMarkArray[i].length; j++) {
                nodeList.get(Tools.pos2index(new Vector2(j + 1, i + 1), width)).setRegionMark(regionMarkArray[j][i]);
            }
        }
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
        regionMarkArray = new int[height][width];
        for (int i = 0; i < regionMarkArray.length; i++) {
            for (int j = 0; j < regionMarkArray[i].length; j++) {
                regionMarkArray[i][j] = -1;
            }
        }
        for (int i = 0; i < height * width; i++) {
            NodeBean node = new NodeBean();
            node.setPos(Tools.index2pos(i, width));
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


    // 记录哪个区域已经被合并了 这个列表是被合并区域的原始记录
    List<Integer> mergedRegions = new ArrayList<>();
    // 合并完剩下的的区域标记
    List<Integer> openRegions = new ArrayList<>();

    public void connectRegions() {
        // 潜在连接位置和连接区域映射 用于找到所有连接两个区域的格子
        HashMap<Vector2, List<Integer>> connectPos2RegionsMap = new HashMap<>();

        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                // 排除已经是区域的格子
                if (!isWall(new Vector2(i, j))) {
                    continue;
                }

                List<Integer> connectRegions = new ArrayList<>();
                for (Vector2 pos : checkPos) {
                    Vector2 curPos = new Vector2(pos.getX() + i, pos.getY() + j);
                    if (curPos.isValid(width)) {
                        int regionMark = regionMarkArray[curPos.getX() - 1][curPos.getY() - 1];
                        if (regionMark >= 0) {
                            connectRegions.add(regionMark);
                        }
                    }
                }

                if (connectRegions.size() == 2) {
                    if (connectRegions.get(0) == connectRegions.get(1)) {
                        continue;
                    }
                    connectPos2RegionsMap.put(new Vector2(i, j), connectRegions);
                }
            }
        }

        List<Vector2> allConnectors = new ArrayList<>();
        for (Vector2 pos : connectPos2RegionsMap.keySet()) {
            allConnectors.add(pos);
        }
        if (allConnectors.isEmpty()) {
            return;
        }

        // regionMarkIndex范围: 从 0 到 regionMarkIndex
        for (int i = 0; i <= regionMarkIndex; i++) {
            mergedRegions.add(i);
            openRegions.add(i);
        }

        // 连接所有区域直到连成一个大区域
        while (openRegions.size() > 1) {
            Vector2 curConnectorPos = allConnectors.get(Tools.randomRange(allConnectors.size()));

            // 合并连接的区域 选择一个区域然后把所有和他连接的区域标记成它一样
            List<Integer> connectRegions = connectPos2RegionsMap.get(curConnectorPos);
            for (int i = 0; i < connectRegions.size(); i++) {
                int temRegionMarkIndex = connectRegions.get(i);
                int mergedRegionMark = mergedRegions.get(temRegionMarkIndex);
                connectRegions.set(i, mergedRegionMark);
            }

            // 合并区域 第一个区域把第二个区域合并到自己中
            int firstRegionMark = connectRegions.remove(0);
            for (int i = 0; i < mergedRegions.size(); i++) {
                Integer mergedRegionMark = mergedRegions.get(i);

                if (connectRegions.get(0) == mergedRegionMark) {
                    mergedRegions.set(i, firstRegionMark);
                }
            }

            // 标记连接点
            addJunction(curConnectorPos, firstRegionMark);

            // 丢弃被合并的第二个区域
            int secondRegionMark = connectRegions.get(0);
            List<Integer> temList = new ArrayList<>();
            for (int i = 0; i < openRegions.size(); i++) {
                if (openRegions.get(i) == secondRegionMark) {
                    continue;
                }
                temList.add(openRegions.get(i));
            }
            openRegions = temList;

            List<Vector2> temConnectors = new ArrayList<>();
            for (int i = 0; i < allConnectors.size(); i++) {
                Vector2 connectorPos = allConnectors.get(i);
                //                if (Tools.randomRange(10)==1){ // 构造循环路径
                //                    addJunction(connectorPos);
                //                }
                if (needRemoveCurConnector(mergedRegions, connectPos2RegionsMap, curConnectorPos, connectorPos)) {
                    continue;
                }
                temConnectors.add(allConnectors.get(i));
            }
            allConnectors = temConnectors;
        }
        for (int i = 0; i < regionMarkArray.length; i++) {
            for (int j = 0; j < regionMarkArray[i].length; j++) {
                int temMarkIndex = regionMarkArray[j][i];
                if (temMarkIndex == -1) {
                    continue;
                }
                regionMarkArray[j][i] = mergedRegions.get(temMarkIndex);
            }
        }
    }

    // 是否删除当前连接点
    private boolean needRemoveCurConnector(List<Integer> mergedRegions,
                                           HashMap<Vector2, List<Integer>> connectPos2RegionsMap,
                                           Vector2 curConnectorPos,
                                           Vector2 connectorPos) {
        // 把自己和挨着自己的连接点删除
        if (new Vector2(curConnectorPos.getX() - connectorPos.getX(), curConnectorPos.getY() - connectorPos.getY()).SqrValue() < 1.5f) {
            return true;
        }

        // 遍历每一个连接点 把合并后的区域标记赋值给这个连接点的两个区域 然后对比这两个区域 如果相等就移除这个节点
        List<Integer> connectRegions = connectPos2RegionsMap.get(connectorPos);
        for (int i = 0; i < connectRegions.size(); i++) {
            int temRegionMarkIndex = connectRegions.get(i);
            int mergedRegionMark = mergedRegions.get(temRegionMarkIndex);

            connectRegions.set(i, mergedRegionMark);
        }

        HashSet<Integer> set = new HashSet<>(connectRegions);
        if (set.size() > 1) {
            return false;
        }
        return true;
    }

    public void RemoveDeadEnd() {
        boolean done = false;
        while (!done) {
            done = true;

            for (int i = 0; i < nodeList.size(); i++) {
                if (nodeList.get(i).getTileType() != NodeBean.TileType.Grass) {
                    continue;
                }
                int canWalkNum = 0;
                Vector2 nodePos = nodeList.get(i).getPos();
                for (Vector2 pos : checkPos) {
                    Vector2 curPos = new Vector2(pos.getX() + nodePos.getX(), pos.getY() + nodePos.getY());

                    if (curPos.isValid(width) &&
                            nodeList.get(Tools.pos2index(curPos, width)).getTileType() != NodeBean.TileType.Wall) {
                        canWalkNum++;
                    }
                }
                if (canWalkNum != 1) {
                    continue;
                }
                done = false;
                nodeList.get(i).setTileType(NodeBean.TileType.Wall);
                regionMarkArray[nodePos.getX() - 1][nodePos.getY() - 1] = -1;
            }
        }
    }

    private void startRegion() {
        regionMarkIndex++;
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
        regionMarkArray[curPos.getX() - 1][curPos.getY() - 1] = regionMarkIndex;
    }

    void carve(Vector2 curPos) {
        carve(curPos, NodeBean.TileType.Floor);
    }

    private void addJunction(Vector2 pos, int firstMark) {
        nodeList.get(Tools.pos2index(pos, width)).setTileType(NodeBean.TileType.OpenDoor);
        regionMarkArray[pos.getX() - 1][pos.getY() - 1] = mergedRegions.get(firstMark);
    }
}
