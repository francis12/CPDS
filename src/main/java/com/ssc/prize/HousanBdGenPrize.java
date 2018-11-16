package com.ssc.prize;

import com.ssc.constants.BaseConstants;
import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.util.DateUtils;
import com.ssc.util.LotteryUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
@Scope("prototype")
@Service
public class HousanBdGenPrize extends BaseGenPrize {
    //后3
    @Override
    void init() {
        file = new File("hou3BdFile.txt");
        allFile = new File("hou3BdAllFile.txt");

        this.wfType = BaseConstants.WF_TYPE_HOU3_BD;
    }
    int distance = 1440*15;
    int amount = 700;
    @Override
    public String getGenPrizeNumsStr(TCFFCPRIZE conPrize, TCFFCPRIZE curPrizes) {
        if (null == conPrize) {
            return "";
        }
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Date endTime = curPrizes.getTime();
        Date startTime = DateUtils.addMinutes(-distance, endTime);
        tcffcprizeCondition.createCriteria().andTimeBetween(startTime, endTime);
        List<TCFFCPRIZE> tcffcprizeList = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        Map<String, List<TCFFCPRIZE>> numMissMap = new HashMap<>();
        //以开奖号码为key，将开奖期数存到map中
        tcffcprizeList.stream().forEach(item -> {
            int h3Adjust = item.getAdjustNum()%1000;
            if(h3Adjust < 0) {
                h3Adjust = 1000 + h3Adjust;
            }
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
            int itemResult = curPrizes.getOnlineNum() +  genH3Adjust;
            String itemResultStr = "000" + String.valueOf(itemResult%1000);
            itemResultStr = itemResultStr.substring(itemResultStr.length()-3);
            item.setPrize(itemResultStr);
            result.add(item);
            if (result.size()== amount) {
                Collections.sort(result, new Comparator<TCFFCPRIZE>() {
                    @Override
                    public int compare(TCFFCPRIZE o1, TCFFCPRIZE o2) {
                        return o1.getPrize().compareTo(o2.getPrize());
                    }
                });
                break;
            }
        }
        genPrizeList = result;
        StringBuilder sb = new StringBuilder();
        genPrizeList.stream().forEach(item -> {
            sb.append(item.getPrize() + " ");
        });
        //log.info("预测后3为:" + sb.toString());
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
