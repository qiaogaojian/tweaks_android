package com.etatech.spine;

import android.text.TextUtils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonBounds;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.Attachment;

public abstract class SpineBaseAdapter extends ApplicationAdapter {
    private static final float DEFAULT_ANIM_SWICTH_TIME = 0.3f;
    private FileHandle mAltasFileHandle;
    private FileHandle mSkeletonFileHandle;
    protected boolean mIsInited;
    protected AndroidFragmentApplication mAndroidFragmentApplication;
    protected OrthographicCamera mCamera;
    protected PolygonSpriteBatch mMeshBatch;
    protected SkeletonRenderer mRenderer;
    protected TextureAtlas mAtlas;
    protected Skeleton mSkeleton;
    protected SkeletonBounds mSkeletonBounds;
    protected AnimationState mAnimationState;
    protected AnimationStateData mAnimationStateData;
    protected SkeletonJson mSkeletonJson;
    protected SkeletonData mSkeletonData;
    private OnSpineClickListener mSpineClickListener;
    private OnCreatedLIstener mOnCreatedLIstener;
    private SkeletonRendererDebug mDebugRenderer;
    private boolean mIsDebug = false;
    private int mPadding = 0;

    public SpineBaseAdapter() {
    }

    public void setParentFragment(AndroidFragmentApplication fragmentApplication) {
        mAndroidFragmentApplication = fragmentApplication;
    }

    public void setOnSpineClickListener(final OnSpineClickListener spineClickListener) {
        mSpineClickListener = spineClickListener;
    }

    public void setOnCreatedLIstener(OnCreatedLIstener onCreatedLIstener) {
        mOnCreatedLIstener = onCreatedLIstener;
    }

    /**
     * 设置是否 Debug
     *
     * @param debug
     */
    public void setDebug(boolean debug) {
        mIsDebug = debug;
    }

    /**
     * 单位是 logical pixels
     *
     * @param padding
     */
    public void setPadding(int padding) {
        mPadding = padding;
    }

    /**
     * 注意：这些周期方法都是在子线程中执行的
     */
    @Override
    public void create() {
        try {
            onInit();
            initialize();
            onCreated();
            mIsInited = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mOnCreatedLIstener != null) {
            mOnCreatedLIstener.onCreated();
        }
    }

