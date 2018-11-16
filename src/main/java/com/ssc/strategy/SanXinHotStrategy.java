package com.ssc.strategy;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.model.TcffcPrizeConverter;
import com.ssc.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SanXinHotStrategy extends BaseStrategy{

    /**
     * 三星直选遗漏选取0-2000的遗漏保留，大概是800多注
     * 挂了一期，停，中了再投，平刷
     * @return
     */


    @Override
    public boolean isWin(Object calNo, TCFFCPRIZE realNo) {
        Set<String> lastHotResult = (Set<String>) calNo;
        String realHs =  "" + realNo.getBai() + realNo.getShi() + realNo.getGe();
        boolean isPrized =  lastHotResult.contains(realHs);
        //System.out.println(realNo.getNo() + " --- " + (isPrized?"中":"挂"));
        return isPrized;
    }

    @Override
    public Object calBetNum(Date time) {
        Set<String> lastHotResult = new HashSet<>();

        try {
            //1.计算理论预测数据 2.数据库读取实际开奖数据,3比较是否
            String no = TcffcPrizeConverter.genNofromTime(time);
            TCFFCPRIZECondition condition = new TCFFCPRIZECondition();

            Date startDate = DateUtils.addMinutes(-500,time);
            Date endDate = DateUtils.addMinutes(-1,time);
            condition.createCriteria().andTimeBetween(startDate, endDate);
            List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
            Collections.sort(prizeList);
            prizeList.stream().forEach(item -> {
                String houSan =  "" + item.getBai() + item.getShi() + item.getGe();
                lastHotResult.add(houSan);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //取反集
        Set<String> result = new HashSet<>();
        result.addAll(allSanXinNums);
        lastHotResult.stream().forEach( item -> {
            result.remove(item);
        });
        //System.out.print(lastHotResult.size() + "注");
        return lastHotResult;
    }
}
