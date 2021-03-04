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
    public void onCreateImpl() {
        setAltasPath("spineboy/spineboy.atlas");
        setSkeletonPath("spineboy/spineboy.json");
    }

    /**
     * 设置动画初始状态
     */
    @Override
    public void onCreatedImpl() {
        mAnimationState.setAnimation(0, "walk", true);
    }

    /**
     * 动画点击回调
     */
    @Override
    public void doClick() {
        Animation animation = mSkeletonData.findAnimation("shoot");
        mAnimationState.setAnimation(0, animation, false);
        mAnimationState.addAnimation(0, "walk", true, animation.getDuration());
    }

    public void doJump() {
        Animation animation = mSkeletonData.findAnimation("jump");
        mAnimationState.setAnimation(0, animation, false);
        mAnimationState.addAnimation(0, "walk", true, animation.getDuration());
    }

    public void doRun() {
        Animation animation = mSkeletonData.findAnimation("run");
        mAnimationState.setAnimation(0, animation, false);
        mAnimationState.addAnimation(0, "walk", true, animation.getDuration());
    }
}
