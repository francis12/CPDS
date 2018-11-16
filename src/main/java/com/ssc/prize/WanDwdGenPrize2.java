package com.ssc.prize;

import com.ssc.constants.BaseConstants;
import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.util.DateUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WanDwdGenPrize2 extends BaseGenPrize {
    //万位定位胆
    @Override
    void init() {
        file = new File("wanFile.txt");
        allFile = new File("wanAllFile.txt");
        this.wfType = BaseConstants.WF_TYPE_DWD_WAN4_DX;
        this.isWrite2File = false;
        isSyncGenData = true;
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize, TCFFCPRIZE curPrize) {

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
            if((0<=adjust&&adjust<=4)||(-9<=adjust&&adjust<=-6)) {
                bdDsList.add("0");
            } else {
                bdDsList.add("1");
            }
        }
        //判断下一期波动的大小
        String nextBdDs = this.judgeNextBdDs(bdDsList);
        Integer curDx = curPrize.getGe();

        genStr = "";
        if("0".equals(nextBdDs)) {
            genStr = "0,1,2,3,4";
        } else if("1".equals(nextBdDs)) {
            genStr = "5,6,7,8,9";
        } else {
            genStr = nextBdDs;
        }
        log.info("万位当前大小为：" + bdDsList.toString() + ",预测下期" + conPrize.getNo() +  "万位:" + genStr);

        return genStr;
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
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (null != genPrize) {
            if(genStr.indexOf("" + curPrize.getWan()) >= 0) {
                result = true;
            }
        }
        return result;
    }
}
