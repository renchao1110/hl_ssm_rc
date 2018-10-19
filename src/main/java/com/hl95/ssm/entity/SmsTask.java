package com.hl95.ssm.entity;

public class SmsTask {
    private Integer id;

    private String userid;

    private String sendid;

    private String branchid;

    private String sendspeed;

    private String requesttime;

    private String scheduletime;

    private String expiretime;

    private String serviceid;

    private String orgaddr;

    private String smcontent;

    private String smpram;

    private String state;

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

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid == null ? null : branchid.trim();
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

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid == null ? null : serviceid.trim();
    }

    public String getOrgaddr() {
        return orgaddr;
    }

    public void setOrgaddr(String orgaddr) {
        this.orgaddr = orgaddr == null ? null : orgaddr.trim();
    }

    public String getSmcontent() {
        return smcontent;
    }

    public void setSmcontent(String smcontent) {
        this.smcontent = smcontent == null ? null : smcontent.trim();
    }

    public String getSmpram() {
        return smpram;
    }

    public void setSmpram(String smpram) {
        this.smpram = smpram == null ? null : smpram.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    @Override
    public String toString() {
        return "SmsTask{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", sendid='" + sendid + '\'' +
                ", branchid='" + branchid + '\'' +
                ", sendspeed='" + sendspeed + '\'' +
                ", requesttime='" + requesttime + '\'' +
                ", scheduletime='" + scheduletime + '\'' +
                ", expiretime='" + expiretime + '\'' +
                ", serviceid='" + serviceid + '\'' +
                ", orgaddr='" + orgaddr + '\'' +
                ", smcontent='" + smcontent + '\'' +
                ", smpram='" + smpram + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}