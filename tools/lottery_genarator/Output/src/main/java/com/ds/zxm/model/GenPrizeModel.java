package com.ds.zxm.model;

public class GenPrizeModel {
    private Long id;

    private String lotteryCode;

    private String no;

    private String genPrize;

    private String realPrize;

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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getGenPrize() {
        return genPrize;
    }

    public void setGenPrize(String genPrize) {
        this.genPrize = genPrize == null ? null : genPrize.trim();
    }

    public String getRealPrize() {
        return realPrize;
    }

    public void setRealPrize(String realPrize) {
        this.realPrize = realPrize == null ? null : realPrize.trim();
    }
}