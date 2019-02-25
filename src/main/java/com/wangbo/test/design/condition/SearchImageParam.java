package com.wangbo.test.design.condition;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SearchImageParam {
    private Date beginTime;
    private Date endTime;
    private float threshold;
    private int topn;
    private String searchImageData;
    private String userId;

    public Date getBeginTime() {
        return beginTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public int getTopn() {
        return topn;
    }

    public void setTopn(int topn) {
        this.topn = topn;
    }

    public String getSearchImageData() {
        return searchImageData;
    }

    public void setSearchImageData(String searchImageData) {
        this.searchImageData = searchImageData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
