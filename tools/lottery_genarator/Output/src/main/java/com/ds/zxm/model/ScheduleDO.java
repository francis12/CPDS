package com.ds.zxm.model;

public class ScheduleDO {
    private Long id;

    private String lotteryCode;

    private String genId;

    private String no;

    private String winNo;

    private String loseNo;

    private String multiple;

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

    public String getGenId() {
        return genId;
    }

    public void setGenId(String genId) {
        this.genId = genId == null ? null : genId.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getWinNo() {
        return winNo;
    }

    public void setWinNo(String winNo) {
        this.winNo = winNo == null ? null : winNo.trim();
    }

    public String getLoseNo() {
        return loseNo;
    }

    public void setLoseNo(String loseNo) {
        this.loseNo = loseNo == null ? null : loseNo.trim();
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple == null ? null : multiple.trim();
    }
}