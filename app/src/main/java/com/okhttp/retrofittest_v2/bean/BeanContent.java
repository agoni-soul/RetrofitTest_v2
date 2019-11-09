package com.okhttp.retrofittest_v2.bean;

public class BeanContent {
    private BeanAndroidContent data;
    private String errorCode;
    private String errorMsg;

    public BeanAndroidContent getData(){
        return data;
    }

    public void setDatas(BeanAndroidContent data){
        this.data = data;
    }

    public String getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }
}
