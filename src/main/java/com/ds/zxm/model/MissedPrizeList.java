package com.ds.zxm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MissedPrizeList {

    //监控开奖数据
    private String num;
    //当前遗漏期数
    private int curMissedCnt;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<MissedPrizeModel> getList() {
        return list;
    }

    public void setList(List<MissedPrizeModel> list) {
        this.list = list;
    }

    private List<MissedPrizeModel> list;

    public int getCurMissedCnt() {
        return curMissedCnt;
    }

    public void setCurMissedCnt(int curMissedCnt) {
        this.curMissedCnt = curMissedCnt;
    }
}