package com.ssc.model;

public class CurNoModel {
    private Long id;

    private String type;

    private String lotteryCode;

    private String curNo;

    private String nextNo;

    private String nextSec;

    private String prize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize == null ? null : prize.trim();
    }
}