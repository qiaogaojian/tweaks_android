package com.etatech.test.spine;

import android.net.Uri;

import com.badlogic.gdx.Files;
import com.esotericsoftware.spine.Animation;
import com.etatech.spine.SpineBaseAdapter;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/3
 * Desc:
 */
class SpineBoyAdapter extends SpineBaseAdapter {
    /**
     * 设置动画文件资源路径
     */
    @Override
    public void onInit() {
        setAltasPath("spineboy/spineboy.atlas");
        setSkeletonPath("spineboy/spineboy.json");

        setDebug(true);
        setPadding(100);
    }

    /**
     * 设置动画初始状态
     */
    @Override
    public void onCreated() {
        animate("idle");
    }

    @Override
    public void onClick() {
        animate("shoot","walk");
    }

    public void doJump() {
        animate("jump","walk");
    }

    public void doRun() {
        animate("run");
    }
}
