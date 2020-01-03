package com.etatech.test.network;

import com.etatech.test.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PC-QGJ
 * Data: 2020/1/3 11:20
 * Desc: 网络管理器
 */
public class NetworkManager
{
    public static final String ALI_URL_BASE = "http://ip.taobao.com/service/";

    private static NetworkManager networkManager;

    public static NetworkManager getInstance() {
        if (networkManager == null) {
            networkManager = new NetworkManager();
        }
        return networkManager;
    }

    private        OkHttpClient        okHttpClient;
    private static Converter.Factory   gsonConverterFactory;
    private static CallAdapter.Factory rxJavaCallAdapterFactory;

    private NetworkManager() {
        HttpLoggingInterceptor httpLoggingInterceptor;
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            this.okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        } else {
            this.okHttpClient = new OkHttpClient.Builder().build();
        }

        gsonConverterFactory = GsonConverterFactory.create();
        rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    }

    private NetApi netApi;

    public NetApi getAliNetApi() {
        if (netApi == null) {
            Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(ALI_URL_BASE).addConverterFactory(gsonConverterFactory).addCallAdapterFactory(rxJavaCallAdapterFactory).build();
            netApi = retrofit.create(NetApi.class);
        }
        return netApi;
    }

}
