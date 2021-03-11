package com.etatech.test.spine;

import com.esotericsoftware.spine.attachments.BoundingBoxAttachment;
import com.etatech.spine.SpineBaseAdapter;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/5
 * Desc:
 */
public class GoblinAdapter extends SpineBaseAdapter {

    @Override
    public void onInit() {
        setAssetsPath("goblins/goblins.atlas", "goblins/goblins.json");
        setScale(2f);
    }

    @Override
    public void onCreated() {
        setSkin("goblin");
        animate("walk");
    }

    @Override
    public void onClick(BoundingBoxAttachment attachment) {

    }
}
