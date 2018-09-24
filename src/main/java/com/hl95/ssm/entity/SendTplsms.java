package com.hl95.ssm.entity;

import java.util.Date;

public class SendTplsms {

    private String tpl_id;

    private String tpl_content;

    private String mobile;

    private String ext;

    private String stime;

    private String rrid;

    private String status;

    private String reason;

    private String state;



    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime == null ? null : stime.trim();
    }

    public String getRrid() {
        return rrid;
    }

    public void setRrid(String rrid) {
        this.rrid = rrid == null ? null : rrid.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getTpl_id() {
        return tpl_id;
    }

    public void setTpl_id(String tpl_id) {
        this.tpl_id = tpl_id;
    }

    public String getTpl_content() {
        return tpl_content;
    }

    public void setTpl_content(String tpl_content) {
        this.tpl_content = tpl_content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "SendTplsms{" +
                "tpl_id='" + tpl_id + '\'' +
                ", tpl_content='" + tpl_content + '\'' +
                ", mobile='" + mobile + '\'' +
                ", ext='" + ext + '\'' +
                ", stime='" + stime + '\'' +
                ", rrid='" + rrid + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}