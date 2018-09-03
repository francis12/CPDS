package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
@Scope("prototype")
@Service
public class HousanBdGenPrize extends BaseGenPrize {

    public List<String> allSanXinNum = new ArrayList<>();

    //后3
    @Override
    void init() {
        file = new File("hou3BdFile.txt");
        allFile = new File("hou3BdAllFile.txt");

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    allSanXinNum.add("" + i + j + k);
                }
            }
        }
    }

    @Override
    public String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrizes) {
        if (null == conPrize) {
            return "";
        }
        //统计最近15000期的遗漏
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Date endTime = curPrizes.getTime();
        Date startTime = DateUtils.addMinutes(-1440, endTime);
        tcffcprizeCondition.createCriteria().andTimeBetween(startTime, endTime);
        List<TCFFCPRIZE> tcffcprizeList = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        Map<String, List<TCFFCPRIZE>> numMissMap = new HashMap<>();
        //以开奖号码为key，将开奖期数存到map中
        tcffcprizeList.stream().forEach(item -> {
            int h3Adjust = Math.abs(item.getAdjustNum()%1000);
            String num = String.valueOf(h3Adjust);
            TCFFCPRIZE tjPrize = new TCFFCPRIZE();
            BeanUtils.copyProperties(item, tjPrize);

            if (numMissMap.get(num) != null) {
                numMissMap.get(num).add(tjPrize);
            } else {
                List<TCFFCPRIZE> list = new ArrayList<>();
                list.add(tjPrize);
                numMissMap.put(num, list);
            }
        });
        Map<String, Integer> prizeCntMap = new HashMap<>();
        for(Map.Entry<String, List<TCFFCPRIZE>> entry : numMissMap.entrySet()) {
            prizeCntMap.put(entry.getKey(),entry.getValue().size());
        }
        Map<String, Integer>  resultMap = LotteryUtil.sortMapByValue(prizeCntMap, "1");
        List<TCFFCPRIZE> result = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            TCFFCPRIZE item = new TCFFCPRIZE();
            int genH3Adjust = Integer.valueOf(entry.getKey());
            int itemResult = 0;
            if(curPrizes.getAdjustNum() >=0) {
                itemResult = curPrizes.getOnlineNum() +  genH3Adjust;
            } else {
                itemResult = curPrizes.getOnlineNum() - genH3Adjust;
            }
            item.setPrize(String.valueOf(itemResult%1000));
            result.add(item);
        }
        if(result.size()>700) {
            genPrizeList = result.subList(0,700);
        }else {
            genPrizeList = result;
        }
        StringBuilder sb = new StringBuilder();
        genPrizeList.stream().forEach(item -> {
            sb.append(item.getPrize() + " ");
        });
        log.info("new bd:" + sb.toString());
        return sb.toString();
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        String hou3Bd = curPrize.getPrize().substring(2,5);
        if (null != genPrizeList && genPrizeList.size() > 0) {
            for (TCFFCPRIZE tcffcprize : genPrizeList) {
                String prize = tcffcprize.getPrize();
                if (StringUtils.isNotEmpty(prize) && prize.equals(hou3Bd)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
