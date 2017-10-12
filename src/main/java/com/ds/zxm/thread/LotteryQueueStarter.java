package com.ds.zxm.thread;

import com.ds.zxm.service.LotteryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("LotteryQueueStarter")
@Scope("singleton")
public class LotteryQueueStarter implements Runnable{
/*
	@Autowired
	LotteryUpdateConsumer lotteryUpdateConsumer;*/
	@Autowired
	LotteryService lotteryService;
	private String lotteryCode;

	Logger log = Logger.getLogger(LotteryQueueStarter.class);
	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	private String no;
	
	public LotteryQueueStarter() {
		
	}
	
	public LotteryQueueStarter(String lotteryCode, String no) {
		this.lotteryCode = lotteryCode;
		this.no = no;
	}
	@Override
	public void run() {
		try {
			lotteryService.fetchLotteryInfo(this.lotteryCode, this.no);
		} catch (Exception e) {
			log.error(this.lotteryCode + "--" + this.no + "  error", e);
		}
		
	}
}
