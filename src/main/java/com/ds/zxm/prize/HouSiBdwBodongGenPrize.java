package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class HouSiBdwBodongGenPrize extends BaseGenPrize {
    @Override
    void init() {
        file = new File("hou4BdwFile.txt");
        allFile = new File("hou4BdwAllFile.txt");
    }
    public static final String shuan = "0,2,4,6,8";

    public static final String dan = "1,3,5,7,9";
    public static final int hotCnt = 8;
    public static final int coldCnt = 120;

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {

        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Date endTime = curPrize.getTime();
        Date startTime = DateUtils.addMinutes(-180, endTime);
        tcffcprizeCondition.createCriteria().andTimeBetween(startTime, endTime);
        List<TCFFCPRIZE> tcffcprizeList = tcffcprizedao.selectByCondition(tcffcprizeCondition);
        if(tcffcprizeList==null || tcffcprizeList.size()<=120) {
            return "";
        }
        Collections.sort(tcffcprizeList, new Comparator<TCFFCPRIZE>() {
            @Override
            public int compare(TCFFCPRIZE o1, TCFFCPRIZE o2) {
                return o2.getNo().compareTo(o1.getNo());
            }
        });
        Map<String, Integer> missedCntMap = new HashMap<>();
        for (int i=0;i<10;i++) {
            int missedCnt = 0;
            for(TCFFCPRIZE tcffcprize : tcffcprizeList) {
                if(i==tcffcprize.getGe()
                        || i==tcffcprize.getShi()
                        || i==tcffcprize.getBai()
                        || i==tcffcprize.getQian()) {
                    break;
                } else {
                    missedCnt++;
                }
            }
            missedCntMap.put(""+i, missedCnt);
        }
        List<TCFFCPRIZE> hotList = tcffcprizeList.subList(0,hotCnt);
        List<TCFFCPRIZE> coldList = tcffcprizeList.subList(0,coldCnt);
        Map<String, Double> finalResultMap = new HashMap<>();
        //最近hot期开出*40% + 最近cold期未开出*40% + 当前遗漏 *20%
        for (int i=0;i<10;i++) {
            String curNum = String.valueOf(i);
            int hotCnt = 0;
            int coldCnt = 0;
            int missedCnt = 0;
            for(TCFFCPRIZE tcffcprize : hotList) {
                if(i == tcffcprize.getGe())
                        {
                    hotCnt++;
                }
                if(i == tcffcprize.getShi())
                {
                    hotCnt++;
                }
                if(i == tcffcprize.getBai())
                {
                    hotCnt++;
                }
                if(i == tcffcprize.getQian())
                {
                    hotCnt++;
                }
            }
            for(TCFFCPRIZE tcffcprize : coldList) {

                if(i==tcffcprize.getGe()
                        || i==tcffcprize.getShi()
                        || i==tcffcprize.getBai()
                        || i==tcffcprize.getQian()) {

                }
                if(i == tcffcprize.getGe())
                {
                    coldCnt++;
                }
                if(i == tcffcprize.getShi())
                {
                    coldCnt++;
                }
                if(i == tcffcprize.getBai())
                {
                    coldCnt++;
                }
                if(i == tcffcprize.getQian())
                {
                    coldCnt++;
                }
            }
            double ylValue = (1-missedCntMap.get("" + i)/6)*0.1;
            double finalValue = (hotCnt/(0.4*hotList.size())) *0.55 + ((0.4*coldList.size())/coldCnt)*0.35 + ylValue;
            log.info(curPrize.getNo() + "(" +i + ")hotCnt:" + hotCnt + ",coldCnt" +  coldCnt + ",ylValue:" + ylValue +  " finalValue: " + finalValue);
            finalResultMap.put(""+i, finalValue);
        }
        List<String> list =  new ArrayList<>(LotteryUtil.sortMapByDoubleValue(finalResultMap, "-1").keySet());
        this.genStr = list.get(1);
        return genStr;
    }
    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (StringUtils.isNotEmpty(genStr)) {
            if(genStr.equals("" + curPrize.getGe())
                    ||genStr.equals("" + curPrize.getShi())
                    || genStr.equals("" + curPrize.getBai())
                    || genStr.equals("" + curPrize.getQian())
                    ) {
                result = true;
            }
        }
        return result;
    }
}
