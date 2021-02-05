package com.sdbean.megashare.twitter;

import android.content.Context;
import android.content.Intent;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

final public class TwitterAuthHelper
{


    private TwitterAuthCallback mCallback;

    private static TwitterAuthHelper mManager;

    private TwitterAuthHelper(){}
    protected static TwitterAuthHelper getInstance(){
        if (mManager == null) {
            synchronized (TwitterManager.class) {
                if (mManager == null) {
                    mManager = new TwitterAuthHelper();
                }
            }
        }
        return mManager;
    }

    protected void authorizeTwitter(Context context, TwitterAuthCallback callback) {
        mCallback = callback;
        Intent intent = new Intent(context, TwitterAuthActivity.class);
        context.startActivity(intent);

    }
    protected boolean hasLogged() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        return session != null;
    }

    protected TwitterAuthCallback getAuthCallback() { return mCallback; }

}
