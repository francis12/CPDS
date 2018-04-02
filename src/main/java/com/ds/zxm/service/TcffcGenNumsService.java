package com.ds.zxm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ds.zxm.mapper.CurNOModelDAO;
import com.ds.zxm.mapper.GenPrizeModelDAO;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.HttpUtil;
import com.ds.zxm.util.LotteryUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class TcffcGenNumsService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private CurNOModelDAO curNOModelDAO;
    @Resource
    private GenPrizeModelDAO genPrizeModelDAO;
    Logger log = Logger.getLogger(TcffcGenNumsService.class);

    private TCFFCPRIZE genPrize = null;
    public void generateNextNums(TCFFCPRIZE  curPrize) {
        File file = new File("gen.txt");
        File file2 = new File("update.txt");
        try {
            Date curTime = curPrize.getTime();
            //当天过去三分钟的开奖数据
            Date last1Min = DateUtils.addMinutes(-1, curTime);
            Date last2Min = DateUtils.addMinutes(-2, curTime);
            Date last3Min = DateUtils.addMinutes(-3, curTime);
            Date last4Min = DateUtils.addMinutes(-4, curTime);

            //最近三天待开奖期，待开奖前一期，待开奖后一期的记录
            Date last1Day = DateUtils.addDate(-1, curTime);
            Date last2Day = DateUtils.addDate(-2, curTime);
            Date last3Day = DateUtils.addDate(-3, curTime);

            //下一分钟即为待开奖期
            Date nextMin = DateUtils.addMinutes(1, curTime);
            Date nextMinlast1Day = DateUtils.addDate(-1, nextMin);
            Date nextMinlast2Day = DateUtils.addDate(-2, nextMin);
            Date nextMinlast3Day = DateUtils.addDate(-3, nextMin);

            Date next2Min = DateUtils.addMinutes(2, curTime);
            Date next2Minlast1Day = DateUtils.addDate(-1, next2Min);
            Date next2Minlast2Day = DateUtils.addDate(-2, next2Min);
            Date next2Minlast3Day = DateUtils.addDate(-3, next2Min);

            List<Date> dateList = new ArrayList();

            dateList.add(last1Min);
            dateList.add(last2Min);
            dateList.add(last3Min);
            dateList.add(last4Min);

            dateList.add(last1Day);
            dateList.add(last2Day);
            dateList.add(last3Day);

            dateList.add(nextMinlast1Day);
            dateList.add(nextMinlast2Day);
            dateList.add(nextMinlast3Day);

            dateList.add(next2Minlast1Day);
            dateList.add(next2Minlast2Day);
            dateList.add(next2Minlast3Day);

            List<String> noList = new ArrayList<>();

            for(Date dateItem : dateList) {
                noList.add(TcffcPrizeConverter.genNofromTime(dateItem));
            }

            TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
            condition.createCriteria().andNoIn(noList);

            List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
            prizeList.add(curPrize);

            //去除异常值后计算平均值
           // List<Integer> onlineNums = new ArrayList<>();
            List<Integer> adjustNums = new ArrayList<>();

            for (TCFFCPRIZE item : prizeList) {
                //onlineNums.add(item.getOnlineNum());
                adjustNums.add(item.getAdjustNum());
            }

            //调用一次代表去除一个波动值
           /* this.calAvgNum(onlineNums);
            BigDecimal avgOnlineNum = this.calAvgNum(onlineNums);*/
           //去除3次最大偏移值
            this.calAvgNum(adjustNums);
            this.calAvgNum(adjustNums);
            this.calAvgNum(adjustNums);
            BigDecimal avgAdjustNum = this.calAvgNum(adjustNums);

            TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
            tcffcprize.setAdjustNum(avgAdjustNum.intValue());
            tcffcprize.setOnlineNum(curPrize.getOnlineNum() + avgAdjustNum.intValue());
            tcffcprize.setTime(nextMin);

            TCFFCPRIZE conPrize = TcffcPrizeConverter.convert2TCFFCPrize(tcffcprize);

            boolean isQian2Prized = false;
            if (genPrize != null) {
                if(LotteryUtil.judgeIsmatchBetween3(genPrize.getWan(), curPrize.getWan()) &&
                        LotteryUtil.judgeIsmatchBetween3(genPrize.getQian(), curPrize.getQian())) {
                    isQian2Prized = true;
                }
            }
            log.info(TcffcPrizeConverter.genNofromTime(nextMin) + "期理论数据数:" + dateList.size() + ",实际参与计算数据数:" + prizeList.size());
            String qian2GenStr = " zhuan(" + LotteryUtil.genPy3NumStr(conPrize.getWan()) + "*" + LotteryUtil.genPy3NumStr(conPrize.getQian()) + ")zhuan ";
            String result = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(isQian2Prized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + conPrize.getPrize() + qian2GenStr;
            //String outPutStr ="实际调整值:" + curPrize.getAdjustNum() + "\r\n" + "第" + curPrize.getNo() + "期在线人数为:" + curPrize.getOnlineNum() +  ",预测第" + TcffcPrizeConverter.genNofromTime(nextMin) + "期的调整值为:" + avgAdjustNum.intValue() ;
            FileUtils.writeStringToFile(file, result, true);
            FileUtils.writeStringToFile(file2, result, false);
            updateCurNO(nextMin);
            updateGenPrizeResult(conPrize, curPrize);

            genPrize = conPrize;
        } catch (Exception e) {
            log.error("generateNextNums error", e);
        }
    }
    public void updateCurNO(Date date) {
        CurNOModel curNOModel = new CurNOModel();
        curNOModel.setLotteryCode("TCFFC");
        String nextNO = TcffcPrizeConverter.genNofromTime(date);
        curNOModel.setNextNo(nextNO);
        CurNOModelCondition curNOModelCondition = new CurNOModelCondition();
        curNOModelCondition.createCriteria().andLotteryCodeEqualTo("TCFFC");
        int cnt = curNOModelDAO.countByCondition(curNOModelCondition);
        if (cnt > 0) {
            curNOModelDAO.updateByConditionSelective(curNOModel, curNOModelCondition);
        } else {
            curNOModelDAO.insert(curNOModel);
        }
    }

    public void updateGenPrizeResult(TCFFCPRIZE nextPrize, TCFFCPRIZE curPirze) {
        GenPrizeModel genPrizeModel = new GenPrizeModel();
        genPrizeModel.setLotteryCode(nextPrize.getLotteryCode());
        genPrizeModel.setNo(nextPrize.getNo());
        genPrizeModel.setGenPrize(nextPrize.getPrize());

        genPrizeModelDAO.insert(genPrizeModel);


        GenPrizeModelCondition genPrizeModelCondition = new GenPrizeModelCondition();
        genPrizeModelCondition.createCriteria().andLotteryCodeEqualTo(curPirze.getLotteryCode()).andNoEqualTo(curPirze.getNo());

        GenPrizeModel lastPrizeModel = new GenPrizeModel();
        lastPrizeModel.setRealPrize(curPirze.getPrize());
        genPrizeModelDAO.updateByConditionSelective(lastPrizeModel, genPrizeModelCondition);


    }

    //去除一个最大偏移值后的平均值
    private BigDecimal calAvgNum(List<Integer> items) {
        if (items.size() == 0) {
            return BigDecimal.ZERO;
        } else if (items.size() == 1) {
            return  BigDecimal.valueOf(items.get(0));
        }
        //与其余平均值的最大差
        double maxLeftAvg = 0;
        int maxItem = 0;
        for (int i = 0; i < items.size(); i++) {

            double curItem = items.get(i);
            double leftSum = 0;
            for (int j = 0; j < items.size(); j++) {
                if (j != i) {
                    leftSum =+ items.get(j);
                }
            }
            double leftAvg = BigDecimal.valueOf(leftSum).divide(BigDecimal.valueOf(items.size()-1), 2, BigDecimal.ROUND_FLOOR).doubleValue();
            if(maxLeftAvg <= leftAvg) {
                maxLeftAvg = leftAvg;
                maxItem = i;
            }
        }

        //log.info("剔除最大偏移值:" + items.get(maxItem));
        items.remove(maxItem);
        double maxTotal = 0;
        for (int i = 0; i < items.size(); i++) {
                maxTotal += items.get(i);
        }
        return  BigDecimal.valueOf(maxTotal).divide(BigDecimal.valueOf(items.size()), 2, BigDecimal.ROUND_FLOOR);
    }
}