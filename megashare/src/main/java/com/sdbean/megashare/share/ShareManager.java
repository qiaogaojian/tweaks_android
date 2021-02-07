package com.sdbean.megashare.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import com.sdbean.megashare.facebook.FacebookManager;
import com.sdbean.megashare.line.LineManager;
import com.sdbean.megashare.twitter.TwitterManager;
import com.sdbean.megashare.util.FileHelper;
import com.sdbean.megashare.util.PlatformHelper;

public final class ShareManager
{

    public enum ShareChannel
    {
        Line,
        FacebookClient,
        FacebookBroswer,
        TwitterInnerApp,
        TwitterClient
    }

    private final String TAG = "ShareManager==>";


    private static ShareManager mManager;

    private ShareManager()
    {
    }

    public static ShareManager getInstance()
    {
        if (mManager == null)
        {
            synchronized (ShareManager.class)
            {
                if (mManager == null)
                {
                    mManager = new ShareManager();
                }
            }
        }
        return mManager;
    }

    /**
     * 分享图片
     *
     * @param context
     * @param image
     * @param channel
     * @param listener
     */
    public void shareImage(Context context, Bitmap image, ShareChannel channel, ShareListener listener)
    {
        ArrayList<Bitmap> img = new ArrayList<Bitmap>();
        img.add(image);

        if (channel == ShareChannel.FacebookClient)
        {
            if (!PlatformHelper.isInstalled(context, SharePlatform.Platform
                    .Facebook)) {
                Log.e(TAG, "Facebook not installed.");
                return;
            }

            FacebookManager.getInstance().sharePhotos(context, img);
        } else if (channel == ShareChannel.FacebookBroswer)
        {
            if (!PlatformHelper.isInstalled(context, SharePlatform.Platform
                    .Facebook)) {
                Log.e(TAG, "Facebook not installed.");
                return;
            }

            FacebookManager.getInstance().sharePhotos(context, img);
        } else if (channel == ShareChannel.TwitterInnerApp)
        {
            if (!PlatformHelper.isInstalled(context , SharePlatform.Platform
                    .Twitter)) {
                Log.e(TAG, "Twitter not installed");
                return;
            }
            TwitterManager.getInstance().share(context,
                                               null,
                                               "werewolf",
                                               image,
                                               null,
                                               MegaShare.Mode.Automatic,
                                               listener);
        } else if (channel == ShareChannel.TwitterClient)
        {
            if (!PlatformHelper.isInstalled(context , SharePlatform.Platform
                    .Twitter)) {
                Log.e(TAG, "Twitter not installed");
                return;
            }
            TwitterManager.getInstance().share(context,
                                               "https://werewolf.com",
                                               "werewolf",
                                               image,
                                               null,
                                               MegaShare.Mode.Native,
                                               listener);
        } else if (channel == ShareChannel.Line)
        {
            if (!PlatformHelper.isInstalled(context, SharePlatform.Platform.Line)) {
                Log.e(TAG, "Line not installed");
                return;
            }
            if (Build.VERSION.SDK_INT >= 30){
                LineManager.getInstance().shareImageR(context, image);
            }else {
                LineManager.getInstance().shareImage(context, image);
            }
        } else
        {
            Log.e(TAG, "该种方式不支持图片分享");
        }
    }

    /**
     * 分享文本
     *
     * @param context
     * @param content
     * @param channel
     * @param listener
     */
    public void shareText(Context context, String content, ShareChannel channel, ShareListener listener)
    {
        if (channel == ShareChannel.TwitterInnerApp)
        {
            if (!PlatformHelper.isInstalled(context , SharePlatform.Platform
                    .Twitter)) {
                Log.e(TAG, "Twitter not installed");
                return;
            }
            TwitterManager.getInstance().share(context,
                                               null,
                                               content,
                                               null,
                                               null,
                                               MegaShare.Mode.Automatic,
                                               listener);
        } else if (channel == ShareChannel.TwitterClient)
        {
            if (!PlatformHelper.isInstalled(context , SharePlatform.Platform
                    .Twitter)) {
                Log.e(TAG, "Twitter not installed");
                return;
            }
            TwitterManager.getInstance().share(context,
                                               null,
                                               content,
                                               null,
                                               null,
                                               MegaShare.Mode.Native,
                                               listener);
        } else if (channel == ShareChannel.Line)
        {
            if (!PlatformHelper.isInstalled(context, SharePlatform.Platform.Line)) {
                Log.e(TAG, "Line not installed");
                return;
            }
            LineManager.getInstance().shareText(context,
                                                content);
        } else
        {
            Log.e(TAG, "该种方式不支持文字分享");
        }
    }

    /**
     * 分享链接
     *
     * @param context
     * @param content
     * @param channel
     * @param listener
     */
    public void shareWebpage(Context context, WebContent content, ShareChannel channel, ShareListener listener)
    {
        if (channel == ShareChannel.FacebookClient)
        {
            if (!PlatformHelper.isInstalled(context, SharePlatform.Platform
                    .Facebook)) {
                Log.e(TAG, "Facebook not installed.");
                return;
            }
            FacebookManager.getInstance().shareWebpage(context,
                                                       content.getWebpageUrl(),
                                                       content.getQuote(),
                                                       content.getTag(),
                                                       MegaShare.Mode.Automatic,
                                                       listener);
        } else if (channel == ShareChannel.FacebookBroswer)
        {
            if (!PlatformHelper.isInstalled(context, SharePlatform.Platform
                    .Facebook)) {
                Log.e(TAG, "Facebook not installed.");
                return;
            }
            FacebookManager.getInstance().shareWebpage(context,
                                                       content.getWebpageUrl(),
                                                       content.getQuote(),
                                                       content.getTag(),
                                                       MegaShare.Mode.Feed, listener);
        } else if (channel == ShareChannel.TwitterInnerApp)
        {
            if (!PlatformHelper.isInstalled(context , SharePlatform.Platform
                    .Twitter)) {
                Log.e(TAG, "Twitter not installed");
                return;
            }
            TwitterManager.getInstance().share(context,
                                               content.getWebpageUrl(),
                                               content.getQuote(),
                                               content.getThumbImage(),
                                               content.getTag(),
                                               MegaShare.Mode.Automatic,
                                               listener);
        } else if (channel == ShareChannel.TwitterClient)
        {
            if (!PlatformHelper.isInstalled(context , SharePlatform.Platform
                    .Twitter)) {
                Log.e(TAG, "Twitter not installed");
                return;
            }
            TwitterManager.getInstance().share(context,
                                               content.getWebpageUrl(),
                                               content.getQuote(),
                                               content.getThumbImage(),
                                               null,
                                               MegaShare.Mode.Native,
                                               listener);
        } else
        {
            Log.e(TAG, "该种方式不支持链接分享");
        }
    }
}
