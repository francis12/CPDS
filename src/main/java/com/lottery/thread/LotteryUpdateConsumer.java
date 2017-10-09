package com.lottery.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.model.LotteryDetailDO;
import com.lottery.service.ILotteryService;

@Service
public class LotteryUpdateConsumer {

	/*@Autowired
	private ILotteryService lotteryService;
	public static BlockingQueue<LotteryDetailDO> queue = new LinkedBlockingQueue<LotteryDetailDO>();
	public static Executor executor = Executors.newFixedThreadPool(200);
*/
/*	public void startQueue() {
		try {
			while(true) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName() + "准备取数据!");
						LotteryDetailDO detail = queue.take();
						System.out.println(Thread.currentThread().getName() + "已经取走数据:" +  detail.getLotteryCode() + "-" + detail.getNo() +                           
								                                ",队列目前有" + queue.size() + "个数据");  
						lotteryService.fetchLotteryInfo(detail.getLotteryCode(), detail.getNo());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}*/
}
