package com.ssc.vo;

//倍投
public class TradeSchedule implements Comparable<TradeSchedule>{

    //局数
    private int no;
    //中后局
    private int winNo;
    //挂后局
    private int loseNo;
    //倍数
    private int multiple;

    public TradeSchedule(int no, int winNo, int loseNo, int multiple) {
        this.no = no;
        this.winNo = winNo;
        this.loseNo = loseNo;
        this.multiple = multiple;
    }
    @Override
    public int compareTo(TradeSchedule ts) {
        return Integer.compare(this.getNo(), ts.getNo());
    }
    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public int getWinNo() {
        return winNo;
    }
    public void setWinNo(int winNo) {
        this.winNo = winNo;
    }
    public int getLoseNo() {
        return loseNo;
    }
    public void setLoseNo(int loseNo) {
        this.loseNo = loseNo;
    }
    public int getMultiple() {
        return multiple;
    }
    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

}
