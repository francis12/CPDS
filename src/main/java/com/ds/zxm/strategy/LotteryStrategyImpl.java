package com.ds.zxm.strategy;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ds.zxm.mapper.LotteryDetailMapper;
import org.apache.log4j.Logger;

import com.ds.zxm.model.LotteryDetail;
import com.ds.zxm.model.LotteryDetailCondition;
import com.ds.zxm.model.StrategyDO;
import com.ds.zxm.model.TradeSchedule;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;

public abstract class LotteryStrategyImpl implements ILotteryStrategy {

	@Resource
	private LotteryDetailMapper lotteryDetailDAO;
	//一天期数
	public static final int oneDayNos = 1440;
	/*	// 投注模式
	public String lotteryMode = "后一";*/
	// 投注号码
	protected List<LotteryDetail> noList = new ArrayList<LotteryDetail>();
	protected Map<String,TradeSchedule> scheMap = new HashMap<String,TradeSchedule>();
	
	protected Logger Log = Logger.getLogger(LotteryStrategyImpl.class);

	//基础本金
	private BigDecimal baseAmt = new BigDecimal("0.2");	
	protected String runType = "大本金";
	//赔率
	protected BigDecimal rate = new BigDecimal("1");
	protected BigDecimal maxWinWithBig = BigDecimal.ZERO;
	protected BigDecimal maxLoseWithBig = BigDecimal.ZERO;
	protected BigDecimal maxWinWithSmall = BigDecimal.ZERO;
	protected BigDecimal maxLoseWithSmall = BigDecimal.ZERO;
	protected List<LotteryDetail> winLotteryDetailList = new ArrayList<LotteryDetail>();

	private BigDecimal strategyResult= BigDecimal.ZERO;	
	protected BigDecimal smallStrategyResult= BigDecimal.ZERO;	
	protected BigDecimal bigStrategyResult= BigDecimal.ZERO;
	//流水
	protected BigDecimal salarySeq = BigDecimal.ZERO;
	
	//单次计划临界值
	protected BigDecimal maxWinCurMode = BigDecimal.ZERO;
	protected BigDecimal maxLoseCurMode = BigDecimal.ZERO;

	//初始化倍投计划和投注号码
	protected abstract void initScheAndNo();
	@Override
	public void runStrategy(String startNo, String endNo, List<String> orignNo) {
		for (String no : orignNo) {
			this.noList = new ArrayList<>();
			this.noList.addAll(LotteryUtil.convertStr2DetailList(no));
			//originSendNo= no;
			this.runStrategy(startNo, endNo);
		}
		
	}
	@Override
	public void runStrategy(String startNo, String endNo, String originSendNo) {
		
		
		noList.addAll(LotteryUtil.convertStr2DetailList(originSendNo));
		
		Log.info("策略开始：" + startNo + " --->" + endNo + "投注号码：" + originSendNo );
		//List<LotteryDetail> tzList = new ArrayList<LotteryDetail>();

		LotteryDetailCondition LotteryDetailCondition = new LotteryDetailCondition();
		LotteryDetailCondition.createCriteria().andAliasNoGreaterThanOrEqualTo(startNo)
				.andAliasNoLessThanOrEqualTo(endNo);
		List<LotteryDetail> toRunList = lotteryDetailDAO.selectByCondition(LotteryDetailCondition);
		Collections.sort(toRunList);
		
	/*	if ("后一".equals(lotteryMode)) {
			for (LotteryDetail no : noList) {
				// 257数字转成do
				LotteryDetail detail = new LotteryDetail();
				detail.setNum3(no.substring(0, 1));
				detail.setNum2(no.substring(1, 2));
				detail.setNum1(no.substring(2, 2));
				detail.setNum1(no);
				tzList.add(detail);
			}
		}*/
		
		//将noList分组跑
		int limit = 1000;
		if (toRunList.size() > limit) {
			int part = toRunList.size()/limit;
			StrategyDO prev = null;
			for (int i = 0; i< part; i++) {
				if(i != part -1) {
					List<LotteryDetail> limitList = toRunList.subList(0, limit);
					List<LotteryDetail> tmp = new ArrayList<LotteryDetail>();
					tmp.addAll(limitList);
					prev = runSchedule(noList, tmp, "1", prev);
					toRunList.removeAll(limitList);
				}else {
					StrategyDO result = runSchedule(noList, toRunList, "1", prev);
				}
			}
		}
		//StrategyDO result = runSchedule(noList, toRunList, "1", null);
		printWinNoDetail();
		
		Log.info("策略结束：" + startNo + " --->" + endNo + "大本金最大盈利：" + maxWinWithBig + ",大本金最大亏损：" + maxLoseWithBig);
		Log.info("策略结束：" + startNo + " --->" + endNo + "小本金最大盈利：" + maxWinWithSmall + ",小本金最大亏损：" + maxLoseWithSmall);
		Log.info(startNo + " --->" + endNo + "投注号码" +originSendNo + ",最终盈利：" + strategyResult + ",最终流水:" + salarySeq );
		

	}
	protected StringBuffer colorResultList = new StringBuffer();
	//计算与上一次中奖间隔
	private void printWinNoDetail() {
		Collections.sort(winLotteryDetailList);
		for (int i= 1;i<winLotteryDetailList.size();i++) {
			int noDistance = calWinNodistance(winLotteryDetailList.get(i), winLotteryDetailList.get(i-1));
			String colorResult = noDistance<=10?"(白)":"(红)";
			colorResultList.append(colorResult);
			Log.info(winLotteryDetailList.get(i).getNum2() + winLotteryDetailList.get(i).getNum1() + "---"
					+ winLotteryDetailList.get(i).getAliasNo() + " ---遗漏： " + noDistance + colorResult);
		}
	}

