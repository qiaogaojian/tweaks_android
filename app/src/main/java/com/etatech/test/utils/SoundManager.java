package com.etatech.test.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.etatech.test.R;
import com.etatech.test.ResourcesHelper;

/**
 * Created by Michael
 * Data: 2020/1/8 15:44
 * Desc: 音效管理器
 */
public class SoundManager {
    private volatile static SoundManager soundManager;
    private Context mContext;
    private MediaPlayer singlePlayer = null;
    private MediaPlayer multiPlayer = null;
    private MediaPlayer musicPlayer = null;

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

    /**
     * 播放不能同时存在的声音
     *
     * @param path
     */
    public void playSingle(String path) {
        try {
            if ("on".equals(SPUtils.getInstance().getString(GameConfig.OPERATION_SOUND_SWITCH, "on"))) {
                if (singlePlayer != null) {
                    singlePlayer.stop();
                    singlePlayer.reset();
                    singlePlayer.release();
                    singlePlayer = null;
                }
                if (FileUtils.getFileByPath(path).exists()) {
                    Uri uri = Uri.fromFile(FileUtils.getFileByPath(path));
                    singlePlayer = MediaPlayer.create(mContext, uri);
                } else {
                    singlePlayer = MediaPlayer.create(mContext, ResourcesHelper.getObbResourceUri("test.mp3"));
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

    /**
     * 播放不能同时存在的声音
     */
    public void playSingleAsset(String path) {
        try {
            if ("on".equals(SPUtils.getInstance().getString(GameConfig.OPERATION_SOUND_SWITCH, "on"))) {
                if (singlePlayer != null) {
                    singlePlayer.stop();
                    singlePlayer.reset();
                    singlePlayer.release();
                    singlePlayer = null;
                }
                Uri uri = Uri.parse(path);
                singlePlayer = MediaPlayer.create(mContext, uri);

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

    /**
     * 播放同时存在的声音
     *
     * @param path
     */
    public void playMulti(String path) {
        try {
            if ("on".equals(SPUtils.getInstance().getString(GameConfig.OPERATION_SOUND_SWITCH, "on"))) {
                if (FileUtils.getFileByPath(path).exists()) {
                    Uri uri = Uri.fromFile(FileUtils.getFileByPath(path));
                    multiPlayer = MediaPlayer.create(mContext, uri);
                } else {
                    multiPlayer = MediaPlayer.create(mContext, ResourcesHelper.getObbResourceUri("test.mp3"));
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

    /**
     * 播放音乐
     *
     * @param path
     */
    public void playMusic(String path) {
        try {
            if ("on".equals(SPUtils.getInstance().getString(GameConfig.MUSIC_BG_SWITCH, "on"))) {
                if (musicPlayer != null) {
                    musicPlayer.stop();
                    musicPlayer.reset();
                    musicPlayer.release();
                    musicPlayer = null;
                }
                if (FileUtils.getFileByPath(path).exists()) {
                    Uri uri = Uri.fromFile(FileUtils.getFileByPath(path));
                    musicPlayer = MediaPlayer.create(mContext, uri);
                } else {
                    musicPlayer = MediaPlayer.create(mContext, ResourcesHelper.getObbResourceUri("test.mp3"));
                }

                musicPlayer.setLooping(true);
                musicPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        musicPlayer.reset();
        musicPlayer.release();
        musicPlayer = null;
    }
}
