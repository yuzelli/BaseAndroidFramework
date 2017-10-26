package com.example.vpubao.baseandroidframework.net;

import com.example.vpubao.vpubaopda.App;
import com.example.vpubao.vpubaopda.model.net.netinterface.IRequset;
import com.example.vpubao.vpubaopda.utils.StringUtil;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by a on 2016/5/4.
 */
public class HttpUtil {

    private static Retrofit getRetrofit(String url) {
        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
        String cacheFile = App.getAppContext().getCacheDir()+"/http";
        Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .cache(cache)
                .build();
        return new Retrofit.Builder().baseUrl(url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public static IRequset IRequest() {
        return getRetrofit(Urls.BASE_URL).create(IRequset.class);
    }

    public static RequestBody getRequestFileBody(Map<String, File> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Set<String> keyset = map.keySet();
        for (String key : keyset) {
            File file = map.get(key);
            builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        return builder.build();
    }

    public static MultipartBody.Part postStringParams(String key, String value) {
        return MultipartBody.Part.createFormData(key, value);
    }

    public static MultipartBody.Part postFileParams(String key, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(key, file.getName(), fileBody);
    }

    public static MultipartBody.Part postFileParams(String mediaType, String key, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse(mediaType), file);
        return MultipartBody.Part.createFormData(key, file.getName(), fileBody);
    }

    public static Map<String, String> requestAPI(String c, String act, String content) {
        Date now = new Date();
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("c", c);
        params.put("act", act);
        params.put("app_id", "999999");
        params.put("app_secret", "99a23c125e6fd10420eb3b6ed48ee057");
        params.put("session_key", "s1");
        params.put("timestamp", String.valueOf(now.getTime()));
        params.put("sig", generatorSignature(act, content.toString(), String.valueOf(now.getTime())));
        params.put("content", content.toString());

        return params;
    }

    protected static String generatorSignature(String act, String content, String time) {
        String str = act;
        str += "999999";
        str += "99a23c125e6fd10420eb3b6ed48ee057";
        str += "s1";
        str += time;
        str += content;
        str += "yf329^$#(&H5d~!d,.(*0";
        return StringUtil.MD5(str);
    }

}