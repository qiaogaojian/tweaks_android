package com.etatech.test.utils.txlive;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.blankj.utilcode.util.SPUtils;
import com.etatech.test.utils.App;
import com.etatech.test.utils.Tools;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import static com.tencent.rtmp.TXLiveConstants.PLAY_WARNING_RECONNECT;

/**
 * Created by Administrator on 2017/2/22.
 */

public class PlayDao implements LifecycleObserver {
    private static final String TAG = "PlayDao";

    private Activity mMainView;
    private TXCloudVideoView mPlayerView;
    public String globalUrl = "";
    private static final int MESSAGE_ID_RECONNECTING = 0x01;
    private String mVideoPath = null;
    private volatile static PlayDao playDao;
    private int targetVolume;
    private long startPlayTime;
    private int intTimer = 0;
    private TXLivePlayer mLivePlayer = null;

    private PlayDao() {
    }

    public static PlayDao getInstance() {
        PlayDao pl = playDao;
        if (pl == null) {
            synchronized (PlayDao.class) {
                pl = playDao;
                if (pl == null) {
                    pl = new PlayDao();
                    playDao = pl;
                }
            }
        }
        return pl;
    }

    public void initPlayDao(Activity mainView, TXCloudVideoView playerView) {
        mMainView = mainView;
        mPlayerView = playerView;
        mMainView.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initPlay3();

        // RxBus.getRxBus().toFlowable(PhoneStateBean.class)
        //         .observeOn(AndroidSchedulers.mainThread())
        //         .compose(mMainView.getActivity().bindUntilEvent(ActivityEvent.DESTROY))
        //         .subscribe(new Action1<PhoneStateBean>() {
        //             @Override
        //             public void call(PhoneStateBean phoneStateBean) {
        //                 BaseActivity.currentPhoneState = phoneStateBean.state;
        //                 switch (phoneStateBean.state) {
        //                     //电话等待接听
        //                     case TelephonyManager.CALL_STATE_RINGING:
        //                         break;
        //                     //电话接听
        //                     case TelephonyManager.CALL_STATE_OFFHOOK:
        //                         if (mLivePlayer != null) {
        //                             mLivePlayer.setVolume(0);
        //                         }
        //                         break;
        //                     //电话挂机
        //                     case TelephonyManager.CALL_STATE_IDLE:
        //                         setVolume(targetVolume);
        //                         break;
        //                 }
        //             }
        //         });
    }

    private void initPlay3() {
        // 是否开启log
        TXLiveBase.setConsoleEnabled(true);

        if (mLivePlayer == null) {
            mLivePlayer = new TXLivePlayer(mMainView);
            mLivePlayer.setPlayerView(mPlayerView);
        }
        TXLivePlayConfig mPlayConfig = new TXLivePlayConfig();
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        mPlayConfig.setAutoAdjustCacheTime(true);
        mPlayConfig.setMaxAutoAdjustCacheTime(1.0f);
        mPlayConfig.setMinAutoAdjustCacheTime(1.0f);
        mPlayConfig.setConnectRetryCount(3);
        mPlayConfig.setConnectRetryInterval(3);
        mLivePlayer.enableHardwareDecode(true);
        mLivePlayer.setConfig(mPlayConfig);
        mLivePlayer.setPlayListener(playListener);
        targetVolume = 50;
        setVolume(targetVolume);
    }

    public void setVolume(int volume) {
        targetVolume = volume;
        if (!Tools.isAppRunningForeground(App.getInstance())) {
            if (mLivePlayer != null) {
                mLivePlayer.setVolume(0);
            }
        } else {
            if (mLivePlayer != null) {
                mLivePlayer.setVolume(volume);
            }
        }
    }