	private int calWinNodistance(LotteryDetail LotteryDetail, LotteryDetail LotteryDetail2) {
		try {
			int dayBetween = DateUtils.calculateDaysBetween(LotteryDetail.getAliasNo().substring(0, 8),
					LotteryDetail2.getAliasNo().substring(0, 8));
			
			int curDayBetween = Integer.valueOf(LotteryDetail.getAliasNo().substring(9)) - Integer.valueOf(LotteryDetail2.getAliasNo().substring(9))-1 ;
			
			return oneDayNos * dayBetween + curDayBetween;
			
		} catch (ParseException e) {
			e.printStackTrace();		
			return 0;
		}
	}
	
	private StrategyDO runSchedule(List<LotteryDetail> tzNos, List<LotteryDetail> toRunList,
				 				String sheduleNO, StrategyDO prev) {

		StrategyDO result = new StrategyDO();
		TradeSchedule tradeSchedule = scheMap.get(sheduleNO);
		//跑完所有回测数据
		if (toRunList == null || toRunList.size() == 0) {
			//总盈亏加上最后一次跑批数据
			strategyResult = strategyResult.add(prev.getTotalProfit());
			return result;
		}
		//是否投注
		boolean isToWin = this.is2Win();
		
		//中挂标识 -- 默认只有一个号码中
		//下一期开奖数据
		LotteryDetail kjDetailDO = toRunList.get(0);
		boolean winFlag = false;

		if (isToWin) {
			for (LotteryDetail tzNo : tzNos) {
				result.setCurrentNo(kjDetailDO.getAliasNo());
				winFlag = checkIsWin(kjDetailDO, tzNo);
				if (winFlag){
					break;
				}
			}
			//投注额=基础本金*计划倍数
			result.setCurrentAmount(new BigDecimal(tradeSchedule.getMultiple()).multiply(baseAmt).multiply(new BigDecimal(tzNos.size())));
			result.setTotalAmount((null == prev? BigDecimal.ZERO : prev.getTotalAmount()).add(result.getCurrentAmount()));
			salarySeq = salarySeq.add(result.getCurrentAmount());
			
			if (winFlag) {
				result.setCurrentProfit(new BigDecimal(tradeSchedule.getMultiple()).multiply(baseAmt).multiply(rate).subtract(result.getCurrentAmount()));
				result.setTotalProfit((null == prev? BigDecimal.ZERO : prev.getTotalProfit()).add(result.getCurrentProfit()));
			} else {
				result.setCurrentProfit(new BigDecimal("-1").multiply(result.getCurrentAmount()));
				result.setTotalProfit((null == prev? BigDecimal.ZERO : prev.getTotalProfit()).add(result.getCurrentProfit()));
			}
			
			if (result.getTotalProfit().compareTo(result.getMaxWinAmount()) > 0) {
				result.setMaxWinAmount(result.getTotalProfit());
			}
			if (result.getTotalProfit().compareTo(result.getMaxLoseAmount()) < 0) {
				result.setMaxLoseAmount(result.getTotalProfit());
			}
		}
		toRunList.remove(kjDetailDO);

		Log.info("开奖号码:" + kjDetailDO.getNum2() + kjDetailDO.getNum1() + "----" + result.toManageString());
		
		//统计数据
		if ("大本金".equals(runType)) {
			bigStrategyResult = bigStrategyResult.add(result.getCurrentProfit());
			if (result.getTotalProfit().compareTo(maxWinWithBig) > 0) {
				maxWinWithBig = result.getTotalProfit();
			} 
			if (result.getTotalProfit().compareTo(maxLoseWithBig) < 0) {
				maxLoseWithBig = result.getTotalProfit();
			}
			
		} else {
			smallStrategyResult = smallStrategyResult.add(result.getCurrentProfit());
			if (result.getTotalProfit().compareTo(maxWinWithSmall) > 0) {
				maxWinWithSmall = result.getTotalProfit();
			}
			if (result.getTotalProfit().compareTo(maxLoseWithSmall) < 0) {
				maxLoseWithSmall = result.getTotalProfit();
			}
		}
		
		//大本金与小本金切换
		//达到临界
		if (runType.equals("大本金")&&isTurntoSmallAmt(result)) {
			// 切换为小本金模式
			runType = "小本金";
			baseAmt = baseAmt.multiply(new BigDecimal("0.01")).setScale(4);
			//bigStrategyResult = bigStrategyResult.add(result.getTotalProfit());
			strategyResult = strategyResult.add(result.getTotalProfit());
			// System.out.println("切换为小本金模式" + ",切换前总盈利：" + strategyResult);
			Log.info("切换为小本金模式" + ",切换前总盈利：" + strategyResult + " maxWinWithBig:" + maxWinWithBig + ",maxLoseWithBig:"+ maxLoseWithBig + ",bigStrategyResult:" + bigStrategyResult);
			runSchedule(tzNos, toRunList, "1", null);
		} else if (runType.equals("小本金")&&isTurn2Big(result)) {
			// 切换为大本金模式
			runType = "大本金";

			baseAmt = baseAmt.multiply(new BigDecimal("100"));
			//smallStrategyResult = smallStrategyResult.add(result.getTotalProfit());
			strategyResult = strategyResult.add(result.getTotalProfit());
			Log.info("切换为大本金模式" + ",切换前总盈利：" + strategyResult + "maxWinWithSmall:" + maxWinWithSmall +",maxLoseWithSmall:" + maxLoseWithSmall + ",smallStrategyResult:" + smallStrategyResult);
			runSchedule(tzNos, toRunList, "1", null);
		} else {
			runSchedule(tzNos, toRunList,
					String.valueOf(winFlag ? tradeSchedule.getWinNo() : tradeSchedule.getLoseNo()), result);
		}
		return result;
	}
	//是否切换为大本金
	protected abstract boolean isTurn2Big(StrategyDO result);
	//是否切换为大本金
	protected abstract boolean isTurntoSmallAmt(StrategyDO result);
	protected abstract boolean checkIsWin(LotteryDetail kjDetailDO, LotteryDetail tzNo);
	protected abstract boolean is2Win();
	
}
