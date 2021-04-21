package com.etatech.test.utils.txlive;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.etatech.test.BuildConfig;
import com.etatech.test.utils.BaseActivity;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;

import static com.tencent.rtmp.TXLiveConstants.AUTO_ADJUST_LIVEPUSH_STRATEGY;
import static com.tencent.rtmp.TXLiveConstants.PUSH_ERR_NET_DISCONNECT;
import static com.tencent.rtmp.TXLiveConstants.PUSH_EVT_PUSH_BEGIN;
import static com.tencent.rtmp.TXLiveConstants.PUSH_WARNING_NET_BUSY;
import static com.tencent.rtmp.TXLiveConstants.PUSH_WARNING_RECONNECT;
import static com.tencent.rtmp.TXLiveConstants.PUSH_WARNING_SERVER_DISCONNECT;

/**
 * Created by Administrator on 2017/3/17.
 */

public class PushDao implements LifecycleObserver {
    private static final String TAG = "PushDao";
    public String globalUrl = "";
    private Activity mMainView;
    private volatile static PushDao pushDao;
    private boolean mIsPushing = false; //游戏内判断参数
    private TXLivePusher mLivePusher;
    private TXLivePushConfig mLivePushConfig;
    private long startPushTime;

    private PushDao() {

    }

    public static PushDao getInstance() {
        PushDao pd = pushDao;
        if (pd == null) {
            synchronized (PushDao.class) {
                pd = pushDao;
                if (pd == null) {
                    pd = new PushDao();
                    pushDao = pd;
                }
            }
        }
        return pd;
    }

    //游戏内初始化
    private void initPush(final Boolean isPushV) {
        //推流
        mLivePusher = new TXLivePusher(mMainView);
        mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.setVideoEncodeGop(1);
        // mLivePushConfig.setMaxVideoBitrate(Tools.getShare().getInt("videoBitrateMax", 800));
        // mLivePushConfig.setMinVideoBitrate(Tools.getShare().getInt("videoBitrateMin", 300));
        mLivePushConfig.setAutoAdjustBitrate(true);
        mLivePushConfig.setConnectRetryCount(10);
        mLivePushConfig.setConnectRetryInterval(2);
        mLivePushConfig.setVideoEncoderXMirror(true);
        mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_HARDWARE); // 启动硬编
        TXLiveBase.setConsoleEnabled(true);

