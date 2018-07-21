package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class HousanGenPrize extends BaseGenPrize {

    public List<String> allSanXinNum = new ArrayList<>();

    //后3
    @Override
    void init() {
        file = new File("hou3File.txt");
        allFile = new File("hou3AllFile.txt");

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
        Date endTime = DateUtils.addMinutes(-6, conPrize.getTime());
        Date startTime = DateUtils.addMinutes(-16800, endTime);
        tcffcprizeCondition.createCriteria().andTimeBetween(startTime, endTime);
        List<TCFFCPRIZE> tcffcprizeList = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        Map<String, List<TCFFCPRIZE>> numMissMap = new HashMap<>();
        //以开奖号码为key，将开奖期数存到map中
        tcffcprizeList.stream().forEach(item -> {
            String num = item.getPrize().substring(2, 5);
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

        Map<String, List<Integer>> numMissCntMap = new HashMap<>();
        Map<String, Integer> numMissItemCntMap = new HashMap<>();

        //计算每注号码的遗漏值
        for (Map.Entry<String, List<TCFFCPRIZE>> entry : numMissMap.entrySet()) {
            TCFFCPRIZE prePrize = null;
            String num = entry.getKey();
            List<TCFFCPRIZE> prizeList = entry.getValue();

            for (int i = 0; i < prizeList.size(); i++) {
                if (i == 0) {
                    prePrize = prizeList.get(i);
                } else {
                    TCFFCPRIZE curPrize = prizeList.get(i);
                    Integer missDistance = LotteryUtil.calTcffcNoDistance(prePrize, conPrize);
                    if (numMissCntMap.get(num) != null) {
                        numMissCntMap.get(num).add(missDistance);
                    } else {
                        List<Integer> list = new ArrayList<>();
                        list.add(missDistance);
                        numMissCntMap.put(num, list);
                    }

                    //加上最新一期遗漏
                    if (i == prizeList.size() - 1) {
                        Integer latestDistance = LotteryUtil.calTcffcNoDistance(curPrize, conPrize);
                        if (numMissCntMap.get(num) != null) {
                            numMissCntMap.get(num).add(latestDistance);
                        } else {
                            List<Integer> list = new ArrayList<>();
                            list.add(latestDistance);
                            numMissCntMap.put(num, list);
                        }
                        numMissItemCntMap.put(num, latestDistance);
                    }
                    //为下一次计算赋值
                    prePrize = prizeList.get(i);
                }
            }
        }
        //降序取666个最大遗漏
        List<TCFFCPRIZE> resultList = new ArrayList<>();
        for (Map.Entry<String, Integer> lastMissItem : numMissItemCntMap.entrySet()) {
            TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
            tcffcprize.setPrize(lastMissItem.getKey());
            tcffcprize.setOnlineNum(lastMissItem.getValue());
            resultList.add(tcffcprize);
        }
        Collections.sort(resultList, new Comparator<TCFFCPRIZE>() {
            @Override
            public int compare(TCFFCPRIZE o1, TCFFCPRIZE o2) {
                return o2.getOnlineNum().compareTo(o1.getOnlineNum());
            }
        });
        genPrizeList = resultList.subList(0, 788);
        Collections.sort(genPrizeList, new Comparator<TCFFCPRIZE>() {
            @Override
            public int compare(TCFFCPRIZE o1, TCFFCPRIZE o2) {
                return o1.getPrize().compareTo(o2.getPrize());
            }
        });
        StringBuilder sb = new StringBuilder();
        genPrizeList.stream().forEach(item -> {
            sb.append(item.getPrize() + " ");
        });
        return sb.toString();
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        String hou3Prize = curPrize.getPrize().substring(2, 5);
        if (null != genPrizeList && genPrizeList.size() > 0) {
            for (TCFFCPRIZE tcffcprize : genPrizeList) {
                String prize = tcffcprize.getPrize();
                if (StringUtils.isNotEmpty(prize) && prize.equals(hou3Prize)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
