package com.hl95.ssm.entity;

public class MsgTemplet {
    private String tpl_id;

    private String tpl_content;

    private String status;

    private String reason;

    private String state;

    private String opinion;

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
        this.status = status == null ? null : status.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    @Override
    public String toString() {
        return "MsgTemplet{" +
                "tpl_id='" + tpl_id + '\'' +
                ", tpl_content='" + tpl_content + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", state='" + state + '\'' +
                ", opinion='" + opinion + '\'' +
                '}';
    }
}