package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.*;
import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class WuxingGenPrize extends BaseGenPrize {
    //千位定位胆
    @Override
    void init() {
        file = new File("wuXingFile.txt");
        allFile = new File("wuXingAllFile.txt");
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize) {
        //五星根据计算的波动值前后加减25000，算出五星号码
        List<TCFFCPRIZE> tcffcprizeList = genNextWuXingPrizeByGen(conPrize);
        Collections.sort(tcffcprizeList);

        genPrizeList = tcffcprizeList;
        StringBuffer wuXingSb = new StringBuffer();
        if(null != tcffcprizeList && tcffcprizeList.size() > 0) {
            tcffcprizeList.stream().forEach(item -> {
                wuXingSb.append(item.getPrize() + " ");
            });
        }
        return wuXingSb.toString();
    }
    //根据当前期生成下期五星计划
    private List<TCFFCPRIZE> genNextWuXingPrizeByGen(TCFFCPRIZE genPrize) {
        int startPost = genPrize.getOnlineNum()-20000;
        int endPost = genPrize.getOnlineNum() + 20000;
        List<TCFFCPRIZE> result = this.createPrizeNumList2(startPost, endPost, genPrize.getTime());
        return result;

    } //生成投注号码
    public static List<TCFFCPRIZE> createPrizeNumList2(int preStart, int preEnd, Date time) {
        List<TCFFCPRIZE> toPrizeNumList = new ArrayList<>();
        int startSub = new BigDecimal(preStart).divide(new BigDecimal(10000), 0 , RoundingMode.HALF_UP).intValue();
        int endSub = new BigDecimal(preEnd).divide(new BigDecimal(10000), 0 , RoundingMode.HALF_UP).intValue();
        for(int j = startSub; j <= endSub; j++) {
            for(int i = 0; i<9999; i++) {
                int online = j * 10000 + i;
                TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
                tcffcprize.setTime(time);
                tcffcprize.setOnlineNum(online);
                tcffcprize.setLotteryCode("TCFFC");
                TCFFCPRIZE convertResult = TcffcPrizeConverter.convert2TCFFCPrize(tcffcprize);
                toPrizeNumList.add(convertResult);
            }
        }
        return toPrizeNumList;
    }
    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (null != genPrizeList && genPrizeList.size() > 0) {
            result = genPrizeList.contains(curPrize.getPrize());
            for(TCFFCPRIZE item : genPrizeList) {
                if(item.getPrize().equals(curPrize.getPrize())) {
                    result = true;
                    break;
                }
            }
        }
        System.out.println(curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(result?"中":"挂"));
        return result;
    }
}
