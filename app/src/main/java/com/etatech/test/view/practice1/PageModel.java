package com.etatech.test.view.practice1;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

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