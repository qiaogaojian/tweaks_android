package com.etatech.test.spine;

import android.net.Uri;

import com.badlogic.gdx.Files;
import com.esotericsoftware.spine.Animation;
import com.etatech.spine.SpineBaseAdapter;
import com.etatech.test.utils.FileUtils;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/3
 * Desc:
 */
public class SpineBoyAdapter extends SpineBaseAdapter {
    /**
     * 设置动画文件资源路径
     */
    @Override
    public void onInit() {
        setAssetsPath("spineboy3.8/spineboy.atlas", "spineboy3.8/spineboy.json");
        // setResPath(FileUtils.getResPath() + "spineboy3.8/spineboy.atlas", FileUtils.getResPath() + "spineboy3.8/spineboy.json");
        // setResPath(FileUtils.getExternalPath() + "spineboy/spineboy.atlas", FileUtils.getExternalPath() + "spineboy/spineboy.json");
        // setExternalPath("spineboy/spineboy.atlas", "spineboy/spineboy.json");
        // setDebug(true);
        // setPadding(100);
        setScale(.8f);
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
        animate("shoot", "walk");
    }

    public void animateJump() {
        animate("jump", "walk");
    }

    public void animateRun() {
        animate("run");
    }
}
