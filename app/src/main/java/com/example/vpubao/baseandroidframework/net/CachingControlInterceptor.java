package com.example.vpubao.baseandroidframework.net;

import com.example.vpubao.vpubaopda.App;
import com.example.vpubao.vpubaopda.utils.NetUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by a on 2016/5/10.
 */
public class CachingControlInterceptor {
    private static final String TAG = "CacheInterceptor";
    private static final int TIMEOUT_CONNECT = 0; //0秒
    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7; //7天

    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            String cache = chain.request().header("cache");

            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");

            if (cacheControl == null) {
                if (cache == null || "".equals(cache)) {
                    cache = TIMEOUT_CONNECT + "";
                }
                originalResponse = originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + cache)
                        .build();
                return originalResponse;
            } else {
                return originalResponse;
            }
        }
    };

    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isConnected(App.getAppContext())) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + TIMEOUT_DISCONNECT)
                        .build();
            }
            return chain.proceed(request);
        }
    };

}