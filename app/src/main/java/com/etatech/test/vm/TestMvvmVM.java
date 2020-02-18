package com.etatech.test.vm;

import android.content.Context;

import com.etatech.test.interf.ITestMvvmVM;
import com.etatech.test.interf.ITestMvvmView;
import com.etatech.test.model.TestMvvmModel;
import com.etatech.test.utils.Tools;

/**
 * Created by Michael
 * Date:  2020/2/18
 * Func:
 */
public class TestMvvmVM implements ITestMvvmVM {
    public ITestMvvmView mainView;
    private TestMvvmModel priceModel;

    public TestMvvmVM(ITestMvvmView mainView) {
        this.mainView = mainView;

        priceModel = Tools.getViewModel(mainView.getActivity(), TestMvvmModel.class);

        requestData();
    }

    private void requestData() {

    }

    @Override
    public Context getContext() {
        return mainView.getContext();
    }

    @Override
    public void onDestroy() {

    }
}
