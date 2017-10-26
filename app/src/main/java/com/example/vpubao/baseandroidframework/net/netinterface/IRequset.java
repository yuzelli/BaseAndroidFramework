package com.example.vpubao.baseandroidframework.net.netinterface;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xiaos on 2017/4/13.
 */
public interface IRequset {

    @FormUrlEncoded
    @POST("scm/a/login")
    Observable<String> getResult(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("api.php?")
    Observable getMsgResult(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("api.php?c=app_search&m=hang_order_list")
    Observable getPendResult(@FieldMap Map<String, String> params);

}
