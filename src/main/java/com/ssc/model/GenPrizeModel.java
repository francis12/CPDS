package com.ssc.model;

public class GenPrizeModel {
    private Long id;

    private String lotteryCode;

    private String no;

    private String genPrize;

    private String type;

    private String realPrize;

    private String isPrized;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRealPrize() {
        return realPrize;
    }

    public void setRealPrize(String realPrize) {
        this.realPrize = realPrize == null ? null : realPrize.trim();
    }

    public String getIsPrized() {
        return isPrized;
    }

    public void setIsPrized(String isPrized) {
        this.isPrized = isPrized == null ? null : isPrized.trim();
    }
}