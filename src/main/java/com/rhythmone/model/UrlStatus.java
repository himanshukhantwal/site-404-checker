package com.rhythmone.model;

import java.sql.Timestamp;

public class UrlStatus{
    private String url;
    private String statusDesc;
    private int statusCode;
    private Timestamp timestamp;

    public UrlStatus(String url, String statusDesc, int statusCode, Timestamp timestamp){
        this.url=url;
        this.statusCode=statusCode;
        this.statusDesc=statusDesc;
        this.timestamp=timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}