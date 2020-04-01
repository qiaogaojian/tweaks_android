package com.sdbean.splashad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.lang.ref.WeakReference;

/**
 * Created by Michael
 * Data: 2020/2/19 11:49
 * Desc: 开屏广告类
 */
public class SplashAd {

    private SplashAdBean     splashAdBean;
    private SplashAdListener listener;

    private WeakReference<Activity> activity;
    private ViewGroup               container;
    private View                    splashLayout;
    private RelativeLayout          layoutBottomLogo;
    private ImageView               ivLogo;
    private ImageView               ivLogoBottom;
    private TextView                tvCopyRight;
    private TextView                tvJump;
    private AutoScaleWidthImageView imageView;
    private SimpleDraweeView        draweeView;
    private MyVideoView             videoView;
    private SimpleDraweeView        ivPreview;

    // 自定义
    private Typeface       typeface; // 字体
    private String         jumpText; // 跳过文字
    private String         copyRight; // 版权
    private Drawable       logoBottom;  // 底部logo
    private Drawable       defaultCover; // 无网络或错误时的默认开屏广告
    private int            waitTime = 3; // 下载资源等待时间
    private CountDownTimer waitTimer;// 网络不好时 最多等3秒下载资源 下不完就先显示默认图

    private PreciseCountdown     countDownTimer;
    private HttpProxyCacheServer proxy;
    private boolean              isNoAd  = false;
    private boolean              isVideo = false;

    /**
     * 无网络或错误时的默认开屏广告
     *
     * @param drawable
     */
    public void setDefaultCover(Drawable drawable) {
        this.defaultCover = drawable;
        imageView.setImageDrawable(drawable);
    }

    /**
     * 跳过文字
     *
     * @param jumpText
     */
    public void setJumpText(String jumpText) {
        this.jumpText = jumpText;
    }

    /**
     * 字体
     *
     * @param typeface
     */
    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        tvJump.setTypeface(typeface);
    }

    /**
     * 版权
     *
     * @param copyRight
     */
    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
        if (tvCopyRight != null) {
            tvCopyRight.setText(copyRight);
        }
    }

    /**
     * 底部logo
     *
     * @param logoBottom
     */
    public void setLogoBottom(Drawable logoBottom) {
        this.logoBottom = logoBottom;
        ivLogoBottom.setImageDrawable(logoBottom);
    }

    /**
     * 下载资源等待时间
     *
     * @param waitTime
     */
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * 显示正常广告时的构造方法
     *
     * @param activity     所在的Activity
     * @param splashAdBean 广告数据结构体
     * @param container
     * @param listener     广告状态的回调
     */
    public SplashAd(Activity activity, SplashAdBean splashAdBean, ViewGroup container, SplashAdListener listener) {
        this.activity = new WeakReference<Activity>(activity);
        this.splashAdBean = splashAdBean;
        this.container = container;
        this.listener = listener;
        isNoAd = false;
        if (splashAdBean != null && splashAdBean.getType() != null && splashAdBean.getType().equals("mp4")) {
            proxy = new HttpProxyCacheServer.Builder(this.activity.get()).maxCacheSize(ByteConstants.MB * 200).build();
        }
        init();
    }

    /**
     * 显示默认广告时的构造方法
     *
     * @param activity  所在的Activity
     * @param container 广告所在的FrameLayout
     * @param listener  广告状态的回调
     */
    public SplashAd(Activity activity, ViewGroup container, SplashAdListener listener) {
        this.activity = new WeakReference<Activity>(activity);
        this.container = container;
        this.listener = listener;
        isNoAd = true;
        init();
    }

    /**
     * 显示广告
     */
    public void show() {

        waitTimer = new CountDownTimer(waitTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("SplashAD", "Wait " + millisUntilFinished / 1000.0);
            }

            @Override
            public void onFinish() {
                if (isVideo) return;
                startTick();
                imageView.setVisibility(View.VISIBLE);
                listener.onSplashAdFailToShow();
            }
        }.start();

        switch (splashAdBean.getType()) {
            case "null":
                startTick();
                imageView.setVisibility(View.VISIBLE);
                listener.onSplashAdFailToShow();
                break;
            case "jpg":
            case "png":
            case "gif":
            case "webp":
                isVideo = false;
                draweeView.setVisibility(View.VISIBLE);
                draweeView.setController(Fresco.newDraweeControllerBuilder()
                        .setControllerListener(new BaseControllerListener<ImageInfo>() {
                            @Override
                            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                                super.onFinalImageSet(id, imageInfo, animatable);
                                startTick();
                                listener.onSplashAdSuccessToShow();
                                waitTimer.cancel();
                            }

                            @Override
                            public void onFailure(String id, Throwable throwable) {
                                super.onFailure(id, throwable);
                                startTick();
                                imageView.setVisibility(View.VISIBLE);
                                listener.onSplashAdFailToShow();
                            }
                        })
                        .setUri(Uri.parse(splashAdBean.getResUrl()))
                        .setOldController(draweeView.getController())
                        .setAutoPlayAnimations(true)
                        .build());
                break;
            case "mp4":
                isVideo = true;
                break;
            default:
                imageView.setVisibility(View.VISIBLE);
                startTick();
                waitTimer.cancel();
                break;
        }
    }

    /**
     * 显示默认广告图
     *
     * @param drawable 默认广告图
     */
    public void showDefault(Drawable drawable) {
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageDrawable(drawable);
        listener.onSplashAdFailToShow();
        isNoAd = true;
        startTick();
    }

    private void init() {
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(this.activity.get(), ImagePipelineConfigFactory.getImagePipelineConfig(this.activity.get()));
        }

        splashLayout = LayoutInflater.from(activity.get()).inflate(R.layout.splash_ad, container);

        layoutBottomLogo = splashLayout.findViewById(R.id.rl_bottom_logo);
        ivLogoBottom = splashLayout.findViewById(R.id.iv_bottom_logo);
        tvCopyRight = splashLayout.findViewById(R.id.tv_copy_right);

        imageView = splashLayout.findViewById(R.id.iv_ad);
        draweeView = splashLayout.findViewById(R.id.drawee_ad);
        videoView = splashLayout.findViewById(R.id.vv_ad);
        tvJump = splashLayout.findViewById(R.id.btn_jump);

        draweeView.getHierarchy().setActualImageFocusPoint(new PointF(0.5f, 0f));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNoAd)
                    return;
                goAdWeb();
            }
        });
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNoAd)
                    return;
                goAdWeb();
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNoAd)
                    return;
                goAdWeb();
            }
        });
        tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSplashAdClose();
                hide();
            }
        });

        if (splashAdBean != null && splashAdBean.getType() != null && splashAdBean.getType().equals("mp4")) {
            isVideo = true;
        }
        countDownTimer = new PreciseCountdown(isNoAd ? 5000 : splashAdBean.getStayTime() * 1000, 1000) {
            @Override
            public void onTick(final long millisUntilFinished) {
                activity.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tvJump == null) {
                            return;
                        }
                        final String value = String.valueOf((int) (millisUntilFinished / 1000));
                        if (TextUtils.isEmpty(jumpText)) {
                            tvJump.setText(value + " 跳过");
                        } else {
                            tvJump.setText(value + " " + jumpText);
                        }
                    }
                });
            }

            @Override
            public void onFinished() {
                activity.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onTick(0);
                        listener.onSplashAdFinish();
                        hide();
                    }
                });
            }
        };
    }

    private void hide() {
//        container.setVisibility(View.GONE);
//        splashLayout.setVisibility(View.GONE);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (proxy != null) {
            proxy.shutdown();
            proxy = null;
        }

