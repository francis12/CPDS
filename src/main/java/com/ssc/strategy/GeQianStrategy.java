package com.ssc.strategy;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.model.TcffcPrizeConverter;
import com.ssc.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GeQianStrategy extends BaseStrategy{


    @Autowired
    private com.ssc.prize.GeBdjcDwdGenPrize GeBdjcDwdGenPrize;
    @Override
    public synchronized boolean isWin(Object calNo, TCFFCPRIZE realNo) {
        String genStr = (String) calNo;
        boolean result = false;
            if(genStr.indexOf("" + realNo.getGe()) >= 0) {
                result = true;
            }
        return result;
    }

    @Override
    public synchronized Object calBetNum(Date time) {
        String result = "";
        try {
            //1.计算理论预测数据 2.数据库读取实际开奖数据,3比较是否
            Date preDate = DateUtils.addMinutes(-1,time);
            String no = TcffcPrizeConverter.genNofromTime(preDate);
            TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
            condition.createCriteria().andNoEqualTo(no);
            List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
            if (null != prizeList && prizeList.size() > 0) {
                TCFFCPRIZE cur = prizeList.get(0);
                result = GeBdjcDwdGenPrize.getGenPrizeNumsStr(null, cur);
            }
            log.info(no+"tou:"+result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
