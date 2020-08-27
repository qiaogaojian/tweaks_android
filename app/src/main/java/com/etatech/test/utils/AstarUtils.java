package com.etatech.test.utils;

import com.etatech.test.bean.PathNodeBean;
import com.etatech.test.bean.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael
 * Date:  2020/8/25
 * Func:
 */
public class AstarUtils {

    private static List<PathNodeBean> openList = new ArrayList<>();
    private static List<PathNodeBean> closeList = new ArrayList<>();
    private static List<PathNodeBean> pathList = new ArrayList<>();
    private static List<PathNodeBean> nodeList;
    private static PathNodeBean curNode;

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

    public static void findPath(List<PathNodeBean> oriList, PathNodeBean start, PathNodeBean end) {
        nodeList = oriList;
        curNode = start;

        while (curNode.getIndex() != end.getIndex()) {
            nextStep(nodeList, start, end);
        }
    }

    public static int nextStep(List<PathNodeBean> oriList, PathNodeBean start, PathNodeBean end) {
        if (nodeList == null || nodeList.size() == 0) {
            nodeList = oriList;
            curNode = start;
        }

        closeList.add(curNode);

        nodeList.get(curNode.getIndex()).setReachSate(2);
        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).getIndex() == curNode.getIndex()) {
                openList.remove(i);
                break;
            }
        }

        Vector2 startPos = start.getPos();
        Vector2 endPos = end.getPos();

        Vector2 curLeftPos = new Vector2(curNode.getPos().getX() - 1, curNode.getPos().getY());
        Vector2 curRightPos = new Vector2(curNode.getPos().getX() + 1, curNode.getPos().getY());
        Vector2 curTopPos = new Vector2(curNode.getPos().getX(), curNode.getPos().getY() - 1);
        Vector2 curDownPos = new Vector2(curNode.getPos().getX(), curNode.getPos().getY() + 1);

        Vector2 curTopLeftPos = new Vector2(curNode.getPos().getX() - 1, curNode.getPos().getY() - 1);
        Vector2 curTopRightPos = new Vector2(curNode.getPos().getX() + 1, curNode.getPos().getY() - 1);
        Vector2 curDownLeftPos = new Vector2(curNode.getPos().getX() - 1, curNode.getPos().getY() + 1);
        Vector2 curDownRightPos = new Vector2(curNode.getPos().getX() + 1, curNode.getPos().getY() + 1);

        System.out.println(String.format("AstarUtils startPos:%s-%s leftPos:%s-%s rightPos:%s-%s topPos:%s-%s downPos:%s-%s endPos:%s-%s "
                , startPos.getX(), startPos.getY()
                , curLeftPos.getX(), curLeftPos.getY()
                , curRightPos.getX(), curRightPos.getY()
                , curTopPos.getX(), curTopPos.getY()
                , curDownPos.getX(), curDownPos.getY()
                , endPos.getX(), endPos.getY()));

        addNeighborToOpenList(curLeftPos, startPos, endPos);
        addNeighborToOpenList(curRightPos, startPos, endPos);
        addNeighborToOpenList(curTopPos, startPos, endPos);
        addNeighborToOpenList(curDownPos, startPos, endPos);

        //        addNeighborToOpenList(curTopLeftPos, startPos, endPos);
        //        addNeighborToOpenList(curTopRightPos, startPos, endPos);
        //        addNeighborToOpenList(curDownLeftPos, startPos, endPos);
        //        addNeighborToOpenList(curDownRightPos, startPos, endPos);

        if (openList.size() > 0) {
            PathNodeBean minFNode = openList.get(0);
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).getF() <= minFNode.getF()) {
                    minFNode = openList.get(i);
                }
            }
            AstarUtils.curNode = minFNode;
        }

        for (int i = 0; i < closeList.size(); i++) {
            if (closeList.get(i).getIndex() == end.getIndex()) {
                curNode = end;
                do {
                    pathList.add(curNode);
                    curNode = curNode.getParent();
                } while (curNode != null);

                for (int j = 0; j < pathList.size(); j++) {
                    nodeList.get(pathList.get(j).getIndex()).setPath(true);
                }

                return 1;
            }
        }
        if (openList.size() == 0) {
            return 2;
        }
        return 0;
    }

    private static void addNeighborToOpenList(Vector2 curPos, Vector2 startPos, Vector2 endPos) {
        if (curPos.isValid(10) && nodeList.get(AstarUtils.pos2index(curPos, 10)).findNode()) {
            System.out.println(String.format("AstarUtils curLeftPos valid! pos:%s-%s index:%s", curPos.getX(), curPos.getY(), AstarUtils.pos2index(curPos, 10)));
            nodeList.get(AstarUtils.pos2index(curPos, 10)).setG(AstarUtils.getPosDistance(startPos, curPos));
            nodeList.get(AstarUtils.pos2index(curPos, 10)).setH(AstarUtils.getPosDistance(curPos, endPos));
            nodeList.get(AstarUtils.pos2index(curPos, 10)).calF();
            nodeList.get(AstarUtils.pos2index(curPos, 10)).setParent(curNode);

            openList.add(nodeList.get(AstarUtils.pos2index(curPos, 10)));
        }
    }

    public static List<PathNodeBean> getNodeList() {
        return nodeList;
    }

    public static void reset() {
        nodeList.clear();
        openList.clear();
        closeList.clear();
        pathList.clear();
    }
}
