package com.etatech.test.network;

import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sdbean-zlh on 16/5/20.
 */
public class NetworkManager {
    public static final String URL_BASE      = "http://jpwerewolf.53site.com/WerewolfJP/PHPApi/beta/";
    public static final String URL_BASE_TEST = "http://jpwerewolf.53site.com/WerewolfJP/PHPApi/beta/";
    public static final String URL_VERSION   = "v/";

    private static NetworkManager netWorkManager;
    private        OkHttpClient   okHttpClient;
    private        Retrofit       retrofit;

    private static Converter.Factory   gsonConverterFactory;
    private static CallAdapter.Factory rxJavaCallAdapterFactory;
    private        Gson                gson = new GsonBuilder().create();

    private boolean isDebug = true;

    public static NetworkManager getInstance() {
        if (netWorkManager == null) {
            netWorkManager = new NetworkManager();
        }
        return netWorkManager;
    }

    public NetworkManager() {
        if (isDebug) {
            this.okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(okHttpInterceptor)
                    .addInterceptor(debugInterceptor)
                    .build();
        } else {
            this.okHttpClient = new OkHttpClient.Builder().build();
        }

        gsonConverterFactory = GsonConverterFactory.create(gson);
        rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    }


    public NetApi getNetApi() {
        if (URL_VERSION.contains("test")) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(URL_BASE_TEST)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(URL_BASE)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
        }
        return retrofit.create(NetApi.class);
    }

    Interceptor okHttpInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d("Sever", "OkHttp: " + unicodeToUTF8(message));
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    Interceptor debugInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            try {
                //如果网络可用
                if (response != null && response.isSuccessful()) {
                    ResponseBody body = response.body();
                    BufferedSource source = body.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();
                    Charset charset = Charset.defaultCharset();
                    MediaType contentType = body.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(charset);
                    }
                    String string = buffer.clone().readString(charset);
                    Log.e("NetworkManager Success", unicodeToUTF8("url:" + request.url() + " response:" + string));
                } else {
                    Log.e("NetworkManager Error", unicodeToUTF8("url:" + request.url() + " response:" + ""));
                }
            } catch (Exception e) {
            }
            return response;
        }
    };

    public static String unicodeToUTF8(String src) {
        if (null == src) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();
    }
}