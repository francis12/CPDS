package com.ssc.model;

import java.util.List;

public class MissedPrizeList {

    //监控开奖数据
    private String num;
    //当前遗漏期数
    private int curMissedCnt;
    private List<MissedPrizeModel> list;

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


    public int getCurMissedCnt() {
        return curMissedCnt;
    }

    public void setCurMissedCnt(int curMissedCnt) {
        this.curMissedCnt = curMissedCnt;
    }
}