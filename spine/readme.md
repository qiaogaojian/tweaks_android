# SpineLoader Spine 骨骼动画加载器

SpineLoader 通过对 libgdx 和 spine-libgdx 进行封装,简化了 Android 平台对 Spine 骨骼动画的加载

## 引入依赖

```s
    // implementation project(':spine')
    implementation 'com.sdbean.spine:spineLoader:0.0.1'
```

## 配置 app 中 build.gradle

```s
    android {
        defaultConfig {
            ndk {
                moduleName "spine"
                ldLibs "log", "z", "m"
                abiFilters "armeabi"
            }
        }

        sourceSets {
            main.jniLibs.srcDirs = ['../spine/libs']
        }
    }
```

## 示例

- 新建具体的 Adapter 继承 SpineBaseAdapter

```java
class SpineBoyAdapter extends SpineBaseAdapter {
    /**
     * 设置动画文件资源路径
     */
    @Override
    public void onCreateImpl() {
        setAltasPath("spineboy/spineboy.atlas");
        setSkeletonPath("spineboy/spineboy.json");
    }

    /**
     * 设置动画初始状态
     */
    @Override
    public void onCreatedImpl() {
        mAnimationState.setAnimation(0, "walk", true);
    }

    /**
     * 动画点击回调
     */
    @Override
    public void doClick() {
        Animation animation = mSkeletonData.findAnimation("shoot");
        mAnimationState.setAnimation(0, animation, false);
        mAnimationState.addAnimation(0, "walk", true, animation.getDuration());
    }

    public void doJump() {
        Animation animation = mSkeletonData.findAnimation("jump");
        mAnimationState.setAnimation(0, animation, false);
        mAnimationState.addAnimation(0, "walk", true, animation.getDuration());
    }

    public void doRun() {
        Animation animation = mSkeletonData.findAnimation("run");
        mAnimationState.setAnimation(0, animation, false);
        mAnimationState.addAnimation(0, "walk", true, animation.getDuration());
    }
}

```

- 展示动画

```java
    mSpineBaseFragment = new SpineBaseFragment();
    mSpineBoyAdapter = new SpineBoyAdapter();
    mSpineBaseFragment.setAdapter(mSpineBoyAdapter);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fl_spine, (Fragment) mSpineBaseFragment);
    transaction.commitAllowingStateLoss();
```

- 播放动作

``` java
    mSpineBoyAdapter.doJump();
    mSpineBoyAdapter.doRun();
```