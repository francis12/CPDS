package com.lottery.model;

import java.math.BigDecimal;

public class StrategyDO {

	//当前期
	private String currentNo;
	//当前投入金额
	private BigDecimal currentAmount;
	//总投入金额
	private BigDecimal totalAmount;
	//当前局盈亏
	private BigDecimal currentProfit;
	private BigDecimal maxWinAmount = BigDecimal.ZERO;
	private BigDecimal maxLoseAmount= BigDecimal.ZERO;
	public BigDecimal getMaxWinAmount() {
		return maxWinAmount;
	}

	public void setMaxWinAmount(BigDecimal maxWinAmount) {
		this.maxWinAmount = maxWinAmount;
	}

	public BigDecimal getMaxLoseAmount() {
		return maxLoseAmount;
	}

	public void setMaxLoseAmount(BigDecimal maxLoseAmount) {
		this.maxLoseAmount = maxLoseAmount;
	}

	//策略盈亏
	private BigDecimal totalProfit;
	
	public String getCurrentNo() {
		return currentNo;
	}

	public void setCurrentNo(String currentNo) {
		this.currentNo = currentNo;
	}

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getCurrentProfit() {
		return currentProfit;
	}

	public void setCurrentProfit(BigDecimal currentProfit) {
		this.currentProfit = currentProfit;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	public String toManageString() {
		return String.format("第%s期投入%s盈亏%s,总投注%s总盈亏%s", 
				currentNo, String.valueOf(currentAmount),
				String.valueOf(currentProfit.setScale(4)), String.valueOf(totalAmount.setScale(4)),
				String.valueOf(totalProfit.setScale(4)));
	}

}
