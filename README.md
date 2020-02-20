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

- SplashAD

