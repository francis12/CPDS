package com.ssc.vo;

import java.math.BigDecimal;
import java.util.Formatter;

//倍投
public class BetingDetail {

    //总投注额
    private BigDecimal betSum = BigDecimal.ZERO;
    //当前盈亏
    private BigDecimal curProfit = BigDecimal.ZERO;
    private BigDecimal maxProfit= BigDecimal.ZERO;
    private BigDecimal minProfit= BigDecimal.ZERO;
    //最大连挂
    private int maxLoose;
    private int maxWin;
    private int curLoose;

    public BigDecimal getBetSum() {
        return betSum;
    }

    public void setBetSum(BigDecimal betSum) {
        this.betSum = betSum;
    }

    public BigDecimal getCurProfit() {
        return curProfit;
    }

    public void setCurProfit(BigDecimal curProfit) {
        this.curProfit = curProfit;
    }

    public BigDecimal getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(BigDecimal maxProfit) {
        this.maxProfit = maxProfit;
    }

    public BigDecimal getMinProfit() {
        return minProfit;
    }

    public void setMinProfit(BigDecimal minProfit) {
        this.minProfit = minProfit;
    }

    public int getMaxLoose() {
        return maxLoose;
    }

    public void setMaxLoose(int maxLoose) {
        this.maxLoose = maxLoose;
    }

    public int getMaxWin() {
        return maxWin;
    }

    public void setMaxWin(int maxWin) {
        this.maxWin = maxWin;
    }

    public int getCurLoose() {
        return curLoose;
    }

    public void setCurLoose(int curLoose) {
        this.curLoose = curLoose;
    }
}