        //开启纯音频推流，只有在调用 startPusher 前设置才会生效。
        mLivePushConfig.enablePureAudioPush(true);
        mLivePusher.setMirror(true);
        mLivePushConfig.setAutoAdjustStrategy(AUTO_ADJUST_LIVEPUSH_STRATEGY);
        //音频采样率  默认值：48000。
        //其他值：8000、16000、32000、44100、48000。
        mLivePushConfig.setAudioSampleRate(48000);
        mLivePusher.setAudioVolumeEvaluationListener(new TXLivePusher.ITXAudioVolumeEvaluationListener() {
            @Override
            public void onAudioVolumeEvaluationNotify(int volume) {
                if (null != mMainView) {
                    // mMainView.beginMcBuffer(volume);  //音量监听
                }
            }
        });
        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.setPushListener(new ITXLivePushListener() {
            @Override
            public void onPushEvent(int i, Bundle bundle) {

                // Tools.logUtils("pushdao", "onPushEvent=" + i);
                try {
                    if (i == TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL) {//推流失败
                        // PushUpdateHelper.pullStreamTx(globalUrl, 0, 0, startPushTime);
                        if (mIsPushing) {
                            sendReconnectMessage(100);
                        }
                    } else if (i == PUSH_EVT_PUSH_BEGIN) {//推流成功
                        // if (BaseActivity.checkPhoneState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                        //     if (mLivePusher != null && mIsPushing) {
                        //         if (mLivePusher.isPushing()) {
                        //             mLivePusher.setMute(true);
                        //         }
                        //     }
                        // }
                        // PushUpdateHelper.pullStreamTx(globalUrl, 0, 1, startPushTime);
                    } else if (i == PUSH_WARNING_NET_BUSY) {//网络差
                    } else if (i == PUSH_WARNING_RECONNECT) {//网络断连，已启动重连流程（重试失败超过三次会放弃）
                        if (mIsPushing) {
                            sendReconnectMessage(100);
                        }
                    } else if (i == PUSH_ERR_NET_DISCONNECT) {//网络断连，且连续三次无法重新连接，需要自行重启推流
                        if (mIsPushing) {
                            sendReconnectMessage(100);
                        }
                    } else if (i == PUSH_WARNING_SERVER_DISCONNECT) {
                    } else if (i == TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL) {
                        if (mIsPushing) {
                            sendReconnectMessage(0);
                        }
                        // PushUpdateHelper.pullStreamTx(globalUrl, 0, 0, startPushTime);
                    } else if (i == TXLiveConstants.PUSH_ERR_INVALID_ADDRESS) {
                        // PushUpdateHelper.pullStreamTx(globalUrl, 0, 0, startPushTime);
                    } else if (i < 0) {
                        // PushUpdateHelper.pullStreamTx(globalUrl, 0, 0, startPushTime);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onNetStatus(Bundle bundle) {
                try {
                    // Tools.logUtils(TAG, "/tag=" + getStatus(bundle));
                } catch (Exception e) {

                }
            }
        });
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
        //                         if (mLivePusher != null && mIsPushing) {
        //                             if (mLivePusher.isPushing()) {
        //                                 mLivePusher.setMute(true);
        //                             }
        //                         }
        //                         break;
        //                     //电话挂机
        //                     case TelephonyManager.CALL_STATE_IDLE:
        //                         if (mIsPushing) {
        //                             if (BaseActivity.checkPhoneState() == TelephonyManager.CALL_STATE_OFFHOOK) {
        //                                 return;
        //                             }
        //                             if (mLivePusher != null && mLivePusher.isPushing()) {
        //                                 mLivePusher.setMute(false);
        //                             }
        //                         }
        //                         break;
        //                 }
        //             }
        //         });
    }

    public void initPushDao(Activity mainView, Boolean isPushV) {
        mMainView = mainView;
        mainView.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initPush(isPushV);
    }


    public void sendReconnectMessage(int time) {
        if (mLivePusher == null || TextUtils.isEmpty(globalUrl))
            return;
        if (mLivePusher.isPushing()) {
            mLivePusher.stopPusher();
        }
        mLivePusher.enableAudioVolumeEvaluation(300);
        mLivePusher.startPusher(globalUrl);
    }

    public void destroyPush() {
        if (mLivePushConfig != null) {
            mLivePushConfig = null;
        }
        if (mLivePusher != null) {
            mLivePusher.setPushListener(null);
            mLivePusher.stopPusher();
            mLivePusher = null;

        }
        mIsPushing = false;
        if (pushDao != null) {
            pushDao = null;
        }
    }

    public void publishStream(boolean isPublic, String url) {
        if (isPublic) {
            globalUrl = url;
            if (!mIsPushing) {
                mIsPushing = true;
                startOwnPushing(url);
            }
        } else {
            stopPushAction();
        }
    }

    public void previewStream() {
        if (mLivePusher != null) {
            mIsPushing = false;
        }
    }

    public void startOwnPushing(String videoUrl) {
        globalUrl = videoUrl;
        if (mMainView == null || mMainView.isFinishing()) {
            return;
        }
        startPushTime = System.currentTimeMillis();
        if (!mIsPushing) {
            mIsPushing = true;
        }
        if (TextUtils.isEmpty(globalUrl)) return;

        if (mLivePusher == null) {
            return;
        }

        if (mLivePusher.isPushing()) {
            mLivePusher.stopPusher();
        }
        mLivePusher.enableAudioVolumeEvaluation(300);
        int result = mLivePusher.startPusher(globalUrl);
        // Tools.logUtils(TAG, "ret=" + result);
    }

    private void stopPushAction() {
        stopPushStreaming();
    }

    //停止推流
    private void stopPushStreaming() {
        mIsPushing = false;
        if (mLivePusher != null) {
            // 停止本地预览
            mLivePusher.stopCameraPreview(true);
            // 停止推流
            mLivePusher.stopPusher();
            // 隐藏本地预览的View
        }
        mIsPushing = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        destroyPush();
    }

    /**
     * 获取当前推流状态
     *
     * @param status
     * @return
     */
    private String getStatus(Bundle status) {
        String str = String.format("%-14s %-14s %-12s\n%-8s %-8s %-8s %-8s\n%-14s %-14s %-12s\n%-14s %-14s",
                "CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE),
                "RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT),
                "SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps",
                "JIT:" + status.getInt(TXLiveConstants.NET_STATUS_NET_JITTER),
                "FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS),
                "GOP:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_GOP) + "s",
                "ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps",
                "QUE:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_CACHE) + "|" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE),
                "DRP:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_DROP) + "|" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_DROP),
                "VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps",
                "SVR:" + status.getString(TXLiveConstants.NET_STATUS_SERVER_IP),
                "AUDIO:" + status.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO));
        return str;
    }

}