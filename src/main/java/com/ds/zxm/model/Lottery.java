package com.ds.zxm.model;

/**
 * Created by zl on 2015/8/27.
 */
public class Lottery {
    private String lotteryCode;
    private String lotteryName;
    private String creator;  
    private String modifier;
    
	public String getLotteryCode() {
		return lotteryCode;
	}
	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}



}
