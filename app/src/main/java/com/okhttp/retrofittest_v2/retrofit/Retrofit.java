package com.okhttp.retrofittest_v2.retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private IBeanService service;
    public static volatile String address = "";

    /**
     * 获取Retrofit实例
     * @return
     */
    public static Retrofit getRetrofit(){
        return new Retrofit();
    }

    private Retrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(address)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(IBeanService.class);
    }

    /**
     * 获取IBeanService实例
     * @return
     */
    public IBeanService getService(){
        return service;
    }
}
