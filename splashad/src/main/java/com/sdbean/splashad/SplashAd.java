package com.sdbean.splashad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

    private Activity activity;
    private SplashAdBean splashAdBean;
    private ViewGroup container;
    private SplashAdListener listener;

    private View splashLayout;
    private RelativeLayout layoutLogo;
    private RelativeLayout layoutBottomLogo;
    private ImageView ivLogo;
    private ImageView ivLogoBottom;
    private TextView tvCopyRight;

    private ImageView imageView;
    private SimpleDraweeView draweeView;
    private VideoView videoView;
    private TextView tvJump;

    // 自定义
    private Typeface typeface; // 字体
    private String jumpText; // 跳过文字
    private String copyRight; // 版权
    private Drawable logo;  // 底部logo
    private int bgColor;  // logo背景色
    private Drawable defaultCover; // 无网络或错误时的默认开屏广告
    private int waitTime = 3; // 下载资源等待时间

    private CountDownTimer countDownTimer;
    private HttpProxyCacheServer proxy;
    private boolean isNoAd = false;

    /**
     * 无网络或错误时的默认开屏广告
     * @param drawable
     */
    public void setDefaultCover(Drawable drawable) {
        this.defaultCover = drawable;
        imageView.setImageDrawable(drawable);
    }

    /**
     * 跳过文字
     * @param jumpText
     */
    public void setJumpText(String jumpText) {
        this.jumpText = jumpText;
    }

    /**
     * 字体
     * @param typeface
     */
    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        tvJump.setTypeface(typeface);
    }

    /**
     * 版权
     * @param copyRight
     */
    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
        tvCopyRight.setText(copyRight);
    }

    /**
     * 应用logo
     * @param logo
     */
    public void setLogo(Drawable logo) {
        this.logo = logo;
        ivLogo.setImageDrawable(logo);
        ivLogoBottom.setImageDrawable(logo);
    }

    /**
     * 下载资源等待时间
     * @param waitTime
     */
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * logo背景色
     * @param bgColor
     */
    public void setBgColor(String bgColor) {
        this.bgColor = Color.parseColor(bgColor);
        layoutLogo.setBackgroundColor(this.bgColor);
    }

    /**
     * 显示正常广告时的构造方法
     * @param activity 所在的Activity
     * @param splashAdBean 广告数据结构体
     * @param container
     * @param listener 广告状态的回调
     */
    public SplashAd(Activity activity, SplashAdBean splashAdBean, ViewGroup container, SplashAdListener listener) {
        this.activity = activity;
        this.splashAdBean = splashAdBean;
        this.container = container;
        this.listener = listener;
        isNoAd = false;
        init();
    }

    /**
     * 显示默认广告时的构造方法
     * @param activity 所在的Activity
     * @param container 广告所在的FrameLayout
     * @param listener 广告状态的回调
     */
    public SplashAd(Activity activity, ViewGroup container, SplashAdListener listener) {
        this.activity = activity;
        this.container = container;
        this.listener = listener;
        isNoAd = true;
        init();
    }

    /**
     * 显示广告
     */
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
                                    listener.onSplashAdSuccessToShow();
                                    waitTimer.cancel();
                                }
                                return true;
                            }
                        });
                        mp.setVolume(0f, 0f);
                        mp.setLooping(true);
                    }
                });
                String videoUrl = proxy.getProxyUrl(splashAdBean.getResUrl());
                videoView.setVideoPath(videoUrl);
                videoView.setAlpha(0);
                videoView.start();
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
            Fresco.initialize(activity, ImagePipelineConfigFactory.getImagePipelineConfig(activity));
        }
        if (proxy == null) {
            proxy = new HttpProxyCacheServer.Builder(activity).maxCacheSize(ByteConstants.MB * 200).build();
        }

        splashLayout = LayoutInflater.from(activity).inflate(R.layout.splash_ad, container);

        layoutLogo = splashLayout.findViewById(R.id.rl_logo);
        layoutBottomLogo = splashLayout.findViewById(R.id.rl_bottom_logo);
        ivLogo = splashLayout.findViewById(R.id.iv_ad);
        ivLogoBottom = splashLayout.findViewById(R.id.iv_bottom_logo);
        tvCopyRight = splashLayout.findViewById(R.id.tv_copy_right);

        imageView = splashLayout.findViewById(R.id.iv_ad);
        draweeView = splashLayout.findViewById(R.id.drawee_ad);
        videoView = splashLayout.findViewById(R.id.vv_ad);
        tvJump = splashLayout.findViewById(R.id.btn_jump);

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
            layoutBottomLogo.setVisibility(View.GONE);
            // 视频剪裁全屏
            initViewSize();
        }
    }

    public int screenWidth;
    public int screenHeight;

    private void initViewSize() {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        float density = dm.density;
        int screenWidth = (int) (this.screenWidth / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (this.screenHeight / density);// 屏幕高度(dp)
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.screenWidth, this.screenHeight);

        if ((float) this.screenHeight / this.screenWidth > 16.0f / 9) {
            layoutParams.width = (int) (dm.heightPixels / 16.0 * 9);
        } else if ((float) this.screenHeight / this.screenWidth < 16.0 / 9) {
            layoutParams.height = (int) (dm.widthPixels / 9.0 * 16);
        }
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        videoView.setLayoutParams(layoutParams);
    }

    private void hide() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        activity = null;
        splashLayout = null;
        imageView = null;
        draweeView = null;
        videoView = null;
        tvJump = null;
        container.removeAllViews();
    }

    private void startTick() {
        tvJump.setVisibility(View.VISIBLE);
        layoutLogo.setVisibility(View.INVISIBLE);

        countDownTimer = new CountDownTimer(isNoAd ? 5000 : splashAdBean.getStayTime() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String value = String.valueOf((int) (millisUntilFinished / 1000) + 1);
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
