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
public class Qh4GenPrize extends BaseGenPrize {


    //前后4
    @Override
    void init() {
        file = new File("qh4File.txt");
        allFile = new File("qh4AllFile.txt");
    }

    @Override
    public String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Date endTime = DateUtils.addMinutes(-1, conPrize.getTime());
        Date startTime = DateUtils.addMinutes(-10, endTime);
        tcffcprizeCondition.createCriteria().andTimeBetween(startTime, endTime);
        List<TCFFCPRIZE> tcffcprizeList = tcffcprizedao.selectByCondition(tcffcprizeCondition);
        Map<String, Integer> numCntMap= new HashMap<>();
        for(TCFFCPRIZE tcffcprize : tcffcprizeList) {
            String wan = String.valueOf(tcffcprize.getWan());
            String qian = String.valueOf(tcffcprize.getQian());
            String bai = String.valueOf(tcffcprize.getBai());
            String shi = String.valueOf(tcffcprize.getShi());
            String ge = String.valueOf(tcffcprize.getGe());
            this.countMapKey(numCntMap, wan);
            this.countMapKey(numCntMap, qian);
            this.countMapKey(numCntMap, bai);
            this.countMapKey(numCntMap, shi);
            this.countMapKey(numCntMap, ge);
        }
        LotteryUtil.sortMapByValue(numCntMap, "1");
        List<String> list =  new ArrayList<>(LotteryUtil.sortMapByValue(numCntMap, "1").keySet());
        List<String> sortDanList = list.subList(0, 2);
        List<String> sortDanList2 = list.subList(list.size()-2, list.size());
        sortDanList.addAll(sortDanList2);
        StringBuilder danSb = new StringBuilder();
        sortDanList.stream().forEach(item -> {
            danSb.append(item);
        });
        System.out.println(conPrize.getNo() + "四星预测胆码为:" + danSb.toString());
        List<String> result = LotteryUtil.genNumfromDan(sortDanList, true, false,false,false,false);
        StringBuilder sb = new StringBuilder();
        result.stream().forEach( item -> {
            sb.append(item + " ");
        });
        return sb.toString();
    }

    private void countMapKey(Map<String, Integer> map, String key){
        if(map.get(key) == null) {
            map.put(key, 1);
        } else {
            map.put(key, map.get(key)+1);
        }
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (null != genPrize) {
            String prizeNumsStr = this.getGenPrizeNumsStr(genPrize, curPrize);
            String qian4 = curPrize.getPrize().substring(0,4);
            String hou4 = curPrize.getPrize().substring(1,5);
            if(prizeNumsStr.indexOf(qian4) >= 0 ) {
                System.out.println(curPrize.getNo() + "前4中了！");
                result = true;
            }
            if(prizeNumsStr.indexOf(hou4) >= 0 ) {
                System.out.println(curPrize.getNo() + "后4中了！");

                result = true;
            }
        }
        return result;
    }
}
