# AndroidTest

- Adapt Width

    测试以屏宽为标准进行适配

- Adapt Height

    测试以屏高为标准进行适配

    [参考链接](https://blankj.com/2018/12/18/android-adapt-screen-killer/)

- Test Translation

    测试Translation动画运动时的数值单位,结果是Translation动画是以px为单位进行运动的

- Test Surfaceview

    测试Surfaceview的生命周期

    1. Surfaceview自身隐藏到显示的生命周期为 surfaceDestroyed => surfaceCreated => surfaceChanged

    2. 隐藏Surfaceview父级不会隐藏Surfaceview的绘制内容,还有可能带来ANR,所以要操作Surfaceview本身

- TestDataBinding

    开启databinding 创建公共基类

- TestMvp

    mvp方式的代码结构

- TestAudio

    由于声音和音乐的不同需求 分别创建播放器进行配置管理 声音同时多个不循环 音乐同时一个循环

- TestLeak

    测试内存泄漏的8个情形和解决方法 [详细地址](https://gitee.com/qiaogaojian/notes/blob/master/2_Android/25009_Android%E5%BC%80%E5%8F%91%E5%B8%B8%E8%A7%81%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E9%97%AE%E9%A2%98%E5%92%8C%E5%AF%B9%E5%BA%94%E7%9A%84%E8%A7%A3%E5%86%B3%E6%96%B9%E6%B3%95.md)

- TestSort

    测试多基准排序

- TestAnimation

    测试recyclerview中item动画异常问题 原因在于item在notifyItemChanged()时有默认动画 去掉即可解决
    同时实现Item动画过程中总在最上层 解决下方item遮挡当前item的问题

- TestSvg

    svg常用绘制命令 svg动画

- TestMVVM

    databinding + livedata MVVM代码结构

- TestJavaRequest

    测试java原生网络请求

- TestSplashAd

    自己封装的开屏广告插件 通过inflate view填入到页面中相应的容器里实现,支持 jpg png gif webp mp4 等多种格式, 支持资源预下载缓存.

    - 接入

    ```
    implementation 'com.sdbean.splashad:splashad:1.1.1'
    ```

    - 使用

    获取广告数据,构造 SplashAdBean,实例化 SplashAd 组件(可参考天狼日服SplashActivity)

    ```java
    private void postAdvertInfo() {
        WerewolfApplication.create(SplashActivity.this)
                .getWerewolfNetwork()
                .getAdvertiseData(IS_TENCENT)
                .compose(SplashActivity.this.<Result<AdvertiseBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result<AdvertiseBean>>() {
                    @Override
                    public void call(Result<AdvertiseBean> advertiseBean) { //成功返回数据
                        if (advertiseBean.getSign() == 1 || advertiseBean.getSign() == 200) {
                            if (advertiseBean.getData().getAdvertise().size() > 0) {
                                Random random = new Random();
                                int currentNum = random.nextInt(advertiseBean.getData().getAdvertise().size());

                                SplashAdBean adBean = new SplashAdBean();

                                adBean.setAdUrl(advertiseBean.getData().getAdvertise().get(currentNum).getUrl()); 		// 设置点击跳转地址 	必须
                                adBean.setResUrl(advertiseBean.getData().getAdvertise().get(currentNum).getImage()); 	// 设置图片或视频地址 	必须
                                adBean.setType(advertiseBean.getData().getAdvertise().get(currentNum).getType()); 		// 设置广告类型 		图片时非必须 视频时必须
                                adBean.setStayTime(advertiseBean.getData().getAdvertise().get(currentNum).getTime()); 	// 设置展示时间			非必需 默认5秒

                                SplashAd splashAd = new SplashAd(SplashActivity.this, adBean, mDataBinding.splashContainer, splashAdListener);

                                splashAd.setDefaultCover(SplashActivity.this.getResources().getDrawable(R.drawable.yx_dl_bg01));// 设置网络差或错误时的默认开屏图片 	必须
                                splashAd.setLogoBottom(SplashActivity.this.getResources().getDrawable(R.drawable.yx_dl_logo)); 	// 设置底部logo图片 					必须
                                splashAd.setCopyRight("Copyright © 2019 MEGA All Rights Reserved"); 							// 设置copy right内容 					必须
                                splashAd.setTypeface(WerewolfApplication.getInstance().getTypeface()); 							// 设置跳过文字和底部copy right的字体 	非必需 	默认系统字体
                                splashAd.setJumpText(SplashActivity.this.getString(R.string.global_script_name_2_16)); 			// 设置跳过文字内容 					非必需	默认"跳过"
                                splashAd.setWaitTime(3); 																		// 设置等待资源下载时间 				非必需 	默认3秒


                                splashAd.show();
                            } else {
                                showDefaultAd();
                            }
                        } else {
                            showDefaultAd();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) { //失败返回错误信息
                        showDefaultAd();
                    }
                });
    }
    ```

    - 无广告时显示默认广告图的方法

    ```java
    private void showDefaultAd() {
        SplashAd splashAd = new SplashAd(SplashActivity.this, mDataBinding.splashContainer, splashAdListener);

        splashAd.setTypeface(WerewolfApplication.getInstance().getTypeface());
        splashAd.setJumpText(SplashActivity.this.getString(R.string.global_script_name_2_16));
        splashAd.setWaitTime(3);
        splashAd.setCopyRight("Copyright © 2019 MEGA All Rights Reserved");
        splashAd.showDefault(SplashActivity.this.getResources().getDrawable(R.drawable.yx_dl_bg01));
    }
    ```