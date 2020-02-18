package com.etatech.test.model;

import com.etatech.test.bean.BtcPriceBean;
import com.etatech.test.network.NetworkManager;
import com.etatech.test.utils.BaseModel;

/**
 * Created by Michael
 * Date:  2020/2/18
 * Func:
 */
public class TestMvvmModel extends BaseModel<BtcPriceBean> {

    public void getBtcPrice() {
        requestData(NetworkManager.getInstance()
                .getNetApi("https://api.gemini.com/v1/")
                .getBtcPrice());
    }
}
