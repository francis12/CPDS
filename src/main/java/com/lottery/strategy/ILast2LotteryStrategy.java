package com.lottery.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 策略回测接口
 * @author hywin
 *
 */
public interface ILast2LotteryStrategy {
	

	void runStrategy(String startNo, String endNo);
}
