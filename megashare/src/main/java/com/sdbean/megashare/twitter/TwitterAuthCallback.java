package com.sdbean.megashare.twitter;


public interface TwitterAuthCallback
{
    public abstract void onComplete();
    public abstract void onFail(String errorInfo);

}
