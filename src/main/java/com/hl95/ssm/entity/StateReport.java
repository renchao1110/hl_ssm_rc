package com.hl95.ssm.entity;

public class StateReport {
    private Integer id;

    private String platform;

    private String funikey;

    private String forgaddr;

    private String fdestaddr;

    private String fsubmittime;

    private String ffeeterminal;

    private Integer fserviceupid;

    private String freportcode;

    private Integer flinkid;

    private String fackstatus;

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

    public Integer getFserviceupid() {
        return fserviceupid;
    }

    public void setFserviceupid(Integer fserviceupid) {
        this.fserviceupid = fserviceupid;
    }

    public String getFreportcode() {
        return freportcode;
    }

    public void setFreportcode(String freportcode) {
        this.freportcode = freportcode == null ? null : freportcode.trim();
    }

    public Integer getFlinkid() {
        return flinkid;
    }

    public void setFlinkid(Integer flinkid) {
        this.flinkid = flinkid;
    }

    public String getFackstatus() {
        return fackstatus;
    }

    public void setFackstatus(String fackstatus) {
        this.fackstatus = fackstatus == null ? null : fackstatus.trim();
    }
}