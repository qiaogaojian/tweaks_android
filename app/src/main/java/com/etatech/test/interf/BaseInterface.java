package com.etatech.test.interf;

import android.app.Activity;
import android.content.Context;

public interface BaseInterface
{
    interface IView
    {
        Activity getActivity();

        Context getContext();
    }

    interface IVM
    {
        Context getContext();

        void onDestroy();
    }
}
