package com.ds.zxm.thread;

import org.springframework.stereotype.Service;


@Service
public class LotteryUpdateConsumer {

	/*@Autowired
	private ILotteryService lotteryService;
	public static BlockingQueue<LotteryDetail> queue = new LinkedBlockingQueue<LotteryDetail>();
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
						LotteryDetail detail = queue.take();
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
