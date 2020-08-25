package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.PathNodeAdapter;
import com.etatech.test.bean.PathNodeBean;
import com.etatech.test.databinding.ActivityTestAstarAlgorithmBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.functions.Action1;

public class TestAstarAlgorithmActivity extends BaseActivity<ActivityTestAstarAlgorithmBinding> {
    private PathNodeAdapter nodeAdapter;
    private int start;
    private int end;

    @Override
    public ActivityTestAstarAlgorithmBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestAstarAlgorithmActivity.this, R.layout.activity_test_astar_algorithm);
    }

    @Override
    public void init() {
        binding.rvPathNode.setLayoutManager(new GridLayoutManager(this, 10));

        nodeAdapter = new PathNodeAdapter(getData());
        binding.rvPathNode.setAdapter(nodeAdapter);
        nodeAdapter.notifyDataSetChanged();

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

    private List<PathNodeBean> getData() {
        List<PathNodeBean> nodeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            nodeList.add(new PathNodeBean());
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

        return nodeList;
    }
}
