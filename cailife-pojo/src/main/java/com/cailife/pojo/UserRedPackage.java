package com.cailife.pojo;

import java.util.Date;

public class UserRedPackage {
    private String id;

    private String userId;

    private String redPackageId;

    private Date grabTime;

    private Double grabMoney;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRedPackageId() {
        return redPackageId;
    }

    public void setRedPackageId(String redPackageId) {
        this.redPackageId = redPackageId == null ? null : redPackageId.trim();
    }

    public Date getGrabTime() {
        return grabTime;
    }

    public void setGrabTime(Date grabTime) {
        this.grabTime = grabTime;
    }

    public Double getGrabMoney() {
        return grabMoney;
    }

    public void setGrabMoney(Double grabMoney) {
        this.grabMoney = grabMoney;
    }
}