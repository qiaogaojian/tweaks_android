package com.etatech.test.spine;

import com.etatech.spine.SpineBaseAdapter;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/5
 * Desc:
 */
public class RaptorAdapter extends SpineBaseAdapter {
    /**
     * 设置动画文件资源路径
     */
    @Override
    public void onInit() {
        setAssetsPath("raptor3.8/raptor.atlas", "raptor3.8/raptor.skel");
        setDebug(true);
        setScale(0.99f);
        setPadding(600);
    }

    /**
     * 设置动画初始状态
     */
    @Override
    public void onCreated() {
        animate("walk");
    }

    @Override
    public void onClick() {

    }
}
