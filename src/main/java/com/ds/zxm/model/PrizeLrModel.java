package com.ds.zxm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class PrizeLrModel {

    //监控开奖数据
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    private Integer order;

}