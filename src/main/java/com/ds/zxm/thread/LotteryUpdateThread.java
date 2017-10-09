package com.ds.zxm.thread;

import com.ds.zxm.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LotteryUpdateThread implements Runnable{
	@Autowired
	private LotteryService lotteryService;
	
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
