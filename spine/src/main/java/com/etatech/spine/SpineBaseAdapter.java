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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonBinary;
import com.esotericsoftware.spine.SkeletonBounds;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.Attachment;
import com.esotericsoftware.spine.attachments.BoundingBoxAttachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;

import javax.microedition.khronos.opengles.GL10;

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
    protected SkeletonBinary mSkeletonBinary;
    protected SkeletonData mSkeletonData;
    private OnSpineClickListener mSpineClickListener;
    private OnCreatedLIstener mOnCreatedLIstener;
    private SkeletonRendererDebug mDebugRenderer;
    private boolean mIsDebug = false;
    private boolean mActivePremultipliedAlpha = false;
    private int mPadding = 0;
    private float mXoffset = 0;
    private float mYoffset = 0;
    private float mScale = 1f;

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
     * ???????????? Debug
     *
     * @param debug
     */
    public void setDebug(boolean debug) {
        mIsDebug = debug;
    }

    /**
     * ????????? logical pixels
     *
     * @param padding
     */
    public void setPadding(int padding) {
        mPadding = padding;
    }

    /**
     * ?????????????????????????????????????????? ????????? 1
     *
     * @param scale
     */
    public void setScale(float scale) {
        mScale = scale;
    }

    /**
     * ?????????????????? activePremultipliedAlpha (?????????????????????????????????)
     *
     * @param activePremultipliedAlpha
     */
    public void setActivePremultipliedAlpha(boolean activePremultipliedAlpha) {
        mActivePremultipliedAlpha = activePremultipliedAlpha;
    }

    public void setXoffset(float xoffset) {
        mXoffset = xoffset;
    }

    public void setYoffset(float yoffset) {
        mYoffset = yoffset;
    }

    /**
     * ?????????????????????????????????????????????????????????
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
            throw new RuntimeException("??????createImpl?????????altas?????????skeleton??????");
        }
        mCamera = new OrthographicCamera();
        mMeshBatch = new PolygonSpriteBatch();
        mRenderer = new SkeletonRenderer();
        mRenderer.setPremultipliedAlpha(mActivePremultipliedAlpha);

        /**debug?????? ?????????????????????????????????????????????**/
        if (mIsDebug) {
            mDebugRenderer = new SkeletonRendererDebug();
            mDebugRenderer.setBoundingBoxes(false);
            mDebugRenderer.setRegionAttachments(false);
        }

        mAtlas = new TextureAtlas(mAltasFileHandle);
        if (mSkeletonFileHandle.toString().contains(".json")) {
            mSkeletonJson = new SkeletonJson(mAtlas);
            mSkeletonData = mSkeletonJson.readSkeletonData(mSkeletonFileHandle);
        } else {
            mSkeletonBinary = new SkeletonBinary(mAtlas);
            mSkeletonData = mSkeletonBinary.readSkeletonData(mSkeletonFileHandle);
        }

        /**???????????????????????????????????????????????????????????????????????????**/
        float scale;
        if (mSkeletonData.getHeight() >= mSkeletonData.getWidth()) {
            scale = (float) ((float) Gdx.graphics.getHeight() / (mSkeletonData.getHeight() + mPadding));
        } else {
            scale = (float) ((float) Gdx.graphics.getWidth() / (mSkeletonData.getWidth() + mPadding));
        }
        if (mSkeletonData.getHeight() == 0 || mScale != 1f) {
            scale = mScale;
        }
        if (mSkeletonFileHandle.toString().contains(".json")) {
            mSkeletonJson.setScale(scale); //?????????scale???????????????????????????mSkeletonData
            mSkeletonData = mSkeletonJson.readSkeletonData(mSkeletonFileHandle);
        } else {
            mSkeletonBinary.setScale(scale);
            mSkeletonData = mSkeletonBinary.readSkeletonData(mSkeletonFileHandle);
        }
        mSkeleton = new Skeleton(mSkeletonData);
        /**????????????????????????????????????**/
        float midHeight = Gdx.graphics.getHeight() / 2 - mSkeletonData.getHeight() / 2 * scale;
        if (mSkeletonData.getHeight() == 0 || mScale != 1f) {
            mSkeleton.setPosition(Gdx.graphics.getWidth() / 2, mPadding);
        } else {
            mSkeleton.setPosition(Gdx.graphics.getWidth() / 2, midHeight > 0 ? midHeight : 0);
        }
        mSkeletonBounds = new SkeletonBounds();
        mAnimationStateData = new AnimationStateData(mSkeletonData);
        /**????????????????????????????????????**/
        mAnimationStateData.setDefaultMix(DEFAULT_ANIM_SWICTH_TIME);
        mAnimationState = new AnimationState(mAnimationStateData);
        Gdx.input.setInputProcessor(new InputAdapter() {
            final Vector3 point = new Vector3();

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                mCamera.unproject(point.set(screenX, screenY, 0));
                mSkeletonBounds.update(mSkeleton, false);
                if (mSkeletonBounds.aabbContainsPoint(point.x, point.y)) {
                    onClick(mSkeletonBounds.containsPoint(point.x, point.y));
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

    /**
     * ????????? ???????????????????????????
     */
    public abstract void onInit();

    public abstract void onCreated();

    /**
     * ?????????????????? BoundingBoxAttachment ?????????????????????
     */
    public abstract void onClick(BoundingBoxAttachment attachment);

    /**
     * ??????spine????????????(assets????????????????????????)
     *
     * @param atlasPath    ??????
     * @param skeletonPath json??????
     */
    public void setAssetsPath(String atlasPath, String skeletonPath) {
        setAltasPath(atlasPath, Files.FileType.Internal);
        setSkeletonPath(skeletonPath, Files.FileType.Internal);
    }

    /**
     * ??????spine????????????(????????????)
     *
     * @param atlasPath    ??????
     * @param skeletonPath json??????
     */
    public void setResPath(String atlasPath, String skeletonPath) {
        setAltasPath(atlasPath, Files.FileType.Absolute);
        setSkeletonPath(skeletonPath, Files.FileType.Absolute);
    }

    /**
     * ??????spine????????????(??????????????????????????? Android\data\com.etatech.test\files)
     *
     * @param atlasPath    ??????
     * @param skeletonPath json??????
     */
    public void setExternalPath(String atlasPath, String skeletonPath) {
        setAltasPath(atlasPath, Files.FileType.External);
        setSkeletonPath(skeletonPath, Files.FileType.External);
    }

    private void setAltasPath(String path, Files.FileType fileType) {
        this.mAltasFileHandle = Gdx.files.getFileHandle(path, fileType);
    }

    private void setSkeletonPath(String path, Files.FileType fileType) {
        this.mSkeletonFileHandle = Gdx.files.getFileHandle(path, fileType);
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
            mCamera.position.x = Gdx.graphics.getWidth() / 2.0f + mXoffset;
            mCamera.position.y = Gdx.graphics.getHeight() / 2.0f + mYoffset;
            mCamera.update();

            onDrawBg();

            mMeshBatch.getProjectionMatrix().set(mCamera.combined);
            mMeshBatch.begin();
            mRenderer.draw(mMeshBatch, mSkeleton);
            mMeshBatch.end();

            if (mIsDebug) {
                mDebugRenderer.getShapeRenderer().setProjectionMatrix(mCamera.combined);
                mDebugRenderer.draw(mSkeleton);
            }

            onDrawFg();
        }
    }

    public void onDrawBg() {
        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void onDrawFg() {

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
     * ?????????
     *
     * @param slotName       ????????????
     * @param attachmentName ????????????
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
     * ??????
     *
     * @param skinName ????????????
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
     * ??????????????????????????????
     *
     * @param aniName
     */
    public void animate(String aniName) {
        mAnimationState.addAnimation(0, aniName, true, 0);
    }

    public void animate(String aniName, AnimationState.AnimationStateListener listener) {
        mAnimationState.addAnimation(0, aniName, true, 0);
        mAnimationState.addListener(listener);
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param ani1 ???????????????
     * @param ani2 ???????????????
     */
    public void animate(String ani1, String ani2) {
        Animation animation = mSkeletonData.findAnimation(ani1);
        mAnimationState.setAnimation(0, animation, false);
        mAnimationState.addAnimation(0, ani2, true, animation.getDuration());
    }
}

