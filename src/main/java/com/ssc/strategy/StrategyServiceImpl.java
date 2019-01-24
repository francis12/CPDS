package com.ssc.strategy;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.model.TcffcPrizeConverter;
import com.ssc.prizeschedule.BaseGenPrize;
import com.ssc.service.LotteryStrategyService;
import com.ssc.util.DateUtils;
import com.ssc.vo.BetingDetail;
import com.ssc.vo.StrategyContext;
import com.ssc.vo.TradeSchedule;
import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StrategyServiceImpl {

    Logger log = Logger.getLogger(StrategyServiceImpl.class);

    public BetingDetail calBetNum(BaseGenPrize genPrize,List<TCFFCPRIZE> list) {
        if(null == list || list.size() == 0) {
            return null;
        }
        BetingDetail betingDetail = new BetingDetail();
        Map<String ,Integer> loseCntMap = new HashMap<>();
        Map<String ,Integer> winCntMap = new HashMap<>();

        StrategyContext context = new StrategyContext();
        Map<String, TradeSchedule> scheduleMap =  context.getScheduleMap();
        TradeSchedule curSchedule =scheduleMap.get("1");
        BigDecimal baseAmt = context.getBaseAmt();
        BigDecimal curAmt = context.getCurAmt();
        BigDecimal baseRate = context.getBaseRate();
        int lzCnt = 0;
        int lgCnt = 0;
        int totalCheckCnt = list.size();
        int winCheckCnt = 0;
        StringBuffer zgXt = new StringBuffer();
        for(TCFFCPRIZE curPrize : list) {
            Map<String, Object> result = genPrize.run(curPrize);
            boolean isPrized = (Boolean) result.get("isPrized");
            zgXt.append(isPrized?"1":"0");
            if(isPrized) {
                winCheckCnt++;
                lzCnt++;
                if(lgCnt > 0) {
                    //更新连挂连中
                    if(loseCntMap.get(""+ lgCnt) == null) {
                        loseCntMap.put(""+lgCnt, 1);
                    } else {
                        loseCntMap.put(""+lgCnt, loseCntMap.get(""+ lgCnt)+1);
                    }
                    lgCnt = 0;
                }
            } else {
                lgCnt++;
                if(lzCnt > 0) {
                    //更新连挂连中
                    if(winCntMap.get(""+ lzCnt) == null) {
                        winCntMap.put(""+lzCnt, 1);
                    } else {
                        winCntMap.put(""+lzCnt, winCntMap.get(""+ lzCnt)+1);
                    }
                    lzCnt = 0;
                }
            }
            curAmt = baseAmt.multiply(BigDecimal.valueOf(curSchedule.getMultiple()));
            betingDetail.setBetSum(betingDetail.getBetSum().add(curAmt));
            BigDecimal profit = curAmt.multiply(isPrized?baseRate:new BigDecimal("-1"));
            betingDetail.setCurProfit(betingDetail.getCurProfit().add(profit));

            //设置最大盈利最大亏损
            if(betingDetail.getMaxProfit().compareTo(betingDetail.getCurProfit()) < 0) {
                betingDetail.setMaxProfit(betingDetail.getCurProfit());
            }
            if(betingDetail.getMinProfit().compareTo(betingDetail.getCurProfit()) > 0) {
                betingDetail.setMinProfit(betingDetail.getCurProfit());
            }

            curSchedule = scheduleMap.get(isPrized?("" + curSchedule.getWinNo()) : ("" + curSchedule.getLoseNo()));

        }
        log.info("zgxt:" + zgXt.toString());
        log.info("回测结果:总验证期数：" + totalCheckCnt + ",预测准确率：" + BigDecimal.valueOf(winCheckCnt).divide(BigDecimal.valueOf(totalCheckCnt), 4, RoundingMode.FLOOR)
                + "总投注:" + betingDetail.getBetSum() + "最终盈利：" + betingDetail.getCurProfit() + "最大亏损：" + betingDetail.getMinProfit() + "最大盈利:" + betingDetail.getMaxProfit());

        return betingDetail;
    }
}
