package com.sdbean.splashad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created by Michael
 * Data: 2020/2/19 11:49
 * Desc: 开屏广告类
 */
public class SplashAd {

    private Activity         activity;
    private SplashAdBean     splashAdBean;
    private ViewGroup        container;
    private SplashAdListener listener;

    private View             splashLayout;
    private RelativeLayout   imageLogo;
    private ImageView        imageView;
    private SimpleDraweeView draweeView;
    private VideoView        videoView;
    private TextView         tvJump;
    private String           jumpText;
    private Typeface         typeface;

    private Drawable             defaultCover;
    private CountDownTimer       countDownTimer;
    private HttpProxyCacheServer proxy;
    private int                  waitTime = 3;


    public void setDefaultCover(Drawable drawable) {
        this.defaultCover = drawable;
    }

    public void setJumpText(String jumpText) {
        this.jumpText = jumpText;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public SplashAd(Activity activity, SplashAdBean splashAdBean, ViewGroup container, SplashAdListener listener) {
        this.activity = activity;
        this.splashAdBean = splashAdBean;
        this.container = container;
        this.listener = listener;

        init();
    }

    private void init() {
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(activity, ImagePipelineConfigFactory.getImagePipelineConfig(activity));
        }
        if (proxy == null) {
            proxy = new HttpProxyCacheServer.Builder(activity).maxCacheSize(ByteConstants.MB * 200).build();
        }

        splashLayout = LayoutInflater.from(activity).inflate(R.layout.splash_ad, container);

        imageLogo = splashLayout.findViewById(R.id.rl_logo);
        imageView = splashLayout.findViewById(R.id.iv_ad);
        draweeView = splashLayout.findViewById(R.id.drawee_ad);
        videoView = splashLayout.findViewById(R.id.vv_ad);
        tvJump = splashLayout.findViewById(R.id.btn_jump);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAdWeb();
            }
        });
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAdWeb();
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    public void show() {
        // 网络不好时 最多等3秒下载资源 下不完就先显示默认图
        final CountDownTimer waitTimer = new CountDownTimer(waitTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("SplashAD", "Wait " + millisUntilFinished / 1000.0);
            }

            @Override
            public void onFinish() {
                startTick();
                imageView.setImageDrawable(defaultCover);
                imageView.setVisibility(View.VISIBLE);
                listener.onSplashAdFailToShow();
            }
        }.start();

        switch (splashAdBean.getType()) {
            case "jpg":
            case "png":
            case "gif":
            case "webp":
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
                                imageView.setImageDrawable(defaultCover);
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
                videoView.setVisibility(View.VISIBLE);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                            @Override
                            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                                    videoView.setAlpha(1);
                                    videoView.setBackgroundColor(Color.TRANSPARENT);
                                    startTick();
                                    waitTimer.cancel();
                                }
                                return true;
                            }
                        });
                    }
                });
                String videoUrl = proxy.getProxyUrl(splashAdBean.getResUrl());
                videoView.setVideoPath(videoUrl);
                videoView.setAlpha(0);
                videoView.start();
                break;
            default:
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageDrawable(defaultCover);
                break;
        }
    }

    private void hide() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        splashLayout = null;
        imageView = null;
        draweeView = null;
        videoView = null;
        tvJump = null;
        container.removeAllViews();
    }

    private void startTick() {
        tvJump.setVisibility(View.VISIBLE);
        imageLogo.setVisibility(View.INVISIBLE);
        countDownTimer = new CountDownTimer(splashAdBean.getStayTime() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String value = String.valueOf((int) (millisUntilFinished / 1000) + 1);
                tvJump.setTypeface(typeface);
                if (TextUtils.isEmpty(jumpText)) {
                    tvJump.setText(value + " 跳过");
                } else {
                    tvJump.setText(value + " " + jumpText);
                }
            }

            @Override
            public void onFinish() {
                listener.onSplashAdFinish();
                hide();
            }
        }.start();
    }

    private void goAdWeb() {
        if (TextUtils.isEmpty(splashAdBean.getAdUrl())) {
            return;
        }
        listener.onSplashAdClicked();

        Intent intent = new Intent("android.intent.action.VIEW");
        Uri downloadUrl = Uri.parse(splashAdBean.getAdUrl());
        intent.setData(downloadUrl);
        activity.startActivity(intent);
    }
}
