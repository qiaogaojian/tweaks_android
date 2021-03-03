package com.etatech.test.spine;

import com.etatech.spine.SpineBaseAdapter;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/3
 * Desc:
 */
class SpineBoyAdapter extends SpineBaseAdapter {
    @Override
    public void onCreateImpl() {
        setAltasPath("spineboy/spineboy.atlas");
        setSkeletonPath("spineboy/spineboy.json");
    }

    @Override
    public void onCreatedImpl() {
        setAnimate("walk");
    }

    @Override
    public void doClick() {
        setAnimate("shoot");
        setAnimate("walk");
    }

    public void doJump() {
        setAnimate("jump");
        setAnimate("walk");
    }

    public void doRun() {
        setAnimate("run");
        setAnimate("walk");
    }
}
