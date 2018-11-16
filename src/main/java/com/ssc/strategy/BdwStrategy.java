package com.ssc.strategy;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.model.TcffcPrizeConverter;
import com.ssc.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BdwStrategy extends BaseStrategy{


    @Override
    public boolean isWin(Object calNo, TCFFCPRIZE realNo) {
        return true;
    }

    @Override
    public Object calBetNum(Date time) {
        TCFFCPRIZE genNoPrize = null;
        try {
            //1.计算理论预测数据 2.数据库读取实际开奖数据,3比较是否
            String no = TcffcPrizeConverter.genNofromTime(time);
            TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
            condition.createCriteria().andNoEqualTo(no);
            List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
            if (null == prizeList || prizeList.size() <= 0) {
                //log.info(no + "期未找到开奖数据,默认中奖.");
                return null;
            }
            //取上一期实际开奖数据
            Date last1Min = DateUtils.addMinutes(-1, time);
            String lastNo = TcffcPrizeConverter.genNofromTime(last1Min);
            TCFFCPRIZECondition condition2 = new TCFFCPRIZECondition();
            condition2.createCriteria().andNoEqualTo(lastNo);
            List<TCFFCPRIZE> lastPrizes = tcffcprizedao.selectByCondition(condition2);
            if (null != lastPrizes && lastPrizes.size() > 0) {
                genNoPrize = tcffcGenNumsService.calGenPrizeByRateNum(lastPrizes.get(0), 4);
                return genNoPrize;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