    public void playAction(String strUrl) {
        mVideoPath = "";
        if (mMainView == null) {
            return;
        }
        if (mMainView == null || mMainView.isFinishing()) {
            return;
        }
        startPlayTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(strUrl)) return;
        globalUrl = strUrl;
        mVideoPath = strUrl;
        try {
            if (mLivePlayer != null) {
                initPlay3();
                intTimer = 0;
                sendReconnectMessage(500);

            }
        } catch (Exception e) {
            //#798 java.lang.IllegalStateException
            e.printStackTrace();
        }
    }

    public void stopPlayAction() {
        mVideoPath = "";
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mLivePlayer != null) {//防止播放失败，没有停止
            mLivePlayer.stopPlay(true);
        }


    }

    public void release() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mLivePlayer != null) {//防止播放失败，没有停止
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        mVideoPath = "";
        if (playDao != null) {
            playDao = null;
        }
    }

    ITXLivePlayListener playListener = new ITXLivePlayListener() {
        @Override
        public void onPlayEvent(int event, Bundle bundle) {
            try {
                String playEventLog = "receive event: " + event + ", " + bundle.getString(TXLiveConstants.EVT_DESCRIPTION);
                // Tools.logUtils(TAG, playEventLog);
                if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {

                } else if (event == TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND || event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    sendReconnectMessage(1000);
                    // PushUpdateHelper.pullStreamTx(globalUrl, 1, 0, startPlayTime);
                } else if (event == TXLiveConstants.PLAY_EVT_CONNECT_SUCC) {//链接成功播放

                    // Tools.logUtils(TAG, "isPlaying=" + mLivePlayer.isPlaying());
                    //   isNeedReconnect =true;
                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
                    mHandler.removeCallbacksAndMessages(null);
                    // PushUpdateHelper.pullStreamTx(globalUrl, 1, 1, startPlayTime);
                } else if (PLAY_WARNING_RECONNECT == event) {
                    //                if (intTimer > 15){
                    //                    intTimer=14;
                    mHandler.removeCallbacksAndMessages(null);
                    sendReconnectMessage(100);
                    //                }
                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END || event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
                } else if (event < 0) {
                    // PushUpdateHelper.pullStreamTx(globalUrl, 1, 0, startPlayTime);
                }

            } catch (Exception e) {

            }

        }

        @Override
        public void onNetStatus(Bundle status) {
            String str = getNetStatusString(status);
            //            if (!TextUtils.isEmpty(str)){
            //                Tools.logUtils(TAG,str);
            //            }
            //            Log.d(TAG, "Current status, CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
            //                    ", RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
            //                    ", SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
            //                    ", FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
            //                    ", ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
            //                    ", VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
        }
    };

    //公用打印辅助函数
    protected String getNetStatusString(Bundle status) {
        String str = String.format("%-14s %-14s %-12s\n%-8s %-8s %-8s %-8s\n%-14s %-14s\n%-14s %-14s",
                "CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE),
                "RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT),
                "SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps",
                "JIT:" + status.getInt(TXLiveConstants.NET_STATUS_NET_JITTER),
                "FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS),
                "GOP:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_GOP) + "s",
                "ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps",
                "QUE:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_CACHE)
                        + "|" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE)
                        + "," + status.getInt(TXLiveConstants.NET_STATUS_V_SUM_CACHE_SIZE)
                        + "," + status.getInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE)
                        + "|" + status.getInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL)
                        + "," + status.getInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL)
                        + "," + String.format("%.1f", status.getFloat(TXLiveConstants.NET_STATUS_AUDIO_CACHE_THRESHOLD)).toString(),
                "VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps",
                "SVR:" + status.getString(TXLiveConstants.NET_STATUS_SERVER_IP),
                "AUDIO:" + status.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO));
        return str;
    }

    private void sendReconnectMessage(int time) {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), time);
    }

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != MESSAGE_ID_RECONNECTING) {
                return;
            }
            if (!Tools.isNetworkAvailable(mMainView)) {
                intTimer++;
                if (intTimer < 3) {
                    sendReconnectMessage(1000);
                }
                return;
            }

            try {
                if (mLivePlayer != null) {
                    if (mLivePlayer.isPlaying()) {
                        mLivePlayer.stopPlay(true);
                    }
                    if (mVideoPath.contains("rtmp")){
                        mLivePlayer.startPlay(mVideoPath, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
                    }else if (mVideoPath.contains(".mp4")) {
                        mLivePlayer.startPlay(mVideoPath, TXLivePlayer.PLAY_TYPE_VOD_MP4);
                    } else if (mVideoPath.contains(".mp3")) {
                        mLivePlayer.startPlay(mVideoPath, TXLivePlayer.PLAY_TYPE_VOD_MP4);
                    } else {
                        mLivePlayer.startPlay(mVideoPath, TXLivePlayer.PLAY_TYPE_LIVE_FLV);
                    }

                    intTimer++;
                    if (intTimer < 15) {
                        sendReconnectMessage(1000);
                    }

                    if (intTimer == 14) {
                        if (!mLivePlayer.isPlaying()) {
                            // PushUpdateHelper.pullStreamTx(globalUrl, 1, 0, startPlayTime);
                        }
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        if (!Tools.isAppRunningForeground(App.getInstance())) {
            if (mLivePlayer != null) {
                mLivePlayer.setVolume(0);
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (Tools.isAppRunningForeground(App.getInstance())) {
            setVolume(targetVolume);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {

    }
}
