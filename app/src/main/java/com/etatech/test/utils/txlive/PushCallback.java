package com.etatech.test.utils.txlive;

/**
 * Created by wxd on 2019-11-28.
 */
public interface PushCallback {

    void onCallBack(int audioFps, int videoFps, int totalAVBitrate);
}
