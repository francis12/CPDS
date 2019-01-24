package com.ssc.service;

import com.ssc.mapper.TCFFCPRIZEDAO;
import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.model.TcffcPrizeConverter;
import com.ssc.prizeschedule.BaseGenPrize;
import com.ssc.prizeschedule.GeBdDwdGenPrize;
import com.ssc.strategy.BaseStrategy;
import com.ssc.strategy.StrategyServiceImpl;
import com.ssc.util.DateUtils;
import com.ssc.util.LotteryUtil;
import com.ssc.vo.BetingDetail;
import com.ssc.vo.TradeSchedule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class PrizeStrategyService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private TcffcGenNumsService tcffcGenNumsService;
    //@Resource(name="sanXinHotStrategy")
    @Resource(name="geBdDwdGenPrize")
    private BaseGenPrize baseGenPrize;
    @Autowired
    private StrategyServiceImpl strategyServiceImpl;
    Logger log = Logger.getLogger(PrizeStrategyService.class);

    public BetingDetail check(String startNo, String endNo) {
        BetingDetail betingDetail = null;
        TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
        condition.createCriteria().andNoBetween(startNo, endNo);
        condition.setOrderByClause("no asc");
        List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
        baseGenPrize.setTestDataMode(true);
        betingDetail = strategyServiceImpl.calBetNum(baseGenPrize,prizeList);
        return betingDetail;
    }
}
