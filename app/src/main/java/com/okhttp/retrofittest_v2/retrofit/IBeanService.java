package com.okhttp.retrofittest_v2.retrofit;

import com.okhttp.retrofittest_v2.bean.BeanContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBeanService {
    @GET("show")
    Call<BeanContent> getMenuById(@Query("errorCode") String errorCode);
}

