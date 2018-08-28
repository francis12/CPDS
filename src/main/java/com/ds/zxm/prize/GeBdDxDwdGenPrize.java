package com.ds.zxm.prize;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.util.DateUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GeBdDxDwdGenPrize extends BaseGenPrize {
    @Override
    void init() {
        file = new File("geBdDxFile.txt");
        allFile = new File("geBdDxAllFile.txt");
    }
    public static final String shuan = "0,2,4,6,8";

    public static final String dan = "1,3,5,7,9";
    @Override
    String getGenPrizeNumsStr(TCFFCPRIZE conPrize,TCFFCPRIZE curPrize) {

        StringBuffer sb = new StringBuffer();
        //取最近10期开奖数据
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Date latestTime = DateUtils.addMinutes(-10, curPrize.getTime());
        tcffcprizeCondition.createCriteria().andTimeGreaterThanOrEqualTo(latestTime);
        tcffcprizeCondition.setOrderByClause("time asc");
        List<TCFFCPRIZE> tcffcprizeList = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        //转波动的大小
        List<String> bdDsList = new ArrayList<>();
        for(TCFFCPRIZE item: tcffcprizeList) {
            int ge = Math.abs(item.getAdjustNum()%10);
            String bdStr = ge >=5?"1":"0";
            bdDsList.add(bdStr);
        }

        //判断下一期波动的大小
        String nextBdDs = this.judgeNextBdDs(bdDsList);
        Integer curDx = curPrize.getGe();


        genStr = "";
        if("0".equals(nextBdDs)) {
            if(curPrize.getAdjustNum() >= 0) {
                for(int i=0;i<5;i++) {
                    int tmp = curDx+i;
                    if (tmp >= 10) {
                        tmp = tmp - 10;
                    }
                    genStr =  genStr + "" + tmp + ",";
                }
            } else {
                for(int i=0;i<5;i++) {
                    int tmp = curDx-i;
                    if (tmp < 0) {
                        tmp = tmp + 10;
                    }
                    genStr =  genStr + "" + tmp + ",";
                }
            }


        } else if("1".equals(nextBdDs)) {
            if(curPrize.getAdjustNum() >= 0) {
                for(int i=5;i<10;i++) {
                    int tmp = curDx+i;
                    if (tmp >= 10) {
                        tmp = tmp - 10;
                    }
                    genStr =  genStr + "" + tmp + ",";
                }
            } else {
                for(int i=5;i<10;i++) {
                    int tmp = curDx-i;
                    if (tmp < 0) {
                        tmp = tmp + 10;
                    }
                    genStr =  genStr + "" + tmp + ",";
                }
            }
        } else {
            genStr = nextBdDs;
        }
        log.info("个位当前波动大小为：" + bdDsList.toString() + ",ben期" + curPrize.getNo() + "波动：" + nextBdDs + ",本期个位大小：" +  curDx + ",预测下期个位:" + genStr);

        return genStr;
    }

    private String judgeNextBdDs(List<String> bdList){
        if(bdList == null || bdList.size() == 0) {
            return "";
        }
        String bdListStr = "";
        for(String item: bdList) {
            bdListStr = bdListStr + item;
        }

        //大小跳方案
        if (bdListStr.endsWith("101") && !bdListStr.endsWith("111101")) {
            return "0";
        } else if (bdListStr.endsWith("010") && !bdListStr.endsWith("000010")) {
            return "1";
        } else {
            //开啥跟啥
            if(bdListStr.endsWith("0")) {
                return "0";
            } else {
                return "1";
            }
        }
    }
    private String judgeNextBdDs2(List<String> bdList){
        if(bdList == null || bdList.size() == 0) {
            return "";
        }
        String bdListStr = "";
        for(String item: bdList) {
            bdListStr = bdListStr + item;
        }
        if (bdListStr.endsWith("0001")) {
            return "waiting";
        }else if (bdListStr.endsWith("1110")) {
            return "waiting";
        }else if (bdListStr.indexOf("000") < 0 && bdListStr.indexOf("111") < 0) {
            return "waiting";
        }else if (bdListStr.endsWith("1101")) {
            return "waiting";
        }else if (bdListStr.endsWith("0010")) {
            return "waiting";
        }else if (bdListStr.endsWith("00100")) {
            return "0";
        } else if (bdListStr.endsWith("11011")) {
            return "1";
        } else if (bdListStr.endsWith("01100")) {
            return "1";
        } else if (bdListStr.endsWith("10011")) {
            return "0";
        } else if (bdListStr.endsWith("1000111")) {
            return "0";
        }else if (bdListStr.endsWith("0111000")) {
            return "1";
        }else if (bdListStr.endsWith("000")) {
            return "0";
        } else if (bdListStr.endsWith("111")) {
            return "1";
        }  else if (bdListStr.endsWith("0101")) {
            return "0";
        } else if (bdListStr.endsWith("1010")) {
            return "1";
        } else   {
            //剩余情况取最近5期出现最多的
            List<String> subBdList = null;
            if(bdList.size() >=5) {
                 subBdList = bdList.subList(bdList.size()-5,bdList.size());
            } else {
                subBdList = bdList;
            }
            int shuangSum = 0;
            int danSum = 0;
            for(String item : subBdList) {
                if("0".equals(item)) {
                    shuangSum++;
                } else {
                    danSum++;
                }
            }
            if(shuangSum >= danSum) {
                return "0";
            } else {
                return "1";
            }
        }
    }

    @Override
    boolean isPrized(TCFFCPRIZE genPrize, TCFFCPRIZE curPrize) {
        boolean result = false;
        if (null != genPrize) {
            if(genStr.indexOf("" + curPrize.getGe()) >= 0) {
                result = true;
            }
        }
        return result;
    }
}
