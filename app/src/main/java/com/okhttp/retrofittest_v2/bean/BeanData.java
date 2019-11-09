package com.okhttp.retrofittest_v2.bean;

public class BeanData {
    private BeanContent data;
    private int errorCode;
    private String errorMsg;

    public BeanContent getData(){
        return data;
    }

    public void setDatas(BeanContent data){
        this.data = data;
    }

    public int getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(int errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }
}
