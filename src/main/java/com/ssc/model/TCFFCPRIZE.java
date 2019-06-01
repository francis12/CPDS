package com.ssc.model;

import java.util.Date;

public class TCFFCPRIZE implements Comparable{
    private Long id;

    private String lotteryCode;

    private String no;

    private String prize;

    private Integer wan;

    private Integer qian;

    private Integer bai;

    private Integer shi;

    private Integer ge;

    private Integer onlineNum;

    private Integer adjustNum;

    private String aliasNo;

    private Date lotteryDate;

    private Date time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(String isMatch) {
        this.isMatch = isMatch;
    }

    private String isMatch;
    private String isBdMatch;

    public String getIsBdMatch() {
        return isBdMatch;
    }

    public void setIsBdMatch(String isBdMatch) {
        this.isBdMatch = isBdMatch;
    }

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

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize == null ? null : prize.trim();
    }

    public Integer getWan() {
        return wan;
    }

    public void setWan(Integer wan) {
        this.wan = wan;
    }

    public Integer getQian() {
        return qian;
    }

    public void setQian(Integer qian) {
        this.qian = qian;
    }

    public Integer getBai() {
        return bai;
    }

    public void setBai(Integer bai) {
        this.bai = bai;
    }

    public Integer getShi() {
        return shi;
    }

    public void setShi(Integer shi) {
        this.shi = shi;
    }

    public Integer getGe() {
        return ge;
    }

    public void setGe(Integer ge) {
        this.ge = ge;
    }

    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }

    public Integer getAdjustNum() {
        return adjustNum;
    }

    public void setAdjustNum(Integer adjustNum) {
        this.adjustNum = adjustNum;
    }

    public String getAliasNo() {
        return aliasNo;
    }

    public void setAliasNo(String aliasNo) {
        this.aliasNo = aliasNo == null ? null : aliasNo.trim();
    }

    public Date getLotteryDate() {
        return lotteryDate;
    }

    public void setLotteryDate(Date lotteryDate) {
        this.lotteryDate = lotteryDate;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof TCFFCPRIZE) {
            TCFFCPRIZE tcffcprize = (TCFFCPRIZE) o;
            if (this.getTime().compareTo(tcffcprize.getTime()) == 0) {
                return this.getPrize().compareTo(tcffcprize.getPrize());
            } else {
                return this.getTime().compareTo(tcffcprize.getTime());
            }
        }
        return 0;
    }
}