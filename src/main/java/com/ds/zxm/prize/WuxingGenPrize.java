package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.math.BigDecimal.*;
import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class WuxingGenPrize extends BaseGenPrize {
    @Override
    void init() {
        file = new File("wuXingFile.txt");
        allFile = new File("wuXingAllFile.txt");
        isWrite2File = false;
    }

    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {
        //五星根据计算的波动值前后加减25000，算出五星号码
        log.info(conPrize.getNo() + "预测波动:" + conPrize.getAdjustNum());
        List<TCFFCPRIZE> tcffcprizeList = LotteryUtil.genNextWuXingPrizeByGen(conPrize, 30000);
        Collections.sort(tcffcprizeList);

        genPrizeList = tcffcprizeList;
        StringBuffer wuXingSb = new StringBuffer();

        HashMap<String, Integer> map = new HashMap<>();
        HashMap<String, Integer> qian4Map = new HashMap<>();

        if(null != tcffcprizeList && tcffcprizeList.size() > 0) {
            tcffcprizeList.stream().forEach(item -> {
                wuXingSb.append(item.getPrize() + " ");
                setMapValuePlus(String.valueOf(item.getWan()), map);
                setMapValuePlus(String.valueOf(item.getQian()), map);
                setMapValuePlus(String.valueOf(item.getBai()), map);
                setMapValuePlus(String.valueOf(item.getShi()), map);
                setMapValuePlus(String.valueOf(item.getGe()), map);

                setMapValuePlus(String.valueOf(item.getPrize().substring(0,4)), qian4Map);
            });
        }
        LotteryUtil.sortMapByValue(qian4Map, "-1");
        return wuXingSb.toString();
    }
    private void setMapValuePlus(String key, Map<String, Integer> map) {
        if(null == map.get(key)) {
            map.put(key, 1);
        } else {
            map.put(key, map.get(key) + 1);
        }
    }
    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;if (null != genPrizeList && genPrizeList.size() > 0) {
            result = genPrizeList.contains(curPrize.getPrize());
            for(TCFFCPRIZE item : genPrizeList) {
                if(item.getPrize().equals(curPrize.getPrize())) {
                    result = true;
                    break;
                }
            }
        }
        log.info(curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(result?"中":"挂"));
        return result;
    }
}
