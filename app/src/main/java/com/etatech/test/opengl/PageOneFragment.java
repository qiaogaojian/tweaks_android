package com.etatech.test.opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2021/1/11
 * Desc:
 */
public class PageOneFragment extends Fragment {
    private GLSurfaceView mGLView;

    public PageOneFragment(GLSurfaceView GLView) {
        mGLView = GLView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pageone, container, false);
        RelativeLayout layout = view.findViewById(R.id.container);
        layout.addView(mGLView);
        return view;
    }
}

