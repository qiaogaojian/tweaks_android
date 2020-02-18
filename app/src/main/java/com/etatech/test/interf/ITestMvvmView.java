package com.etatech.test.interf;

import com.etatech.test.view.TestMvvmActivity;

/**
 * Created by Michael
 * Date:  2020/2/18
 * Func:
 */
public interface ITestMvvmView extends BaseInterface.IView {
    TestMvvmActivity getActivity();
}
