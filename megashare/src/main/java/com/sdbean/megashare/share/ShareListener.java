package com.sdbean.megashare.share;

public interface ShareListener
{

    public abstract void onComplete(SharePlatform.Platform platform);
    public abstract void onFail(SharePlatform.Platform platform, String errorInfo);
    public abstract void onCancel(SharePlatform.Platform platform);
}
