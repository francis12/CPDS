package com.ssc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class MissedPrizeModel {

    //监控开奖数据
    private String no;
    private String prize;
    private Boolean isPrize;
    private Integer prizeCnt;
    private Integer missCnt;

    public Date getTime() {
        return time;
    }
    public void setPrize(Boolean prize) {
        isPrize = prize;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    @JsonIgnore
    private Date time;

    public Integer getMissCnt() {
        return missCnt;
    }

    public void setMissCnt(Integer missCnt) {
        this.missCnt = missCnt;
    }
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
    public Integer getPrizeCnt() {
        return prizeCnt;
    }

    public void setPrizeCnt(Integer prizeCnt) {
        this.prizeCnt = prizeCnt;
    }
}