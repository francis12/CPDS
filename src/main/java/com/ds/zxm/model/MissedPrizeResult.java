package com.ds.zxm.model;

import java.util.List;
import java.util.Map;

public class MissedPrizeResult {

    private List<MissedPrizeList> list;

    public List<MissedPrizeList> getList() {
        return list;
    }

    public void setList(List<MissedPrizeList> list) {
        this.list = list;
    }

    //30qi和60qi冷热排名
    private List<PrizeLrModelList> lrList;

    public List<PrizeLrModelList> getLrList() {
        return lrList;
    }

    public void setLrList(List<PrizeLrModelList> lrList) {
        this.lrList = lrList;
    }
}