package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HouSiBdwBodongGenPrize extends BaseGenPrize {
    @Override
    void init() {
        file = new File("hou4BdwFile.txt");
        allFile = new File("hou4BdwAllFile.txt");
    }
    public static final String shuan = "0,2,4,6,8";

    public static final String dan = "1,3,5,7,9";
    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {

        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotEmpty(genStr)) {
            boolean isPrized = this.isPrized(null, curPrize);
            if(isPrized) {
                genStr = String.valueOf(Math.abs(curPrize.getAdjustNum()%10));
                curCount = 1;
            }else {
                if(curCount == 3) {
                    genStr = String.valueOf(Math.abs(curPrize.getAdjustNum()%10));
                    curCount = 1;
                }else {
                    curCount++;
                }
            }
        }else {
            genStr = String.valueOf(Math.abs(curPrize.getAdjustNum()%10));
            curCount++;
        }
        /*log.info("当前prize：" + curPrize.getPrize()+",波动为：" + curPrize.getAdjustNum()+",curCount:" + curCount + ",预测" + conPrize.getNo() +  ",预测dan" +
                ":" + genStr);*/

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
