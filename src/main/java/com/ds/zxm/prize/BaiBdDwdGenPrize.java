package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.util.DateUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BaiBdDwdGenPrize extends BaseGenPrize {
    @Override
    void init() {
        file = new File("baiBdFile.txt");
        allFile = new File("baiBdAllFile.txt");
    }
    public static final String shuan = "0,2,4,6,8";

    public static final String dan = "1,3,5,7,9";
    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {

        StringBuffer sb = new StringBuffer();
        //取最近10期开奖数据
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Date latestTime = DateUtils.addMinutes(-10, conPrize.getTime());
        tcffcprizeCondition.createCriteria().andTimeGreaterThanOrEqualTo(latestTime);
        tcffcprizeCondition.setOrderByClause("time asc");
        List<TCFFCPRIZE> tcffcprizeList = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        //转波动的单双
        List<String> bdDsList = new ArrayList<>();
        for(TCFFCPRIZE item: tcffcprizeList) {
            String bdStr = "" + Math.abs((item.getAdjustNum()%100/10)%2);
            bdDsList.add(bdStr);
        }

        //判断下一期波动的单双
        String nextBdDs = this.judgeNextBdDs(bdDsList);
        log.info("百位当前波动单双为：" + bdDsList.toString() + ",预测下期波动：" + nextBdDs);
        Integer curDs = Math.abs(curPrize.getBai()%2);
        if("0".equals(nextBdDs)) {
            if(0 == curDs) {
                genStr = shuan;
            } else {
                genStr = dan;
            }
        } else {
            if(0 == curDs) {
                genStr = dan;
            } else {
                genStr = shuan;
            }
        }
        return genStr;
    }

    private String judgeNextBdDs(List<String> bdList){
        if(bdList == null || bdList.size() == 0) {
            return "";
        }
        String bdListStr = "";
        for(String item: bdList) {
            bdListStr = bdListStr + item;
        }
        if (bdListStr.endsWith("00100")) {
            return "0";
        } else if (bdListStr.endsWith("11011")) {
            return "1";
        } else if (bdListStr.endsWith("000")) {
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
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (null != genPrize) {
            if(genStr.indexOf("" + curPrize.getGe()) >= 0) {
                result = true;
            }
        }
        return result;
    }
}
