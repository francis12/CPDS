package com.ds.zxm.model;

public class TecentOnlineDO {
    private Long id;

    private String time;

    private Long onlineNum;

    private String adjustNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Long getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Long onlineNum) {
        this.onlineNum = onlineNum;
    }

    public String getAdjustNum() {
        return adjustNum;
    }

    public void setAdjustNum(String adjustNum) {
        this.adjustNum = adjustNum == null ? null : adjustNum.trim();
    }
}