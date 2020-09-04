package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.blankj.utilcode.util.LogUtils;
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
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
        generator.Init(width, height, 100, 5);

        dungeonAdapter = new DungeonGridAdapter();
        binding.rvDungeon.setAdapter(dungeonAdapter);
        dungeonAdapter.refreshPath(getTestData());
        binding.rvDungeon.setLayoutManager(new GridLayoutManager(this, 51));

        ClickUtil.setOnClick(binding.btnGenerateDungeon, new Action1() {
            @Override
            public void call(Object o) {
                generator.generateRoom();
                dungeonAdapter.refreshPath(generator.getNodeList());
            }
        });

        ClickUtil.setOnLongClick(binding.btnGenerateDungeon, new Action1() {
            @Override
            public void call(Object o) {
                Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .take(100)
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onNext(Long value) {
                                System.out.println(String.format("try create room times:%d", value));

                                generator.generateRoom();
                                dungeonAdapter.refreshPath(generator.getNodeList());
                            }

                            @Override
                            public void onCompleted() {
                                System.out.println(String.format("complete generate room by %d times", 100));
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        });
            }
        });

        ClickUtil.setOnClick(binding.btnNext, new Action1() {
            @Override
            public void call(Object o) {
                generator.generateMaze();
                dungeonAdapter.refreshPath(generator.getNodeList());
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
