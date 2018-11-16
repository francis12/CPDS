package com.ssc.model;

import java.io.Serializable;
import java.util.List;

public class PrizeLrModelList implements Serializable {

    public int getLatestNum() {
        return latestNum;
    }

    public void setLatestNum(int latestNum) {
        this.latestNum = latestNum;
    }

    public List<PrizeLrModel> getList() {
        return list;
    }

    private int latestNum;
    private List<PrizeLrModel> list;

    public void setLatestNum(Integer latestNum) {
        this.latestNum = latestNum;
    }
    public void setList(List<PrizeLrModel> list) {
        this.list = list;
    }
}