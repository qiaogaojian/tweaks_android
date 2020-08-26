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

    private static List<PathNodeBean> openList  = new ArrayList<>();
    private static List<PathNodeBean> closeList = new ArrayList<>();
    private static PathNodeBean       curNode;
    private static List<PathNodeBean> nodeList;

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

        while (curNode.getIndex() != end.getIndex())
        {
            nextStep(nodeList, start, end);
        }
    }

    public static boolean nextStep(List<PathNodeBean> oriList, PathNodeBean start, PathNodeBean end) {
        if (nodeList == null)
        {
            nodeList = oriList;
            curNode = start;
        }

        closeList.add(curNode);

        nodeList.get(curNode.getIndex()).setReachSate(2);
        for (int i = 0; i < openList.size(); i++)
        {
            if (openList.get(i).getIndex() == curNode.getIndex())
            {
                openList.remove(i);
                break;
            }
        }

        Vector2 startPos = start.getPos();
        Vector2 endPos   = end.getPos();

        Vector2 curLeftPos  = new Vector2(curNode.getPos().getX() - 1, curNode.getPos().getY());
        Vector2 curRightPos = new Vector2(curNode.getPos().getX() + 1, curNode.getPos().getY());
        Vector2 curTopPos   = new Vector2(curNode.getPos().getX(), curNode.getPos().getY() - 1);
        Vector2 curDownPos  = new Vector2(curNode.getPos().getX(), curNode.getPos().getY() + 1);

        System.out.println(String.format("startPos:%s-%s leftPos:%s-%s rightPos:%s-%s topPos:%s-%s downPos:%s-%s endPos:%s-%s "
                , startPos.getX(), startPos.getY()
                , curLeftPos.getX(), curLeftPos.getY()
                , curRightPos.getX(), curRightPos.getY()
                , curTopPos.getX(), curTopPos.getY()
                , curDownPos.getX(), curDownPos.getY()
                , endPos.getX(), endPos.getY()));

        if (curLeftPos.isValid(10) && nodeList.get(AstarUtils.pos2index(curLeftPos, 10)).findNode())
        {
            System.out.println(String.format("curLeftPos valid! pos:%s-%s index:%s", curLeftPos.getX(), curLeftPos.getY(), AstarUtils.pos2index(curLeftPos, 10)));
            nodeList.get(AstarUtils.pos2index(curLeftPos, 10)).setG(AstarUtils.getPosDistance(startPos, curLeftPos));
            nodeList.get(AstarUtils.pos2index(curLeftPos, 10)).setH(AstarUtils.getPosDistance(curLeftPos, endPos));
            nodeList.get(AstarUtils.pos2index(curLeftPos, 10)).calF();

            openList.add(nodeList.get(AstarUtils.pos2index(curLeftPos, 10)));
        }
        if (curRightPos.isValid(10) && nodeList.get(AstarUtils.pos2index(curRightPos, 10)).findNode())
        {
            System.out.println(String.format("curRightPos valid! pos:%s-%s index:%s", curRightPos.getX(), curRightPos.getY(), AstarUtils.pos2index(curRightPos, 10)));
            nodeList.get(AstarUtils.pos2index(curRightPos, 10)).setG(AstarUtils.getPosDistance(startPos, curRightPos));
            nodeList.get(AstarUtils.pos2index(curRightPos, 10)).setH(AstarUtils.getPosDistance(curRightPos, endPos));
            nodeList.get(AstarUtils.pos2index(curRightPos, 10)).calF();

            openList.add(nodeList.get(AstarUtils.pos2index(curRightPos, 10)));
        }
        if (curTopPos.isValid(10) && nodeList.get(AstarUtils.pos2index(curTopPos, 10)).findNode())
        {
            System.out.println(String.format("curTopPos valid! pos:%s-%s index:%s", curTopPos.getX(), curTopPos.getY(), AstarUtils.pos2index(curTopPos, 10)));
            nodeList.get(AstarUtils.pos2index(curTopPos, 10)).setG(AstarUtils.getPosDistance(startPos, curTopPos));
            nodeList.get(AstarUtils.pos2index(curTopPos, 10)).setH(AstarUtils.getPosDistance(curTopPos, endPos));
            nodeList.get(AstarUtils.pos2index(curTopPos, 10)).calF();

            openList.add(nodeList.get(AstarUtils.pos2index(curTopPos, 10)));
        }
        if (curDownPos.isValid(10) && nodeList.get(AstarUtils.pos2index(curDownPos, 10)).findNode())
        {
            System.out.println(String.format("curDownPos valid! pos:%s-%s index:%s", curDownPos.getX(), curDownPos.getY(), AstarUtils.pos2index(curDownPos, 10)));
            nodeList.get(AstarUtils.pos2index(curDownPos, 10)).setG(AstarUtils.getPosDistance(startPos, curDownPos));
            nodeList.get(AstarUtils.pos2index(curDownPos, 10)).setH(AstarUtils.getPosDistance(curDownPos, endPos));
            nodeList.get(AstarUtils.pos2index(curDownPos, 10)).calF();

            openList.add(nodeList.get(AstarUtils.pos2index(curDownPos, 10)));
        }

        if (openList.size() > 0)
        {
            PathNodeBean minFNode = openList.get(0);
            for (int i = 0; i < openList.size(); i++)
            {
                if (openList.get(i).getF() < minFNode.getF())
                {
                    minFNode = openList.get(i);
                }
            }
            AstarUtils.curNode = minFNode;
        }

        for (int i = 0; i < closeList.size(); i++)
        {
            if (closeList.get(i).getIndex() == end.getIndex())
            {
                return true;
            }
        }
        return false;
    }

    public static List<PathNodeBean> getNodeList() {
        return nodeList;
    }
}
