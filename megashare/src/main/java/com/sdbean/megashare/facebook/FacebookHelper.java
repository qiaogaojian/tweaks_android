package com.sdbean.megashare.facebook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;

import com.sdbean.megashare.share.MegaShare;
import com.sdbean.megashare.util.FileHelper;

final class FacebookHelper {


    private final String TAG = "FacebookHelper";

    private Intent mIntent;

    protected FacebookHelper(Intent intent) {
        mIntent = intent;

    }

    protected ShareDialog.Mode getShareMode() {
        MegaShare.Mode mode = (MegaShare.Mode) mIntent.getSerializableExtra("mode");
        if (mode == MegaShare.Mode.Automatic) {
            return ShareDialog.Mode.AUTOMATIC;
        } else if (mode == MegaShare.Mode.Feed) {
            return ShareDialog.Mode.FEED;
        } else if (mode == MegaShare.Mode.Web) {
            return ShareDialog.Mode.WEB;
        } else {
            return ShareDialog.Mode.NATIVE;
        }
    }


    /**
     * 构建链接 Content 实例.
     */
    protected ShareLinkContent getLinkContent() {


        ShareHashtag hashtag = new ShareHashtag.Builder()
                .setHashtag(mIntent.getStringExtra("hashTag"))
                .build();
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(mIntent.getStringExtra("webpageUrl")))
                .setShareHashtag(hashtag)
                .setQuote(mIntent.getStringExtra("quote"))
                .build();
        return content;
    }

    /**
     * 构建照片 Content 实例.
     */
    protected SharePhotoContent getPhotoCotent() {

        Context context = FacebookManager.getInstance().getContext();
        ArrayList<Bitmap> bitmaps = FileHelper.getExternalSharePathBitmaps(context);
        ArrayList<SharePhoto> photos = new ArrayList<SharePhoto>();

        Log.e(TAG, bitmaps.size() + "");
        for (int i = 0; i < bitmaps.size(); i++) {

            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bitmaps.get(i))
                    .build();
            photos.add(photo);
        }


        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhotos(photos)
                .build();

        return content;
    }

    /**
     * 构建视频 Content 实例.
     */
    protected ShareVideoContent getVideoContent() {

        Uri videoUrl = mIntent.getParcelableExtra("local_video_path");//Uri.parse(mIntent

        Log.e(TAG, videoUrl.toString());
        ShareVideo video = new ShareVideo.Builder()
                .setLocalUrl(videoUrl)
                .build();
        ShareVideoContent content = new ShareVideoContent.Builder()
                .setVideo(video)
                .build();
        return content;
    }

    /**
     * 构建富媒体 Content 实例.
     */
    protected ShareMediaContent getMediaContent() {
        return null;
    }

}
