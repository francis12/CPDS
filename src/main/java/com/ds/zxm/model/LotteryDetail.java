package com.ds.zxm.model;

import java.util.Date;

public class LotteryDetail implements Comparable<LotteryDetail>{
    private Long id;

    private String lotteryCode;

    private String no;

    private Date time;

    private String num1;

    private String num2;

    private String num3;

    private String num4;

    private String num5;

    private String num6;

    private String num7;

    private String num8;

    private String aliasNo;

    private String lotteryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode == null ? null : lotteryCode.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1 == null ? null : num1.trim();
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2 == null ? null : num2.trim();
    }

    public String getNum3() {
        return num3;
    }

    public void setNum3(String num3) {
        this.num3 = num3 == null ? null : num3.trim();
    }

    public String getNum4() {
        return num4;
    }

    public void setNum4(String num4) {
        this.num4 = num4 == null ? null : num4.trim();
    }

    public String getNum5() {
        return num5;
    }

    public void setNum5(String num5) {
        this.num5 = num5 == null ? null : num5.trim();
    }

    public String getNum6() {
        return num6;
    }

    public void setNum6(String num6) {
        this.num6 = num6 == null ? null : num6.trim();
    }

    public String getNum7() {
        return num7;
    }

    public void setNum7(String num7) {
        this.num7 = num7 == null ? null : num7.trim();
    }

    public String getNum8() {
        return num8;
    }

    public void setNum8(String num8) {
        this.num8 = num8 == null ? null : num8.trim();
    }

    public String getAliasNo() {
        return aliasNo;
    }

    public void setAliasNo(String aliasNo) {
        this.aliasNo = aliasNo == null ? null : aliasNo.trim();
    }

    public String getLotteryDate() {
        return lotteryDate;
    }

    public void setLotteryDate(String lotteryDate) {
        this.lotteryDate = lotteryDate == null ? null : lotteryDate.trim();
    }

	@Override
	public int compareTo(LotteryDetail o) {
		return this.getAliasNo().compareTo(o.getAliasNo());
	}
	
/*	public int calDistanceNum(LotteryDetail o){
		DateUtils.calculateDaysBetween(this.ge, otherDate)
	}*/
}