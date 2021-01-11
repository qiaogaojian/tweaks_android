package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.etatech.test.R;
import com.etatech.test.adapter.PathNodeAdapter;
import com.etatech.test.bean.NodeBean;
import com.etatech.test.databinding.ActivityTestAstarAlgorithmBinding;
import com.etatech.test.utils.AstarUtils;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.Tools;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.functions.Action1;

public class TestAstarAlgorithmActivity extends BaseActivity<ActivityTestAstarAlgorithmBinding> {
    private List<NodeBean> nodeList;
    private PathNodeAdapter nodeAdapter;
    private int start;
    private int end;
    private int reachState;             // 0 未到达 1 已到达 2 此路不通
    private AstarUtils astarUtils;

    @Override
    public ActivityTestAstarAlgorithmBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestAstarAlgorithmActivity.this, R.layout.activity_test_astar_algorithm);
    }

    @Override
    public void init() {
        astarUtils = new AstarUtils();
        nodeAdapter = new PathNodeAdapter(initPath());
        binding.rvPathNode.setAdapter(nodeAdapter);
        binding.rvPathNode.setLayoutManager(new GridLayoutManager(this, 10));
        if (astarUtils.isDiagonal()) {
            binding.btnWalkType.setText("Diagonal");
        } else {
            binding.btnWalkType.setText("Straight");
        }

        ClickUtil.setFastClick(binding.btnWalkType, new Action1() {
            @Override
            public void call(Object o) {
                if (astarUtils.isDiagonal()) {
                    astarUtils.setIsDiagonal(false);
                    binding.btnWalkType.setText("Straight");
                } else {
                    astarUtils.setIsDiagonal(true);
                    binding.btnWalkType.setText("Diagonal");
                }
            }
        });

        ClickUtil.setFastClick(binding.btnNext, new Action1() {
            @Override
            public void call(Object o) {
                if (reachState == 1) {
                    ToastUtils.showShort("Has Reach End");
                    nodeAdapter.refreshPath(astarUtils.getNodeList());
                    return;
                } else if (reachState == 2) {
                    ToastUtils.showShort("No Road");
                    return;
                }
                reachState = astarUtils.nextStep(nodeList, nodeList.get(start), nodeList.get(end));
                nodeAdapter.refreshPath(astarUtils.getNodeList());
            }
        });

        ClickUtil.setOnLongClick(binding.btnNext, this, new Action1() {
            @Override
            public void call(Object o) {
                List<NodeBean> pathList = astarUtils.findPath(nodeList, nodeList.get(start), nodeList.get(end));
                nodeAdapter.refreshPath(astarUtils.getNodeList());
            }
        });

        ClickUtil.setFastClick(binding.btnReset, new Action1() {
            @Override
            public void call(Object o) {
                reachState = 0;
                astarUtils.reset();
                nodeAdapter.refreshPath(initPath());
            }
        });
    }


    private List<NodeBean> initPath() {
        nodeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            NodeBean node = new NodeBean();
            node.setPos(Tools.index2pos(i, 10));
            node.setIndex(i);
            node.setReachSate(0);
            nodeList.add(node);
        }

        // 障碍
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            nodeList.get(random.nextInt(100)).setReachSate(-1);
        }

        // 起点和终点
        start = random.nextInt(100);
        end = random.nextInt(100);
        nodeList.get(start).setReachSate(2);
        nodeList.get(start).setStart(true);
        nodeList.get(end).setReachSate(3);
        nodeList.get(end).setEnd(true);
        System.out.println(String.format("startPos index:%d", start));

        return nodeList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        astarUtils.reset();
    }
}
