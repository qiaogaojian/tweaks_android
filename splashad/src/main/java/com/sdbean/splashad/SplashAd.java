package com.sdbean.splashad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

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
    private ImageView        imageView;
    private SimpleDraweeView draweeView;
    private VideoView        videoView;
    private TextView         tvJump;
    private String           jumpText;
    private Typeface         typeface;

    private Drawable       defaultCover;
    private CountDownTimer countDownTimer;


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
        Fresco.initialize(activity);

        splashLayout = LayoutInflater.from(activity).inflate(R.layout.splash_ad, container);

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
                listener.onSplashAdSuccessToShow();
                hide();
            }
        }.start();
    }


    public void show() {
        switch (splashAdBean.getType()) {
            case "jpg":
            case "png":
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageDrawable(defaultCover);
                break;
            case "gif":
            case "webp":
                draweeView.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse("res:///" + splashAdBean.getResUrl());
                draweeView.setController(Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true)
                        .setOldController(draweeView.getController())
                        .build());
                break;
            case "mp4":
                videoView.setVisibility(View.VISIBLE);
                Uri videoUri = Uri.parse(splashAdBean.getResUrl());
                videoView.setVideoURI(videoUri);
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
