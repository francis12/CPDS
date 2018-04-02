package com.ds.zxm.vo;

import java.util.List;

public class DSResponse {
    private String ret;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getCaipiao() {
        return caipiao;
    }

    public void setCaipiao(String caipiao) {
        this.caipiao = caipiao;
    }

    public String getRecentid() {
        return recentid;
    }

    public void setRecentid(String recentid) {
        this.recentid = recentid;
    }

    public DSPrizeVO[] getPrizes() {
        return prizes;
    }

    public void setPrizes(DSPrizeVO[] prizes) {
        this.prizes = prizes;
    }

    private String caipiao;
    private String recentid;
    private DSPrizeVO[] prizes;
}
