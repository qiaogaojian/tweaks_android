package com.etatech.test.view.practice;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

/**
 * Created by Michael
 * Date:  2020/10/15
 * Desc:
 */
public class PageModel {
    @LayoutRes
    public int sampleLayoutRes;
    @StringRes
    public int titleRes;
    @LayoutRes
    public int practiceLayoutRes;

    public PageModel(@LayoutRes int sampleLayoutRes, @StringRes int titleRes, @LayoutRes int practiceLayoutRes) {
        this.sampleLayoutRes = sampleLayoutRes;
        this.titleRes = titleRes;
        this.practiceLayoutRes = practiceLayoutRes;
    }
}