package com.etatech.spine;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.badlogic.gdx.backends.android.AndroidGraphics;
/**
 * Created by J.Tommy on 17/5/25.
 */

public class SpineBaseFragment extends AndroidFragmentApplication implements FragmentLifeCycleListener {
    protected ApplicationListener mAdapter;
    private View mView;
    private boolean mIsVisible = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.r = cfg.g = cfg.b = cfg.a = 8;
        mView = initializeForView(mAdapter, cfg);
        if (mView instanceof SurfaceView) {
            SurfaceView glView = (SurfaceView) mView;
            glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            glView.setZOrderOnTop(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mView;
    }

    public ApplicationListener getAdapter() {
        return mAdapter;
    }

    @Override
    public void onVisibleChange(boolean isVisible) {
        this.mIsVisible = isVisible;
        if (!isVisible) {
            setGone();
        } else {
            setVisible();
        }
    }

    private void setVisible() {
        if (getGraphics() != null) {
            onResume();
            View view = ((AndroidGraphics) getGraphics()).getView();
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setGone() {
        if (getGraphics() != null) {
            onPause();
            View view = ((AndroidGraphics) getGraphics()).getView();
            view.setVisibility(View.GONE);
        }
    }

    public void setAdapter(ApplicationListener roleSpineAdapter) {
        mAdapter = roleSpineAdapter;
        // mAdapter.setParentFragment(this);
    }

    @Override
    public void startActivity(Intent intent) {

    }
}
