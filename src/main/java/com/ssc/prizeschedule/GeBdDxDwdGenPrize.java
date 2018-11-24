package com.ssc.prizeschedule;

import com.ssc.Application;
import com.ssc.constants.BaseConstants;
import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Scope("prototype")
@Service
public class GeBdDxDwdGenPrize extends BaseGenPrize {
    @Override
    void init() {
        this.wfType = BaseConstants.WF_TYPE_DWD_GE_DX;
        this.isSyncGenData =true;
    }
    @Override
    String getGenPrizeNumsStr( TCFFCPRIZE curPrize) {

        String result = "";
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
            int adjust = item.getAdjustNum()%10;
            if((0<=adjust&&adjust<=4)||(-9<=adjust&&adjust<=-6)) {
                bdDsList.add("0");
            } else {
                bdDsList.add("1");
            }
        }

        //判断下一期波动的大小
        String nextBdDs = this.judgeNextBdDs(bdDsList);
        Integer curDx = curPrize.getGe();


        if("0".equals(nextBdDs)) {
            for(int i=0;i<5;i++) {
                int tmp = curDx+i;
                if (tmp >= 10) {
                    tmp = tmp - 10;
                }
                result =  result + "" + tmp + ",";
            }
        } else if("1".equals(nextBdDs)) {
            for(int i=5;i<10;i++) {
                int tmp = curDx+i;
                if (tmp >= 10) {
                    tmp = tmp - 10;
                }
                result =  result + "" + tmp + ",";
            }
        } else {
            result = nextBdDs;
        }
        Application.cache.put(cacheKey, result);
        return result;
    }

    private String judgeNextBdDs(List<String> bdList){
        if(bdList == null || bdList.size() == 0) {
            return "";
        }
        String bdListStr = "";
        for(String item: bdList) {
            bdListStr = bdListStr + item;
        }

        //大小跳方案
        if (bdListStr.endsWith("101") && !bdListStr.endsWith("111101")) {
            return "0";
        } else if (bdListStr.endsWith("010") && !bdListStr.endsWith("000010")) {
            return "1";
        } else {
            //开啥跟啥
            if(bdListStr.endsWith("0")) {
                return "0";
            } else {
                return "1";
            }
        }
    }
    private String judgeNextBdDs2(List<String> bdList){
        if(bdList == null || bdList.size() == 0) {
            return "";
        }
        String bdListStr = "";
        for(String item: bdList) {
            bdListStr = bdListStr + item;
        }
        if (bdListStr.endsWith("0001")) {
            return "waiting";
        }else if (bdListStr.endsWith("1110")) {
            return "waiting";
        }else if (bdListStr.indexOf("000") < 0 && bdListStr.indexOf("111") < 0) {
            return "waiting";
        }else if (bdListStr.endsWith("1101")) {
            return "waiting";
        }else if (bdListStr.endsWith("0010")) {
            return "waiting";
        }else if (bdListStr.endsWith("00100")) {
            return "0";
        } else if (bdListStr.endsWith("11011")) {
            return "1";
        } else if (bdListStr.endsWith("01100")) {
            return "1";
        } else if (bdListStr.endsWith("10011")) {
            return "0";
        } else if (bdListStr.endsWith("1000111")) {
            return "0";
        }else if (bdListStr.endsWith("0111000")) {
            return "1";
        }else if (bdListStr.endsWith("000")) {
            return "0";
        } else if (bdListStr.endsWith("111")) {
            return "1";
        }  else if (bdListStr.endsWith("0101")) {
            return "0";
        } else if (bdListStr.endsWith("1010")) {
            return "1";
        } else   {
            //剩余情况取最近5期出现最多的
            List<String> subBdList = null;
            if(bdList.size() >=5) {
                subBdList = bdList.subList(bdList.size()-5,bdList.size());
            } else {
                subBdList = bdList;
            }
            int shuangSum = 0;
            int danSum = 0;
            for(String item : subBdList) {
                if("0".equals(item)) {
                    shuangSum++;
                } else {
                    danSum++;
                }
            }
            if(shuangSum >= danSum) {
                return "0";
            } else {
                return "1";
            }
        }
    }

    @Override
    boolean isPrized( TCFFCPRIZE curPrize) {
        boolean result = false;
        String prize = (String)Application.cache.get(cacheKey);

        if(StringUtils.isNotEmpty(prize) && prize.indexOf("" + curPrize.getGe()) >= 0) {
            result = true;
        }
        return result;
    }
}
