package com.ssc.vo;

import java.io.Serializable;
import java.util.Date;

public class OnlineImVO implements Serializable,Comparable{


    private Date onlinetime;
    private String onlinenumber;

    public Date getOnlinetime() {
        return onlinetime;
    }

    public void setOnlinetime(Date onlinetime) {
        this.onlinetime = onlinetime;
    }

    public String getOnlinenumber() {
        return onlinenumber;
    }

    public void setOnlinenumber(String onlinenumber) {
        this.onlinenumber = onlinenumber;
    }

    public String getOnlinechange() {
        return onlinechange;
    }

    public void setOnlinechange(String onlinechange) {
        this.onlinechange = onlinechange;
    }

    private String onlinechange;


    @Override
    public int compareTo(Object o) {
        OnlineImVO onlineImVO = (OnlineImVO) o;
        return this.getOnlinetime().compareTo(onlineImVO.getOnlinetime());
    }
}
