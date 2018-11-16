package com.ssc.prize;

import com.ssc.model.TCFFCPRIZE;
import com.ssc.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class QianDwdGenPrize extends BaseGenPrize {
    //千位定位胆
    @Override
    void init() {
        file = new File("qianFile.txt");
        allFile = new File("qianAllFile.txt");
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize, TCFFCPRIZE curPrize) {
        //根据波动条件判断当前是否适合推波
        //八码推波4期
        String result = LotteryUtil.genPyPost4NumStr(conPrize.getQian());

        int tzNumAbs = Math.abs(conPrize.getAdjustNum() - curPrize.getAdjustNum());
        log.info(conPrize.getNo() + "计算的波动值为:" + conPrize.getAdjustNum() + ",实际" + curPrize.getNo() + "波动值为:" + curPrize.getAdjustNum() + " 预测开奖号码为:" + result);
        if (tzNumAbs > 10000) {
            isToTz = false;
            log.info(conPrize.getNo() + "波动差值大于10000，不适合投注,跳过！");
            result = "不适合投注" + result;
        }
        return result;
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (null != genPrize) {
            if ( LotteryUtil.judgeIsmatchBetweenPost4(genPrize.getQian(), curPrize.getQian())) {
                result = true;
            }
        }
        if(isToTz){
            log.info(curPrize.getNo() + " 开奖号码:" + curPrize.getPrize() + "(" + (result?"中":"挂") + ")");
        }
        return result;
    }
}
