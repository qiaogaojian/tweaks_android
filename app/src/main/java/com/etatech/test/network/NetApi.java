package com.etatech.test.network;

import com.etatech.test.bean.IpInfoBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by PC-QGJ
 * Data: 2020/1/3 17:06
 * Desc: 网络接口
 */
public interface NetApi
{
    //    //个人主页信息
    //    @POST("getUserInfo.php")
    //    @FormUrlEncoded
    //    Observable<UserInfoBean> getUserInfo(@Field("userNo") String userNo, @Field("cookie") String cookie, @Field("checkUserNo") String checkUserNo);

    //    // 获取国家区号
    //    @GET(NetworkManager.WEREWOLF_URL_VERSION + "getAreaPhoneNum.php")
    //    Observable<PhoneAreaBean> getPhoneArea(@Query("channelId") String channelId);

    @GET(NetworkManager.ALI_URL_BASE + "getIpInfo.php")
    Observable<IpInfoBean> getIpInfo(@Query("ip") String ip);

}