package com.ds.zxm.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ds.zxm.model.LotteryDetail;
import com.ds.zxm.model.StrategyDO;
import com.ds.zxm.model.TradeSchedule;

/**
 * 后二
 * @author hywin
 *
 */
@Component(value="Last2LotteryStrategy")
@Scope("prototype")
public class Last2LotteryStrategyImpl extends LotteryStrategyImpl {
	
	@PostConstruct
	@Override
	//初始化倍投计划,投注号码,赔率
	protected void initScheAndNo() {
		int psJh = 30;
		for (int i =1;i<=psJh;i++) {
			TradeSchedule tradeSchedule =new TradeSchedule();
			if (i == psJh) {
				tradeSchedule.setLoseNo(i+1);

			} else {
				tradeSchedule.setLoseNo(1);

			}
			tradeSchedule.setWinNo(1);
			tradeSchedule.setMultiple(1);
			scheMap.put("" + i, tradeSchedule);
			
		}
		
		this.rate = new BigDecimal((1+0.94)*100/2);
	}

	@Override
	protected boolean checkIsWin(LotteryDetail kjDetailDO, LotteryDetail tzNo) {

		boolean winFlag = false;
			if (tzNo.getNum1().equals(kjDetailDO.getNum1()) && tzNo.getNum2().equals(kjDetailDO.getNum2())) {
				winFlag = true;
				winLotteryDetailList.add(kjDetailDO);
			}
		/*else if ("后二".equals(lotteryMode)) {
			if (tzNo.getNum1().equals(kjDetailDO.getNum1())
					&&tzNo.getNum2().equals(kjDetailDO.getNum2())) {
				winFlag = true;
				winLotteryDetailList.add(kjDetailDO);
			}
		}*/
		return winFlag;
	}

	//是否切换为大本金
	protected boolean isTurn2Big(StrategyDO result) {
		//当前计划输额度
		boolean isAmountLimit = result.getTotalProfit().compareTo(new BigDecimal("-0.36")) <= 0;
		isAmountLimit = false;
		//从最高点回落幅度
		boolean isRateLimit = (smallStrategyResult.compareTo(BigDecimal.ZERO) > 0 && maxWinWithSmall
				.divide(smallStrategyResult, 2, RoundingMode.FLOOR).compareTo(new BigDecimal("3")) > 0)
		|| (maxWinWithSmall.compareTo(BigDecimal.ZERO) != 0 && maxWinWithSmall.subtract(smallStrategyResult).divide(maxWinWithSmall, 2).compareTo(new BigDecimal("2")) > 0);

		//从最低点反弹
		if (smallStrategyResult.compareTo(BigDecimal.ZERO) < 0) {
			if (smallStrategyResult.subtract(maxLoseWithSmall).divide(maxLoseWithSmall.abs(), 4).compareTo(new BigDecimal("0.5")) > 0 ){
				isRateLimit = true;
			}
		}
		Log.info("smallisRateLimit:" + isRateLimit + ",smallStrategyResult:" + smallStrategyResult + ",maxWinWithSmall:" + maxWinWithSmall + ",maxLoseWithSmall:" + maxLoseWithSmall);
		if (isRateLimit) {
			if (this.smallStrategyResult.compareTo(BigDecimal.ZERO) > 0) {
				this.maxWinWithSmall = this.smallStrategyResult;
			} else {
				this.maxLoseWithSmall = this.smallStrategyResult;
			}
		}
		return isAmountLimit || isRateLimit ;
	}
	//大本金一期1.8理论期数10
	//是否切换为小本金
	protected boolean isTurntoSmallAmt(StrategyDO result) {

		boolean isAmountLimit = result.getTotalProfit().compareTo(new BigDecimal("10")) >= 0
				|| result.getTotalProfit().compareTo(new BigDecimal("-36")) <= 0;
		isAmountLimit = false;
		
/*		boolean isRateLimit = maxWinWithBig.compareTo(new BigDecimal("10"))> 0&& bigStrategyResult.compareTo(BigDecimal.ZERO) != 0 && maxWinWithBig
				.divide(bigStrategyResult, 2, RoundingMode.FLOOR).compareTo(new BigDecimal("2")) > 0;*/
				
		//从最高点滑落幅度
		boolean isRateLimit = (bigStrategyResult.compareTo(BigDecimal.ZERO) > 0 && maxWinWithBig
				.divide(bigStrategyResult, 2, RoundingMode.FLOOR).compareTo(new BigDecimal("3")) > 0)
				|| (maxWinWithBig.compareTo(BigDecimal.ZERO) != 0 && maxWinWithBig.subtract(bigStrategyResult)
						.divide(maxWinWithBig, 2).compareTo(new BigDecimal("2")) > 0);

		// 从最低点反弹幅度
		if (bigStrategyResult.compareTo(BigDecimal.ZERO) < 0) {
			if (bigStrategyResult.subtract(maxLoseWithBig).divide(maxLoseWithBig.abs(), 4)
					.compareTo(new BigDecimal("0.5")) > 0) {
				isRateLimit = true;
			}
		}

		Log.info("bigisRateLimit:" + isRateLimit + ",bigStrategyResult:" + bigStrategyResult + ",maxWinWithBig:" + maxWinWithBig);
		//替换基准点
		if (isRateLimit) {
			this.maxWinWithBig = this.bigStrategyResult;
		}
		isRateLimit = false;
		
		return isAmountLimit || isRateLimit;
	}

	@Override
	public void runStrategy(String startNo, String endNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean is2Win() {
		return this.colorResultList.toString().endsWith("(红)(白)");
	}
}
