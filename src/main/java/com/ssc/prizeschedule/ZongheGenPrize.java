package com.ssc.prizeschedule;

import com.ssc.model.TCFFCPRIZE;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ZongheGenPrize extends BaseGenPrize {
    //前4
    @Override
    void init() {
    }

    @Override
    public String getGenPrizeNumsStr( TCFFCPRIZE curPrize) {
        String result = "";
       /*  if (conPrize != null) {
            Integer onlineNum = conPrize.getOnlineNum();
            String preOnlineNumStr = String.valueOf(onlineNum).substring(0,5);
            char[] chars = preOnlineNumStr.toCharArray();
            int sum = 0;
            for(int k=0; k <chars.length; k++) {
                int src = Integer.valueOf("" + chars[k]);
                sum = sum + src;
            }
            result = (sum % 2 == 0)? "双" : "单";
            if(conPrize.getAdjustNum() > 10000 || conPrize.getAdjustNum() < -10000) {
                result = result + "  ，" + conPrize.getAdjustNum() + "波动过大，不建议投注";
            }
        }*/
        return result;
    }

    @Override
    boolean isPrized( TCFFCPRIZE curPrize) {
        /*String genStr = this.getGenPrizeNumsStr(genPrize, curPrize);
        String curStr = this.getGenPrizeNumsStr(curPrize, curPrize);
        if (genPrize !=null) {
            log.info(genPrize.getNo() + ":期，预测在线人数:" + genPrize.getOnlineNum() + "---调整值:" + genPrize.getAdjustNum() + genStr);
        }
        if (curPrize != null) {
            log.info(curPrize.getNo() + ":期，实际在线人数:" + curPrize.getOnlineNum() + "---调整值:" + curPrize.getAdjustNum() + curStr);
        }
        return genStr.equals(curStr);*/
        return false;
    }
}
