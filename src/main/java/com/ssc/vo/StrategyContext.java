package com.ssc.vo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

//批处理策略上下文
public class StrategyContext {

    private Map<String, TradeSchedule> scheduleMap;
    private BigDecimal baseAmt;
    //去除本金后的利润率
    private BigDecimal baseRate;
    private BigDecimal curAmt;
    public StrategyContext() {
        TradeSchedule tradeSchedule1 = new TradeSchedule(1, 1,2,1);
        TradeSchedule tradeSchedule2 = new TradeSchedule(2, 1,3,2);
        TradeSchedule tradeSchedule3 = new TradeSchedule(3, 1,4,4);
        TradeSchedule tradeSchedule4 = new TradeSchedule(4, 1,5,8);
        TradeSchedule tradeSchedule5 = new TradeSchedule(5, 1,6,16);
        TradeSchedule tradeSchedule6 = new TradeSchedule(6, 1,1,32);

        scheduleMap = new HashMap<>();
        scheduleMap.put("1", tradeSchedule1);
        scheduleMap.put("2", tradeSchedule2);
        scheduleMap.put("3", tradeSchedule3);
        scheduleMap.put("4", tradeSchedule4);
        scheduleMap.put("5", tradeSchedule5);
        scheduleMap.put("6", tradeSchedule6);
        this.setScheduleMap(scheduleMap);

        setBaseAmt(new BigDecimal("1"));
        setBaseRate(new BigDecimal("0.978"));
        setCurAmt(BigDecimal.ZERO);
    }
    public Map<String, TradeSchedule> getScheduleMap() {
        return scheduleMap;
    }

    public void setScheduleMap(Map<String, TradeSchedule> scheduleMap) {
        this.scheduleMap = scheduleMap;
    }

    public BigDecimal getBaseAmt() {
        return baseAmt;
    }

    public void setBaseAmt(BigDecimal baseAmt) {
        this.baseAmt = baseAmt;
    }

    public BigDecimal getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(BigDecimal baseRate) {
        this.baseRate = baseRate;
    }

    public BigDecimal getCurAmt() {
        return curAmt;
    }

    public void setCurAmt(BigDecimal curAmt) {
        this.curAmt = curAmt;
    }

}
