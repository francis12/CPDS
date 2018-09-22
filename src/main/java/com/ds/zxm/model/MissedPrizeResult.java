package com.ds.zxm.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MissedPrizeResult implements Serializable{

    private List<MissedPrizeList> list;

    public List<MissedPrizeList> getColdList() {
        return coldList;
    }

    public void setColdList(List<MissedPrizeList> coldList) {
        this.coldList = coldList;
    }

    private List<MissedPrizeList> coldList;

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