package com.ds.zxm.strategy;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestStrategy extends BaseStrategy{

    /**
     * 测试
     *
     * @return
     */


    @Override
    public boolean isWin(Object calNo, TCFFCPRIZE realNo) {
        String dan = (String) calNo;
        boolean isPrized =  false;
        /*if (dan.equals(""+realNo.getQian())
                ||dan.equals(""+realNo.getBai())
                    || dan.equals(""+realNo.getShi())
                        || dan.equals(""+realNo.getGe())) {
            isPrized = true;
        }*/
        String adustNum = ("" + realNo.getAdjustNum());
        if(adustNum.length() >= 4) {
            adustNum = adustNum.substring(adustNum.length()-4, adustNum.length());
        }
        if(String.valueOf(realNo.getAdjustNum()).indexOf("0") >= 0) {
            isPrized = true;
        }
        return isPrized;
    }

    @Override
    public Object calBetNum(Date time) {
        String result = "0";
        try {
            TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
            Date preTime = DateUtils.addMinutes(-1,time);
            String no = TcffcPrizeConverter.genNofromTime(preTime);
            condition.createCriteria().andNoEqualTo(no);
            List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);

            if (prizeList != null &  prizeList.size() > 0) {
                TCFFCPRIZE prePrize = prizeList.get(0);
                int ws = (prePrize.getQian() + prePrize.getBai() + prePrize.getShi() + prePrize.getGe()) % 10;
                result = String.valueOf(ws);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
