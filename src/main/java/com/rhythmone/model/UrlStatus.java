package com.rhythmone.model;

public class UrlStatus{
    private String url;
    private String statusDesc;
    private int statusCode;

    public UrlStatus(String url,String statusDesc,int statusCode){
        this.url=url;
        this.statusCode=statusCode;
        this.statusDesc=statusDesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}