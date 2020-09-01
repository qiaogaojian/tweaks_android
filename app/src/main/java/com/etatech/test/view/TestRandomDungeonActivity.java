package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.DungeonGridAdapter;
import com.etatech.test.bean.PathNodeBean;
import com.etatech.test.databinding.ActivityTestRandomDungeonBinding;
import com.etatech.test.utils.AstarUtils;
import com.etatech.test.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestRandomDungeonActivity extends BaseActivity<ActivityTestRandomDungeonBinding> {
    private DungeonGridAdapter dungeonAdapter;
    private List<PathNodeBean> nodeList;
    private AstarUtils astarUtils;
    private final int height = 51;
    private final int width = 51;

    @Override
    public ActivityTestRandomDungeonBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_random_dungeon);
    }

    @Override
    public void init() {
        astarUtils = new AstarUtils();
        dungeonAdapter = new DungeonGridAdapter();
        binding.rvDungeon.setAdapter(dungeonAdapter);
        binding.rvDungeon.setLayoutManager(new GridLayoutManager(this, 51));
        dungeonAdapter.refreshPath(getTestData());

    }

    private void generateMap() {

    }

    private List<PathNodeBean> getTestData() {
        nodeList = new ArrayList<>();
        for (int i = 0; i < height * width; i++) {
            PathNodeBean node = new PathNodeBean();
            node.setPos(astarUtils.index2pos(i, 10));
            node.setIndex(i);
            node.setReachSate(0);
            nodeList.add(node);
        }

        // 障碍
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            nodeList.get(random.nextInt(height * width)).setReachSate(-1);
        }
        return nodeList;
    }
}
