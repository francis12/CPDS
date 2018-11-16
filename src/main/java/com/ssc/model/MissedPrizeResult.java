package com.ssc.model;

import java.io.Serializable;
import java.util.List;

public class MissedPrizeResult implements Serializable{

    private List<MissedPrizeList> list;

    public List<MissedPrizeList> getZhuList() {
        return zhuList;
    }

    public void setZhuList(List<MissedPrizeList> zhuList) {
        this.zhuList = zhuList;
    }

    private List<MissedPrizeList> zhuList;

    public List<MissedPrizeList> getZhu3List() {
        return zhu3List;
    }

    public void setZhu3List(List<MissedPrizeList> zhu3List) {
        this.zhu3List = zhu3List;
    }

    private List<MissedPrizeList> zhu3List;

    public List<MissedPrizeList> getZhu3ColdList() {
        return zhu3ColdList;
    }

    public void setZhu3ColdList(List<MissedPrizeList> zhu3ColdList) {
        this.zhu3ColdList = zhu3ColdList;
    }

    private List<MissedPrizeList> zhu3ColdList;


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