//        activity = null;
//        splashLayout = null;
//        layoutBottomLogo = null;
//        ivLogo = null;
//        ivLogoBottom = null;
//        tvCopyRight = null;
//        tvJump = null;
//        imageView = null;
//        draweeView = null;
//        videoView = null;
//        container.removeAllViews();
//        container = null;
    }

    private void startTick() {
        if (!isVideo) {
            layoutBottomLogo.setVisibility(View.VISIBLE);
        }
    }

    private boolean hasStartTimer = false;

    public void startTimer() {
        if (hasStartTimer) {
            return;
        }
        hasStartTimer = true;

        if (isVideo) {
            showVideo();
        } else {
            tvJump.setVisibility(View.VISIBLE);
            countDownTimer.start();
        }
    }

    public void showVideo() {
        videoView.setVisibility(View.VISIBLE);
        layoutBottomLogo.setVisibility(View.GONE);
        String videoUrl = proxy.getProxyUrl(splashAdBean.getResUrl());
        videoView.setVideoPath(Uri.parse(videoUrl));
        videoView.setLooping(false);
        videoView.muteSound(true);
        videoView.setOnStartListener(new MyVideoView.OnStartListener() {
            @Override
            public void onStart() {
                startTick();
                listener.onSplashAdSuccessToShow();
                waitTimer.cancel();
                tvJump.setVisibility(View.VISIBLE);
                countDownTimer.start();
            }
        });
    }

    private void goAdWeb() {
        if (TextUtils.isEmpty(splashAdBean.getAdUrl())) {
            return;
        }
        listener.onSplashAdClicked();

        Intent intent = new Intent("android.intent.action.VIEW");
        Uri downloadUrl = Uri.parse(splashAdBean.getAdUrl());
        intent.setData(downloadUrl);
        activity.get().startActivity(intent);
    }
}
