package com.etatech.test.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;

/**
 * Created by Michael
 * Data: 2020/1/8 15:44
 * Desc: 音效管理器
 */
public class SoundManager {
    private volatile static SoundManager soundManager;
    private                 Context      mContext;
    private                 MediaPlayer  singlePlayer = null;
    private                 MediaPlayer  multiPlayer  = null;
    private                 String       rootPath     = Environment.getExternalStorageDirectory().getAbsolutePath() + "/001/";

    private SoundManager(Context context) {
        this.mContext = context;
    }

    private static SoundManager getIntence(Context context) {

        SoundManager mp = soundManager;

        if (mp == null) {
            synchronized (SoundManager.class) {
                mp = soundManager;
                if (mp == null) {
                    mp = new SoundManager(context);
                    soundManager = mp;
                }
            }
        }
        return mp;
    }

    public static SoundManager getInstance() {

        SoundManager mp = soundManager;

        if (mp == null) {
            synchronized (SoundManager.class) {
                mp = soundManager;
                if (mp == null) {
                    mp = new SoundManager(App.getInstance());
                    soundManager = mp;
                }
            }
        }
        return mp;
    }

    public void playSingle(String path) {  // 声音互相覆盖
        try {
            if ("on".equals(SPUtils.getInstance().getString(GameConfig.OPERATION_SOUND_SWITCH, "on"))) {
                if (FileUtils.getFileByPath(rootPath + path + ".mp3").exists()) {
                    Uri uri = Uri.fromFile(FileUtils.getFileByPath(rootPath + path + ".mp3"));
                    if (singlePlayer != null && singlePlayer.isPlaying()) {
                        singlePlayer.stop();
                    }
                    singlePlayer = MediaPlayer.create(mContext, uri);
                }

                singlePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                        mp.release();
                        singlePlayer = null;
                    }
                });
                singlePlayer.setLooping(false);
                singlePlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMulti(String path) {  // 声音同时播放
        try {
            if ("on".equals(SPUtils.getInstance().getString(GameConfig.OPERATION_SOUND_SWITCH, "on"))) {
                if (FileUtils.getFileByPath(rootPath + path + ".mp3").exists()) {

                    Uri uri = Uri.fromFile(FileUtils.getFileByPath(rootPath + path + ".mp3"));
                    multiPlayer = MediaPlayer.create(mContext, uri);
                }

                multiPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                        mp.release();
                        multiPlayer = null;
                    }
                });
                multiPlayer.setLooping(false);
                multiPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
