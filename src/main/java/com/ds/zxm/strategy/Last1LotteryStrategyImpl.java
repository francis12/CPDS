package com.ds.zxm.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ds.zxm.model.LotteryDetail;
import com.ds.zxm.model.StrategyDO;
import com.ds.zxm.model.TradeSchedule;

@Component(value="Last1LotteryStrategy")
@Scope("prototype")
public class Last1LotteryStrategyImpl extends LotteryStrategyImpl {
	
	@PostConstruct
	@Override
	//初始化倍投计划,投注号码,赔率
	protected void initScheAndNo() {

		TradeSchedule tradeSchedule1 =new TradeSchedule();
		tradeSchedule1.setWinNo(1);
		tradeSchedule1.setLoseNo(2);
		tradeSchedule1.setMultiple(1);
		scheMap.put("1", tradeSchedule1);
		
		TradeSchedule tradeSchedule2 =new TradeSchedule();
		tradeSchedule2.setWinNo(1);
		tradeSchedule2.setLoseNo(3);
		tradeSchedule2.setMultiple(2);
		scheMap.put("2", tradeSchedule2);
		
		TradeSchedule tradeSchedule3 =new TradeSchedule();
		tradeSchedule3.setWinNo(1);
		tradeSchedule3.setLoseNo(4);
		tradeSchedule3.setMultiple(4);
		scheMap.put("3", tradeSchedule3);
		
		TradeSchedule tradeSchedule4 =new TradeSchedule();
		tradeSchedule4.setWinNo(1);
		tradeSchedule4.setLoseNo(5);
		tradeSchedule4.setMultiple(8);
		scheMap.put("4", tradeSchedule4);
		
		TradeSchedule tradeSchedule5 =new TradeSchedule();
		tradeSchedule5.setWinNo(1);
		tradeSchedule5.setLoseNo(1);
		tradeSchedule5.setMultiple(16);
		scheMap.put("5", tradeSchedule5);
		
		//后一
		LotteryDetail detail1 = new LotteryDetail();
		detail1.setNum1("1");
		noList.add(detail1);
		LotteryDetail detail2 = new LotteryDetail();
		detail2.setNum1("3");
		noList.add(detail2);
		LotteryDetail detail3 = new LotteryDetail();
		detail3.setNum1("5");
		noList.add(detail3);

		LotteryDetail detail4 = new LotteryDetail();
		detail4.setNum1("7");
		noList.add(detail4);
		
		LotteryDetail detail5 = new LotteryDetail();
		detail5.setNum1("9");
		noList.add(detail5);
		
		this.rate = new BigDecimal("" + (1+0.94)*10/2);
	
	}

	@Override
	protected boolean checkIsWin(LotteryDetail kjDetailDO, LotteryDetail tzNo) {

		boolean winFlag = false;
			if (tzNo.getNum1().equals(kjDetailDO.getNum1())) {
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
	@Override
	protected boolean isTurn2Big(StrategyDO result) {
		return (result.getTotalProfit().compareTo(new BigDecimal("-0.8")) <= 0 && runType.equals("小本金"))
				||(smallStrategyResult.compareTo(BigDecimal.ZERO) != 0 && maxWinWithSmall.divide(smallStrategyResult, 2, RoundingMode.FLOOR).compareTo(new BigDecimal("2")) > 0&& runType.equals("小本金"));
	}
	//是否切换为大本金
	@Override
	protected boolean isTurntoSmallAmt(StrategyDO result) {
		return ((result.getTotalProfit().compareTo(new BigDecimal("10")) >= 0
				|| result.getTotalProfit().compareTo(new BigDecimal("-100")) <= 0) && runType.equals("大本金"))
				|| (bigStrategyResult.compareTo(BigDecimal.ZERO) != 0 &&maxWinWithBig.divide(bigStrategyResult, 2, RoundingMode.FLOOR).compareTo(new BigDecimal("2")) > 0&& runType.equals("大本金"));
	}

	@Override
	public void runStrategy(String startNo, String endNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean is2Win() {
		// TODO Auto-generated method stub
		return false;
	}

}
