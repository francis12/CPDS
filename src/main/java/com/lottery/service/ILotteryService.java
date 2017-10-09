package com.lottery.service;

import com.lottery.model.LotteryDO;

public interface ILotteryService {

	public void  saveLottery(LotteryDO lottery) throws Exception;
	//抓取指定期数数据
	public void fetchLotteryInfo(String lotteryCode, String no) throws Exception; 
	/**
	 * 
	 * @param lotteryCode
	 * @param no
	 * @param type: 0 -- 只抓取当前no一期， 1 -- 抓取no当天从no到最后一期  2.按日期抓取 3 -- 从no开始抓取到最新开奖
	 * @throws Exception
	 */
	public void fetchLotteryInfo(String lotteryCode, String no, int type) throws Exception; 
	
	public void fetch2LatestFromMaxRecord() throws Exception;
}
