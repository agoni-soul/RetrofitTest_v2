package com.okhttp.retrofittest_v2.bean;

public class BeanContent {
    private int curPage;
    private BeanAndroidContent datas;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;

    public int getCurPage(){
        return curPage;
    }

    public void setCurPage(int curPage){
        this.curPage = curPage;
    }

    public BeanAndroidContent getDatas(){
        return datas;
    }

    public void setDatas(BeanAndroidContent datas){
        this.datas = datas;
    }

    public int getOffset(){
        return offset;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public boolean getOver(){
        return over;
    }

    public void setpa(boolean over){
        this.over = over;
    }

    public int getPageCount(){
        return pageCount;
    }

    public void setPageCount(int pageCount){
        this.pageCount = pageCount;
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }

    public int getTotal(){
        return total;
    }

    public void setTotal(int offset){
        this.total = total;
    }
}
