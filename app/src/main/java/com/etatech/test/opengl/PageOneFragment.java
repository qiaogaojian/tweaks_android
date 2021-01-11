package com.etatech.test.opengl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

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
    @LayoutRes
    int layoutRes;

    public static PageOneFragment newInstance(@LayoutRes int layoutRes) {
        PageOneFragment fragment = new PageOneFragment();
        Bundle args = new Bundle();
        args.putInt("layoutRes", layoutRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pageone, container, false);

        ViewStub sampleStub = (ViewStub) view.findViewById(R.id.container);
        sampleStub.setLayoutResource(layoutRes);
        sampleStub.inflate();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            layoutRes = args.getInt("layoutRes");
        }
    }
}

