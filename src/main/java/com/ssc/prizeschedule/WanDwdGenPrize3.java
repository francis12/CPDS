package com.ssc.prizeschedule;

import com.ssc.Application;
import com.ssc.constants.BaseConstants;
import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WanDwdGenPrize3 extends BaseGenPrize {
    //万位定位胆
    @Override
    void init() {
        this.wfType = BaseConstants.WF_TYPE_DWD_WAN4_DS;
        this.isWrite2File = false;
        isSyncGenData = true;
    }

    @Override
    String getGenPrizeNumsStr( TCFFCPRIZE curPrize) {

        String genPrizeStr = null;
        StringBuffer sb = new StringBuffer();
        //取最近10期开奖数据
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Date latestTime = DateUtils.addMinutes(-10, curPrize.getTime());
        tcffcprizeCondition.createCriteria().andTimeGreaterThanOrEqualTo(latestTime);
        tcffcprizeCondition.setOrderByClause("time asc");
        List<TCFFCPRIZE> tcffcprizeList = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        //转波动的大小
        List<String> bdDsList = new ArrayList<>();
        for(TCFFCPRIZE item: tcffcprizeList) {
            int adjust = item.getWan();
            if(adjust % 2 ==  1) {
                bdDsList.add("0");
            } else {
                bdDsList.add("1");
            }
        }
        //判断下一期波动的大小
        String nextBdDs = this.judgeNextBdDs(bdDsList);
        Integer curDx = curPrize.getGe();

        if("0".equals(nextBdDs)) {
            genPrizeStr = "1,3,5,7,9";
        } else if("1".equals(nextBdDs)) {
            genPrizeStr = "0,2,4,6,8";
        } else {
            genPrizeStr = nextBdDs;
        }
        log.info("万位当前单双为：" + bdDsList.toString() + ",预测下期万位:" + genPrizeStr);
        Application.cache.put(cacheKey, genPrizeStr);

        return genPrizeStr;
    }

    /**
     * 策略
     * @param bdList
     * @return
     */
    private String judgeNextBdDs(List<String> bdList){
        if(bdList == null || bdList.size() == 0) {
            return "";
        }
        String bdListStr = "";
        for(String item: bdList) {
            bdListStr = bdListStr + item;
        }

        //大小跳方案
        if (bdListStr.endsWith("1010")) {
            return "1";
        }else if (bdListStr.endsWith("0101")) {
            return "0";
        }  else {
            //开啥跟啥
            if(bdListStr.endsWith("0")) {
                return "0";
            } else {
                return "1";
            }
        }
    }
    @Override
    boolean isPrized( TCFFCPRIZE curPrize) {
        boolean result = false;
        String prize = (String) Application.cache.get(cacheKey);
        if (StringUtils.isNotEmpty(prize) && prize.indexOf("" + curPrize.getWan()) >= 0) {
            result = true;
        }
        return result;
    }
}
