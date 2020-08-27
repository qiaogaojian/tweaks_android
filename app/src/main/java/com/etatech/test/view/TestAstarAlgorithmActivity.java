package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.etatech.test.R;
import com.etatech.test.adapter.PathNodeAdapter;
import com.etatech.test.bean.PathNodeBean;
import com.etatech.test.databinding.ActivityTestAstarAlgorithmBinding;
import com.etatech.test.utils.AstarUtils;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.functions.Action1;

public class TestAstarAlgorithmActivity extends BaseActivity<ActivityTestAstarAlgorithmBinding> {
    private List<PathNodeBean> nodeList;
    private PathNodeAdapter nodeAdapter;
    private int start;
    private int end;
    private int reachState; // 0 未到达 1 已到达 2 此路不通


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
                if (reachState == 1) {
                    ToastUtils.showShort("Has Reach End");
                    nodeAdapter.refreshPath(AstarUtils.getNodeList());
                    return;
                } else if (reachState == 2) {
                    ToastUtils.showShort("No Road");
                    return;
                }
                reachState = AstarUtils.nextStep(nodeList, nodeList.get(start), nodeList.get(end));
                nodeAdapter.refreshPath(AstarUtils.getNodeList());
            }
        });

        ClickUtil.setOnClick(binding.btnContinue, new Action1() {
            @Override
            public void call(Object o) {

            }
        });
    }


    private List<PathNodeBean> initPath() {
        nodeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PathNodeBean node = new PathNodeBean();
            node.setPos(AstarUtils.index2pos(i, 10));
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
        nodeList.get(end).setReachSate(3);
        System.out.println(String.format("startPos index:%d", start));

        return nodeList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AstarUtils.reset();
    }
}
