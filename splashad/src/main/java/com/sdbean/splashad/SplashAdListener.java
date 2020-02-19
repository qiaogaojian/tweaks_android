package com.sdbean.splashad;

/**
 * Created by Michael
 * Data: 2020/2/19 11:45
 * Desc: 开屏广告接口
 */
public interface SplashAdListener {
    /**
     * 开屏广告展示成功
     */
    void onSplashAdSuccessToShow();

    /**
     * 开屏广告展示失败
     */
    void onSplashAdFailToShow();

    /**
     * 开屏广告被点击
     */
    void onSplashAdClicked();

    /**
     * 开屏广告被跳过
     */
    void onSplashAdClose();
}
