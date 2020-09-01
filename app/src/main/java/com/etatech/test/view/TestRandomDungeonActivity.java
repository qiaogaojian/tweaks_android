package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.DungeonGridAdapter;
import com.etatech.test.bean.NodeBean;
import com.etatech.test.databinding.ActivityTestRandomDungeonBinding;
import com.etatech.test.utils.AstarUtils;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.DungeonGenerator;
import com.etatech.test.utils.Tools;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.functions.Action1;

public class TestRandomDungeonActivity extends BaseActivity<ActivityTestRandomDungeonBinding> {
    private DungeonGridAdapter dungeonAdapter;
    private List<NodeBean> nodeList;
    private AstarUtils astarUtils;
    private DungeonGenerator generator;
    private final int height = 51;
    private final int width = 51;

    @Override
    public ActivityTestRandomDungeonBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_random_dungeon);
    }

    @Override
    public void init() {
        astarUtils = new AstarUtils();
        generator = new DungeonGenerator();

        dungeonAdapter = new DungeonGridAdapter();
        binding.rvDungeon.setAdapter(dungeonAdapter);
        dungeonAdapter.refreshPath(getTestData());
        binding.rvDungeon.setLayoutManager(new GridLayoutManager(this, 51));

        ClickUtil.setOnClick(binding.btnGenerateDungeon, new Action1() {
            @Override
            public void call(Object o) {
                generator.Init(width, height, 100, 5);
                dungeonAdapter.refreshPath(generator.getNodeList());
            }
        });

        ClickUtil.setOnClick(binding.btnNext, new Action1() {
            @Override
            public void call(Object o) {

            }
        });

        ClickUtil.setOnClick(binding.btnReset, new Action1() {
            @Override
            public void call(Object o) {

            }
        });
    }

    private List<NodeBean> getTestData() {
        nodeList = new ArrayList<>();
        for (int i = 0; i < height * width; i++) {
            NodeBean node = new NodeBean();
            node.setPos(Tools.index2pos(i, 10));
            node.setIndex(i);
            node.setReachSate(0);
            nodeList.add(node);
        }
        return nodeList;
    }
}
