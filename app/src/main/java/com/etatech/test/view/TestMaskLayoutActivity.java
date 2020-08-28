package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.PathNodeAdapter;
import com.etatech.test.bean.PathNodeBean;
import com.etatech.test.databinding.ActivityTestMaskLayoutBinding;
import com.etatech.test.utils.AstarUtils;
import com.etatech.test.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestMaskLayoutActivity extends BaseActivity<ActivityTestMaskLayoutBinding> {

    private PathNodeAdapter nodeAdapter;

    @Override
    public ActivityTestMaskLayoutBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_mask_layout);
    }

    @Override
    public void init() {
        binding.rvPathNode.setLayoutManager(new GridLayoutManager(this, 7));
        nodeAdapter = new PathNodeAdapter(getData());
        binding.rvPathNode.setAdapter(nodeAdapter);
        nodeAdapter.notifyDataSetChanged();
    }

    private List<PathNodeBean> getData() {
        List<PathNodeBean> nodeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PathNodeBean node = new PathNodeBean();
            node.setPos(AstarUtils.index2pos(i, 10));
            nodeList.add(new PathNodeBean());
        }

        // 障碍
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            nodeList.get(random.nextInt(100)).setReachSate(-1);
        }
        return nodeList;
    }
}