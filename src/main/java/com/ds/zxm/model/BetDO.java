package com.ds.zxm.model;

public class BetDO {
    private Long id;

    private String lotteryCode;

    private String seqNo;

    private String startNo;

    private String endNo;

    private String status;

    private String betType;

    private String betNo;

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

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo == null ? null : seqNo.trim();
    }

    public String getStartNo() {
        return startNo;
    }

    public void setStartNo(String startNo) {
        this.startNo = startNo == null ? null : startNo.trim();
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo == null ? null : endNo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType == null ? null : betType.trim();
    }

    public String getBetNo() {
        return betNo;
    }

    public void setBetNo(String betNo) {
        this.betNo = betNo == null ? null : betNo.trim();
    }
}