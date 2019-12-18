package com.okhttp.retrofittest_v2.retrofit;

import com.okhttp.retrofittest_v2.bean.BeanData;
import com.okhttp.retrofittest_v2.bean.Translation;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface IBeanService {
    @GET("show")
    Call<BeanData> getMenuById(@Query("errorCode") int errorCode);

    @FormUrlEncoded
    @POST
    Call<BeanData> getInfo(@Url String url, @FieldMap Map<String, String> map);

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();
}

