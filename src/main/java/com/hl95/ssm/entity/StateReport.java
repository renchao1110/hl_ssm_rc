package com.hl95.ssm.entity;

public class StateReport {
    private Integer id;

    private String platform;

    private String funikey;

    private String forgaddr;

    private String fdestaddr;

    private String fsubmittime;

    private String ffeeterminal;

    private String fserviceupid;

    private String freportcode;

    private String flinkid;

    private String fackstatus;

    private String state;

    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getFunikey() {
        return funikey;
    }

    public void setFunikey(String funikey) {
        this.funikey = funikey == null ? null : funikey.trim();
    }

    public String getForgaddr() {
        return forgaddr;
    }

    public void setForgaddr(String forgaddr) {
        this.forgaddr = forgaddr == null ? null : forgaddr.trim();
    }

    public String getFdestaddr() {
        return fdestaddr;
    }

    public void setFdestaddr(String fdestaddr) {
        this.fdestaddr = fdestaddr == null ? null : fdestaddr.trim();
    }

    public String getFsubmittime() {
        return fsubmittime;
    }

    public void setFsubmittime(String fsubmittime) {
        this.fsubmittime = fsubmittime == null ? null : fsubmittime.trim();
    }

    public String getFfeeterminal() {
        return ffeeterminal;
    }

    public void setFfeeterminal(String ffeeterminal) {
        this.ffeeterminal = ffeeterminal == null ? null : ffeeterminal.trim();
    }

    public String getFserviceupid() {
        return fserviceupid;
    }

    public void setFserviceupid(String fserviceupid) {
        this.fserviceupid = fserviceupid == null ? null : fserviceupid.trim();
    }

    public String getFreportcode() {
        return freportcode;
    }

    public void setFreportcode(String freportcode) {
        this.freportcode = freportcode == null ? null : freportcode.trim();
    }

    public String getFlinkid() {
        return flinkid;
    }

    public void setFlinkid(String flinkid) {
        this.flinkid = flinkid == null ? null : flinkid.trim();
    }

    public String getFackstatus() {
        return fackstatus;
    }

    public void setFackstatus(String fackstatus) {
        this.fackstatus = fackstatus == null ? null : fackstatus.trim();
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