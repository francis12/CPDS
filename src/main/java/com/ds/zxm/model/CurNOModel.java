package com.ds.zxm.model;

public class CurNOModel {
    private Long id;

    private String lotteryCode;

    private String curNo;

    private String nextNo;

    private String nextSec;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode == null ? null : lotteryCode.trim();
    }

    public String getCurNo() {
        return curNo;
    }

    public void setCurNo(String curNo) {
        this.curNo = curNo == null ? null : curNo.trim();
    }

    public String getNextNo() {
        return nextNo;
    }

    public void setNextNo(String nextNo) {
        this.nextNo = nextNo == null ? null : nextNo.trim();
    }

    public String getNextSec() {
        return nextSec;
    }

    public void setNextSec(String nextSec) {
        this.nextSec = nextSec == null ? null : nextSec.trim();
    }
}