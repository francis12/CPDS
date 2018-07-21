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
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {
        //五星根据计算的波动值前后加减25000，算出五星号码
        List<TCFFCPRIZE> tcffcprizeList = LotteryUtil.genNextWuXingPrizeByGen(conPrize);
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
