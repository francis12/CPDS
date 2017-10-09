package com.lottery.model;

import java.text.ParseException;
import java.util.Date;

import com.stock.util.DateUtils;

/**
 * "issueNo":"21-06-2017-2402"
 * @author hywin
 *
 */
public class LotteryparseInfo {

	private Date currentTime;
	//转换后期号
	private String convertNo;
	//别名期号
	private String aliasNo;
	
	
	/**
	 * //计算距离期数
	 * 一分钟：1
	 * 一个小时:
	 * @param info
	 * @return
	 */
	public int calLimitCnt(LotteryparseInfo info) {
		Long distanceLimit = DateUtils.calculateMiniute(this.getCurrentTime(), info.getCurrentTime());
		return distanceLimit.intValue();
		
	}
	public static void main(String[] args) {
		Long s = (long) 3.6;
		System.out.println(s.intValue());
	}
	//20170622-0771
	@SuppressWarnings("deprecation")
	public String convert2AliasNo() {
		//瑞典时间转换成北京时间
		Date bjTime = org.apache.commons.lang.time.DateUtils.addHours(this.currentTime, 7);
		String dateStr = DateUtils.date2String(bjTime, "yyyyMMdd-");
		int s = bjTime.getHours()*60 + bjTime.getMinutes() + 1;
		String hmStr = "0000" + String.valueOf(s);
		return dateStr + hmStr.substring(hmStr.length() -4);
	}
	public String getAliasNo() {
		return convert2AliasNo();
	}
	public void setAliasNo(String aliasNo) {
		this.aliasNo = aliasNo;
	}
	public Date getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	public String getConvertNo() {
		return convertNo;
	}
	public void setConvertNo(String convertNo) {
		this.convertNo = convertNo;
	}
	
	public LotteryparseInfo() {
	}
	public LotteryparseInfo(Date currentTime) {
		this.currentTime = currentTime;
	}
	public LotteryparseInfo next() {
		Date dateAdd = org.apache.commons.lang.time.DateUtils.addMinutes(currentTime, 1);
		return new LotteryparseInfo(dateAdd);
	}
	public String getUseNo() {
		return 	DateUtils.date2String(currentTime, "dd-MM-YYYY-HHmm");
	}
	public boolean equals(LotteryparseInfo obj) {
		super.equals(obj);
		return getUseNo().equals(obj.getUseNo());
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
