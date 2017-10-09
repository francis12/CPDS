package com.lottery.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.service.ILotteryService;


@Service
public class LotteryUpdateThread implements Runnable{
	@Autowired
	private ILotteryService lotteryService;
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(1000 * 30);
				System.out.println("开始更新最新数据");
				lotteryService.fetch2LatestFromMaxRecord();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
