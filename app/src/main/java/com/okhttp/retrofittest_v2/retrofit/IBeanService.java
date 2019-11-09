package com.okhttp.retrofittest_v2.retrofit;

import com.okhttp.retrofittest_v2.bean.BeanContent;
import com.okhttp.retrofittest_v2.bean.BeanData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBeanService {
    @GET("show")
    Call<BeanData> getMenuById(@Query("errorCode") int errorCode);

    @GET("")
    Call<BeanData> getData();
}

