package com.sdbean.megashare.twitter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

import com.sdbean.megashare.share.ShareListener;
import com.sdbean.megashare.share.SharePlatform;
import com.sdbean.megashare.util.FileHelper;

final public class TwitterTweetResultReciver extends BroadcastReceiver {

    private ShareListener mListener = TwitterManager.getInstance().getShareListener();
    private Context       mContext  = TwitterManager.getInstance().getContext();

    @Override
    public void onReceive(Context context, Intent intent) {
        /**
         * 处理发推结果
         **/
        if (mListener != null) {
            if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {

                mListener.onComplete(SharePlatform.Platform.Twitter);

            } else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction())) {

                // 重复的发推会发送失败, 就像新浪微博一样不能重复发同样的内容
                mListener.onFail(SharePlatform.Platform.Twitter, "发推失败");

            } else if (TweetUploadService.TWEET_COMPOSE_CANCEL.equals(intent.getAction())) {

                mListener.onCancel(SharePlatform.Platform.Twitter);
            }
        }
        FileHelper.deleteExternalShareDirectory(mContext);

    }
}
