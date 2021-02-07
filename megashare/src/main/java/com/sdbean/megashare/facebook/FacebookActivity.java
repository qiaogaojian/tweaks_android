package com.sdbean.megashare.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import com.sdbean.megashare.share.MegaShare;
import com.sdbean.megashare.share.SharePlatform;
import com.sdbean.megashare.util.FileHelper;

final public class FacebookActivity extends Activity {

    private final String TAG = "FacebookActivity";

    CallbackManager mCbm;
    ShareDialog mSd;
    FacebookCallback mCb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIntent();
    }

    /**
     * 处理 Intent.
     */
    private void handleIntent() {

        Intent intent = getIntent();
        FacebookHelper helper = new FacebookHelper(intent);
        if (intent.getSerializableExtra("type") == MegaShare.ShareContentType.Webpage) {
            shareWebpage(helper.getLinkContent(), helper.getShareMode());
        } else if (intent.getSerializableExtra("type") == MegaShare.ShareContentType.Photo) {
            sharePhoto(helper.getPhotoCotent(), ShareDialog.Mode.NATIVE);
        } else if (intent.getSerializableExtra("type") == MegaShare.ShareContentType.Video) {
            shareVideo(helper.getVideoContent(), ShareDialog.Mode.NATIVE);
        }
    }

    /**
     * 注册分享结果监听.
     */
    private void registerCallback() {

        final Context context = FacebookManager.getInstance().getContext();

        mCbm = CallbackManager.Factory.create();
        mSd = new ShareDialog(this);
        mCb = new FacebookCallback() {
            @Override
            public void onSuccess(Object o) {
                if (FacebookManager.getInstance().getListener() != null) {
                    FacebookManager.getInstance().getListener().onComplete(SharePlatform
                            .Platform.Facebook);
                }
                FileHelper.deleteExternalShareDirectory(context);
                finish();
            }

            @Override
            public void onCancel() {
                if (FacebookManager.getInstance().getListener() != null) {
                    FacebookManager.getInstance().getListener().onCancel(SharePlatform
                            .Platform.Facebook);
                }
                FileHelper.deleteExternalShareDirectory(context);
                finish();

            }

            @Override
            public void onError(FacebookException error) {
                if (FacebookManager.getInstance().getListener() != null) {
                    FacebookManager.getInstance().getListener().onFail(SharePlatform
                                    .Platform.Facebook,
                            error.toString());
                }
                FileHelper.deleteExternalShareDirectory(context);
                finish();

            }
        };
        mSd.registerCallback(mCbm, mCb);
    }

    // 分享网页
    private void shareWebpage(ShareLinkContent content, ShareDialog.Mode mode) {
        registerCallback();
        if (mSd.canShow(content, mode)) {
            mSd.show(content, mode);
        }
    }

    // 分享图片
    private void sharePhoto(SharePhotoContent content, ShareDialog.Mode mode) {
        registerCallback();
        if (mSd.canShow(content, mode)) {
            if (mode == ShareDialog.Mode.NATIVE || mode == ShareDialog.Mode.AUTOMATIC) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
            }
            mSd.show(content, mode);
        }
    }

    // 分享视频
    private void shareVideo(ShareVideoContent content, ShareDialog.Mode mode) {
        registerCallback();
        if (mSd.canShow(content, mode)) {
            mSd.show(content, mode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCbm.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
