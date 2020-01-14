package com.etatech.test.utils;

import android.content.Context;

/**
 * Created by Michael
 * Data: 2020/1/8 15:44
 * Desc: 音乐管理器
 */
public class MusicManager {
    private volatile static MusicManager musicManager;
    private                 Context      context;

    private MusicManager(Context context) {
        this.context = context;
    }

    public static MusicManager getIntence(Context context) {

        MusicManager mp = musicManager;

        if (mp == null) {
            synchronized (MusicManager.class) {
                mp = musicManager;
                if (mp == null) {
                    mp = new MusicManager(context);
                    musicManager = mp;
                }
            }
        }
        return mp;
    }

    public static MusicManager getInstance() {

        MusicManager mp = musicManager;

        if (mp == null) {
            synchronized (MusicManager.class) {
                mp = musicManager;
                if (mp == null) {
                    mp = new MusicManager(App.getInstance());
                    musicManager = mp;
                }
            }
        }
        return mp;
    }


}
