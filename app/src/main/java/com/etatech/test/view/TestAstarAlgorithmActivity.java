package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.PathNodeAdapter;
import com.etatech.test.bean.PathNodeBean;
import com.etatech.test.bean.Vector2;
import com.etatech.test.databinding.ActivityTestAstarAlgorithmBinding;
import com.etatech.test.utils.AstarUtils;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.functions.Action1;

public class TestAstarAlgorithmActivity extends BaseActivity<ActivityTestAstarAlgorithmBinding> {
    private PathNodeAdapter nodeAdapter;
    private int             start;
    private int             end;

    @Override
    public ActivityTestAstarAlgorithmBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestAstarAlgorithmActivity.this, R.layout.activity_test_astar_algorithm);
    }

    @Override
    public void init() {

        nodeAdapter = new PathNodeAdapter(initPath());
        binding.rvPathNode.setAdapter(nodeAdapter);
        binding.rvPathNode.setLayoutManager(new GridLayoutManager(this, 10));

        ClickUtil.setOnClick(binding.btnPre, new Action1() {
            @Override
            public void call(Object o) {

            }
        });

        ClickUtil.setOnClick(binding.btnNext, new Action1() {
            @Override
            public void call(Object o) {

            }
        });

        ClickUtil.setOnClick(binding.btnContinue, new Action1() {
            @Override
            public void call(Object o) {

            }
        });
    }

    private List<PathNodeBean> initPath() {
        List<PathNodeBean> nodeList = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            PathNodeBean node = new PathNodeBean();
            node.setPos(AstarUtils.index2pos(i, 10));
            node.setIndex(i);
            node.setReachSate(0);
            nodeList.add(new PathNodeBean());
        }

        // 障碍
        Random random = new Random();
        for (int i = 0; i < 30; i++)
        {
            nodeList.get(random.nextInt(100)).setReachSate(-1);
        }

        // 起点和终点
        start = random.nextInt(100);
        end = random.nextInt(100);
        nodeList.get(start).setReachSate(2);
        nodeList.get(end).setReachSate(3);
        System.out.println(String.format("startPos index:%d",start));
        Vector2 startPos = AstarUtils.index2pos(start, 10);
        Vector2 endPos   = AstarUtils.index2pos(end, 10);

        Vector2 curLeftPos  = new Vector2(startPos.getX() - 1, startPos.getY());
        Vector2 curRightPos = new Vector2(startPos.getX() + 1, startPos.getY());
        Vector2 curTopPos   = new Vector2(startPos.getX(), startPos.getY() - 1);
        Vector2 curDownPos  = new Vector2(startPos.getX(), startPos.getY() + 1);

        System.out.println(String.format("startPos:%s-%s leftPos:%s-%s rightPos:%s-%s topPos:%s-%s downPos:%s-%s endPos:%s-%s "
                , startPos.getX(), startPos.getY()
                , curLeftPos.getX(), curLeftPos.getY()
                , curRightPos.getX(), curRightPos.getY()
                , curTopPos.getX(), curTopPos.getY()
                , curDownPos.getX(), curDownPos.getY()
                , endPos.getX(), endPos.getY()));

        if (curLeftPos.isValid(10) && nodeList.get(AstarUtils.pos2index(curLeftPos, 10)).findNode())
        {
            System.out.println(String.format("curLeftPos valid! pos:%s-%s index:%s",curLeftPos.getX(),curLeftPos.getY(),AstarUtils.pos2index(curLeftPos, 10)));
            nodeList.get(AstarUtils.pos2index(curLeftPos, 10)).setG(AstarUtils.getPosDistance(startPos, curLeftPos));
            nodeList.get(AstarUtils.pos2index(curLeftPos, 10)).setH(AstarUtils.getPosDistance(curLeftPos, endPos));
            nodeList.get(AstarUtils.pos2index(curLeftPos, 10)).calF();
        }
        if (curRightPos.isValid(10) && nodeList.get(AstarUtils.pos2index(curRightPos, 10)).findNode())
        {
            System.out.println(String.format("curRightPos valid! pos:%s-%s index:%s",curRightPos.getX(),curRightPos.getY(),AstarUtils.pos2index(curRightPos, 10)));
            nodeList.get(AstarUtils.pos2index(curRightPos, 10)).setG(AstarUtils.getPosDistance(startPos, curRightPos));
            nodeList.get(AstarUtils.pos2index(curRightPos, 10)).setH(AstarUtils.getPosDistance(curRightPos, endPos));
            nodeList.get(AstarUtils.pos2index(curRightPos, 10)).calF();
        }
        if (curTopPos.isValid(10) && nodeList.get(AstarUtils.pos2index(curTopPos, 10)).findNode())
        {
            System.out.println(String.format("curTopPos valid! pos:%s-%s index:%s",curTopPos.getX(),curTopPos.getY(),AstarUtils.pos2index(curTopPos, 10)));
            nodeList.get(AstarUtils.pos2index(curTopPos, 10)).setG(AstarUtils.getPosDistance(startPos, curTopPos));
            nodeList.get(AstarUtils.pos2index(curTopPos, 10)).setH(AstarUtils.getPosDistance(curTopPos, endPos));
            nodeList.get(AstarUtils.pos2index(curTopPos, 10)).calF();
        }
        if (curDownPos.isValid(10) && nodeList.get(AstarUtils.pos2index(curDownPos, 10)).findNode())
        {
            System.out.println(String.format("curDownPos valid! pos:%s-%s index:%s",curDownPos.getX(),curDownPos.getY(),AstarUtils.pos2index(curDownPos, 10)));
            nodeList.get(AstarUtils.pos2index(curDownPos, 10)).setG(AstarUtils.getPosDistance(startPos, curDownPos));
            nodeList.get(AstarUtils.pos2index(curDownPos, 10)).setH(AstarUtils.getPosDistance(curDownPos, endPos));
            nodeList.get(AstarUtils.pos2index(curDownPos, 10)).calF();
        }

        return nodeList;
    }
}
