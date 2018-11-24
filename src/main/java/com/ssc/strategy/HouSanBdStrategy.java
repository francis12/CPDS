package com.ssc.strategy;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.prizeschedule.HousanBdGenPrize;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HouSanBdStrategy extends BaseStrategy{


    @Resource
    protected HousanBdGenPrize housanbdGenPrize;
    @Override
    public boolean isWin(Object calNo, TCFFCPRIZE realNo) {

       List<TCFFCPRIZE> genPrizeList = (List<TCFFCPRIZE>) calNo;
        boolean result = false;
        String hou3Prize = realNo.getPrize().substring(2, 5);
        if (null != genPrizeList && genPrizeList.size() > 0) {
            for (TCFFCPRIZE tcffcprize : genPrizeList) {
                String prize = tcffcprize.getPrize();
                if (StringUtils.isNotEmpty(prize) && prize.equals(hou3Prize)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Object calBetNum(Date time) {
        TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
        tcffcprize.setTime(time);
        housanbdGenPrize.getGenPrizeNumsStr( tcffcprize);
        List<TCFFCPRIZE> result = new ArrayList<>();
        return result;
    }
}