    private void initialize() {
        if (mAltasFileHandle == null || mSkeletonFileHandle == null) {
            throw new RuntimeException("请在createImpl中设置altas路径和skeleton路径");
        }
        mCamera = new OrthographicCamera();
        mMeshBatch = new PolygonSpriteBatch();
        mRenderer = new SkeletonRenderer();
        mRenderer.setPremultipliedAlpha(true);

        /**debug相关 可以在动画中直观的看见骨骼关系**/
        if (mIsDebug) {
            mDebugRenderer = new SkeletonRendererDebug();
        }

        mAtlas = new TextureAtlas(mAltasFileHandle);
        mSkeletonJson = new SkeletonJson(mAtlas);
        mSkeletonData = mSkeletonJson.readSkeletonData(mSkeletonFileHandle);
        /**适配方案：等比拉伸，保证高，牺牲宽，所以构图时主要元素尽量放中间**/
        float scale = (float) ((float) Gdx.graphics.getHeight() / (mSkeletonData.getHeight() + mPadding));
        mSkeletonJson.setScale(scale);//设置完scale之后要重新读取一下mSkeletonData
        mSkeletonData = mSkeletonJson.readSkeletonData(mSkeletonFileHandle);
        mSkeleton = new Skeleton(mSkeletonData);
        /**设置骨架在父布局中的位置**/
        float midHeight = Gdx.graphics.getHeight() / 2 - mSkeletonData.getHeight() / 2 * scale;
        mSkeleton.setPosition(Gdx.graphics.getWidth() / 2, midHeight > 0 ? midHeight : 0);
        mSkeletonBounds = new SkeletonBounds();
        mAnimationStateData = new AnimationStateData(mSkeletonData);
        /**设置动画切换时的过度时间**/
        mAnimationStateData.setDefaultMix(DEFAULT_ANIM_SWICTH_TIME);
        mAnimationState = new AnimationState(mAnimationStateData);
        Gdx.input.setInputProcessor(new InputAdapter() {
            final Vector3 point = new Vector3();

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                mCamera.unproject(point.set(screenX, screenY, 0));
                mSkeletonBounds.update(mSkeleton, false);
                if (mSkeletonBounds.aabbContainsPoint(point.x, point.y)) {
                    onClick();
                    if (mSpineClickListener != null) {
                        mAndroidFragmentApplication.getView().post(new Runnable() {
                            @Override
                            public void run() {
                                mSpineClickListener.onClick();
                            }
                        });
                        return true;
                    }
                }
                return true;
            }
        });
    }

    public abstract void onCreated();

    /**
     * 动画点击回调
     */
    public abstract void onClick();

    public void setAltasPath(String path, Files.FileType fileType) {
        this.mAltasFileHandle = Gdx.files.getFileHandle(path, fileType);
    }

    public void setAltasPath(String path) {
        this.mAltasFileHandle = Gdx.files.getFileHandle(path, Files.FileType.Internal);
    }

    public void setSkeletonPath(String path, Files.FileType fileType) {
        this.mSkeletonFileHandle = Gdx.files.getFileHandle(path, fileType);
    }

    public void setSkeletonPath(String path) {
        this.mSkeletonFileHandle = Gdx.files.getFileHandle(path, Files.FileType.Internal);
    }

    @Override
    public void resize(int width, int height) {
        try {
            onResizeImpl(width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resume() {
        try {
            onResumeImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        try {
            onRenderImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            onPauseImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        try {
            onDisposeImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 设置资源和动画状态
     */
    public abstract void onInit();

    public void onResizeImpl(int width, int height) {
        if (mCamera != null) {
            mCamera.setToOrtho(false);
        }
    }

    public void onResumeImpl() {

    }

    public void onRenderImpl() {
        if (mIsInited) {
            mAnimationState.update(Gdx.graphics.getDeltaTime());
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            mAnimationState.apply(mSkeleton);
            mSkeleton.updateWorldTransform();
            mCamera.update();

            mMeshBatch.getProjectionMatrix().set(mCamera.combined);
            mMeshBatch.begin();
            mRenderer.draw(mMeshBatch, mSkeleton);
            mMeshBatch.end();

            if (mIsDebug) {
                mDebugRenderer.getShapeRenderer().setProjectionMatrix(mCamera.combined);
                mDebugRenderer.draw(mSkeleton);
            }
        }
    }

    public void onPauseImpl() {

    }

    public void onDisposeImpl() {
        if (mAtlas != null) {
            mAtlas.dispose();
        }
    }

    public boolean isInited() {
        return mIsInited;
    }

    /**
     * 换装饰
     *
     * @param slotName       插槽名称
     * @param attachmentName 装饰名称
     * @return
     */
    public boolean setAttachment(String slotName, String attachmentName) {
        if (mSkeleton == null || TextUtils.isEmpty(slotName)) {
            return false;
        }
        Slot slot = mSkeleton.findSlot(slotName);
        if (slot == null) {
            return false;
        }
        if (TextUtils.isEmpty(attachmentName)) {
            slot.setAttachment(null);
        } else {
            Attachment attachment = mSkeleton.getAttachment(slotName, attachmentName);
            if (attachment == null) {
                return false;
            }
            mSkeleton.setAttachment(slotName, attachmentName);
        }
        return true;
    }

    /**
     * 换肤
     *
     * @param skinName 皮肤名称
     * @return
     */
    public boolean setSkin(String skinName) {
        if (mSkeleton == null || mSkeletonData == null || TextUtils.isEmpty(skinName)) {
            return false;
        }
        if (mSkeletonData.findSkin(skinName) == null) {
            return false;
        }
        mSkeleton.setSkin(skinName);
        return true;
    }

    /**
     * 直接切换到另一个动画
     *
     * @param aniName
     */
    public void animate(String aniName) {
        mAnimationState.addAnimation(0, aniName, true, 0);
    }

    /**
     * 播放一个动画后再播放另外一个动画
     *
     * @param ani1 第一个动画
     * @param ani2 第二个动画
     */
    public void animate(String ani1, String ani2) {
        Animation animation = mSkeletonData.findAnimation(ani1);
        mAnimationState.setAnimation(0, animation, false);
        mAnimationState.addAnimation(0, ani2, true, animation.getDuration());
    }
}

