package com.hl95.ssm.entity;

public class SmsFinish {
    private Integer id;

    private String userid;

    private String sendid;

    private String sendspeed;

    private String requesttime;

    private String scheduletime;

    private String expiretime;

    private String smcontent;

    private String mobile;

    private String orgnumber;

    private String state;

    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid == null ? null : sendid.trim();
    }

    public String getSendspeed() {
        return sendspeed;
    }

    public void setSendspeed(String sendspeed) {
        this.sendspeed = sendspeed == null ? null : sendspeed.trim();
    }

    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime == null ? null : requesttime.trim();
    }

    public String getScheduletime() {
        return scheduletime;
    }

    public void setScheduletime(String scheduletime) {
        this.scheduletime = scheduletime == null ? null : scheduletime.trim();
    }

    public String getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime == null ? null : expiretime.trim();
    }

    public String getSmcontent() {
        return smcontent;
    }

    public void setSmcontent(String smcontent) {
        this.smcontent = smcontent == null ? null : smcontent.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getOrgnumber() {
        return orgnumber;
    }

    public void setOrgnumber(String orgnumber) {
        this.orgnumber = orgnumber == null ? null : orgnumber.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}