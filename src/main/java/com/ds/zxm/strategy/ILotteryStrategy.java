package com.ds.zxm.strategy;

import java.util.List;

/**
 * 策略回测接口
 * @author hywin
 *
 */
public interface ILotteryStrategy {
	

	void runStrategy(String startNo, String endNo);

	void runStrategy(String startNo, String endNo, List<String> orignNo);

	void runStrategy(String startNo, String endNo, String originSendNo);
}
