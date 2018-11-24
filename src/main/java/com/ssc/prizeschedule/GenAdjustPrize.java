package com.ssc.prizeschedule;

import com.ssc.constants.BaseConstants;
import com.ssc.mapper.CurNoModelDAO;
import com.ssc.mapper.GenPrizeModelDAO;
import com.ssc.mapper.TCFFCPRIZEDAO;
import com.ssc.mapper.TecentTimeDAO;
import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TCFFCPRIZECondition;
import com.ssc.model.TcffcPrizeConverter;
import com.ssc.service.TcffcGenNumsService;
import com.ssc.util.DateUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Scope("prototype")
@Service
public class GenAdjustPrize {


    @Resource
    protected TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private CurNoModelDAO curNOModelDAO;
    @Resource
    private GenPrizeModelDAO genPrizeModelDAO;
    @Resource
    private TecentTimeDAO tecentTimeDAO;
    Logger log = Logger.getLogger(TcffcGenNumsService.class);

    private TCFFCPRIZE genPrize = null;
    public List<TCFFCPRIZE> genPrizeList = null;
    //是否投注
    protected boolean isToTz = true;
    //是否写入文件
    protected boolean isWrite2File = true;
    //是否同步预测结果到数据库
    protected boolean isSyncGenData = false;
    //千位定位胆
    File file = null;
    File allFile = null;
    String genStr = "";
    int curCount = 0;

    String wfType ="";


    public void generateNextNums(TCFFCPRIZE curPrize) {
        TCFFCPRIZE conPrize = this.calGenPrizeByRateNum(curPrize, 3);
    }
    protected void writeResult2File(String result) {
        try {
            if (isWrite2File){
                FileUtils.writeStringToFile(file, result, false);
                FileUtils.writeStringToFile(allFile, result, true);
            }
        } catch (IOException e) {
            log.error("BaseGenPrize writeResult2File error", e);
        }
    };

    //通过累加最近几天，最近几期的数据计算
    public TCFFCPRIZE calGenPrize(TCFFCPRIZE curPrize) {
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

        for (Date dateItem : dateList) {
            noList.add(TcffcPrizeConverter.genNofromTime(dateItem));
        }

        TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
        condition.createCriteria().andNoIn(noList);

        List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
        prizeList.add(curPrize);

        //去除异常值后计算平均值
        // List<Integer> onlineNums = new ArrayList<>();
        List<BigDecimal> adjustNums = new ArrayList<>();

        for (TCFFCPRIZE item : prizeList) {
            adjustNums.add(BigDecimal.valueOf(item.getAdjustNum()));
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

        log.info(TcffcPrizeConverter.genNofromTime(nextMin) + "期理论数据数:" + dateList.size() + ",实际参与计算数据数:" + prizeList.size());
        return conPrize;
    }

    //通过计算人数占比预测下一期
    public TCFFCPRIZE calGenPrizeByRate(TCFFCPRIZE curPrize) {
        Date curTime = curPrize.getTime();
        //当天过去三分钟的开奖数据
        Date last1Min = DateUtils.addMinutes(-1, curTime);
        Date last2Min = DateUtils.addMinutes(-2, curTime);

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

        for (Date dateItem : dateList) {
            noList.add(TcffcPrizeConverter.genNofromTime(dateItem));
        }

        TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
        condition.createCriteria().andNoIn(noList);

        List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
        prizeList.add(curPrize);

        List<BigDecimal> rateList = new ArrayList<>();
        for (TCFFCPRIZE tcffcprize : prizeList) {

            BigDecimal adjustNum = BigDecimal.valueOf(tcffcprize.getAdjustNum());
            BigDecimal onlineNum = BigDecimal.valueOf(tcffcprize.getOnlineNum());
            BigDecimal rate = adjustNum.divide(onlineNum, 12, BigDecimal.ROUND_FLOOR);
            rateList.add(rate);
        }
        /*this.calAvgNum(rateList);
        this.calAvgNum(rateList);
        this.calAvgNum(rateList);
        BigDecimal rate = this.calAvgNum(rateList);*/
        BigDecimal avgRate = this.calGenPrizeBySort(rateList);

        BigDecimal curOnlineNum = BigDecimal.valueOf(curPrize.getOnlineNum());
        BigDecimal adjustNum = curOnlineNum.multiply(avgRate);
        BigDecimal genOnlineNum = adjustNum.add(curOnlineNum);

        TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
        tcffcprize.setAdjustNum(adjustNum.intValue());
        tcffcprize.setOnlineNum(genOnlineNum.intValue());
        tcffcprize.setTime(nextMin);

        TCFFCPRIZE conPrize = TcffcPrizeConverter.convert2TCFFCPrize(tcffcprize);
        return conPrize;
    }

    //去除一个最大偏移值后的平均值
    private BigDecimal calAvgNum(List<BigDecimal> items) {

        StringBuffer input = new StringBuffer();
        items.forEach(
                (BigDecimal avgItem) -> {
                    input.append(avgItem.toString() + ",");
                }
        );
        //log.info("过滤前输入为：" + input.toString());
        if (items.size() == 0) {
            return BigDecimal.ZERO;
        } else if (items.size() == 1) {
            return items.get(0);
        }
        //与其余平均值的最大差
        BigDecimal maxXcnum = BigDecimal.ZERO;
        int maxItem = 0;
        for (int i = 0; i < items.size(); i++) {

            BigDecimal curItem = items.get(i);
            BigDecimal leftSum = BigDecimal.ZERO;
            for (int j = 0; j < items.size(); j++) {
                if (j != i) {
                    leftSum = leftSum.add(items.get(i));
                }
            }
            BigDecimal leftAvg = leftSum.divide(BigDecimal.valueOf(items.size() - 1), 12, BigDecimal.ROUND_FLOOR);
            BigDecimal xcNum = curItem.subtract(leftAvg).abs();
            if (maxXcnum.compareTo(xcNum) <= 0) {
                maxXcnum = xcNum;
                maxItem = i;
            }
        }

        //log.info("剔除最大偏移值:" + items.get(maxItem));
        items.remove(maxItem);
        BigDecimal maxTotal = BigDecimal.ZERO;
        for (int i = 0; i < items.size(); i++) {
            maxTotal = maxTotal.add(items.get(i));
        }

        StringBuffer output = new StringBuffer();
        items.forEach(
                (BigDecimal avgItem) -> {
                    output.append(avgItem.toString() + ",");
                }
        );
        //log.info("过滤后输出为：" + input.toString());
        //log.info("------------");

        BigDecimal result = maxTotal.divide(BigDecimal.valueOf(items.size()), 12, BigDecimal.ROUND_FLOOR);
        return result;
    }

    private BigDecimal calGenPrizeBySort(List<BigDecimal> items) {

        if (null == items || items.size() == 0) {
            return null;
        }
        int size = items.size();
        this.sortOnlineRateList(items);
        //delete  half
        for (int i = 0; i < (size / 4); i++) {
            items.remove(0);
            items.remove(items.size() - 1);
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal item : items) {
            sum = sum.add(item);
        }
        BigDecimal avg = sum.divide(BigDecimal.valueOf(items.size()), 12, RoundingMode.FLOOR);
        return avg;
    }

    //排序
    private void sortOnlineRateList(List<BigDecimal> items) {
        items.sort(new Comparator<BigDecimal>() {
            @Override
            public int compare(BigDecimal o1, BigDecimal o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public static void main(String[] args) {

        int start = 10000;
        int end = 50000;
        int curPostNum = 271936597 / 10000 * 10000;

        System.out.println((curPostNum + start) + "-" + (curPostNum + end));
    }


    //通过计算人数占比预测下一期
    public TCFFCPRIZE calGenPrizeByRateNum(TCFFCPRIZE curPrize, int num) {
        Date curTime = curPrize.getTime();
        //下一分钟即为待开奖期
        Date nextMin = DateUtils.addMinutes(1, curTime);
        Date next2Min = DateUtils.addMinutes(2, curTime);


        //当天过去三分钟的开奖数据
        List<Date> dateList = new ArrayList();

        for (int i = 1; i <= num; i++) {
            dateList.add(DateUtils.addMinutes(-i, curTime));
            dateList.add(DateUtils.addDate(-i, curTime));
            dateList.add(DateUtils.addDate(-i, nextMin));
            dateList.add(DateUtils.addDate(-i, next2Min));
        }
        dateList.add(curTime);
        List<String> noList = new ArrayList<>();

        for (Date dateItem : dateList) {
            noList.add(TcffcPrizeConverter.genNofromTime(dateItem));
        }

        Collections.sort(noList);
        TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
        condition.createCriteria().andNoIn(noList);

        List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
        prizeList.add(curPrize);

        List<BigDecimal> rateList = new ArrayList<>();
        for (TCFFCPRIZE tcffcprize : prizeList) {

            BigDecimal adjustNum = BigDecimal.valueOf(tcffcprize.getAdjustNum());
            BigDecimal onlineNum = BigDecimal.valueOf(tcffcprize.getOnlineNum());
            BigDecimal rate = adjustNum.divide(onlineNum, 12, BigDecimal.ROUND_FLOOR);
            rateList.add(rate);
        }
        /*this.calAvgNum(rateList);
        this.calAvgNum(rateList);
        this.calAvgNum(rateList);
        BigDecimal rate = this.calAvgNum(rateList);*/
        BigDecimal avgRate = this.calGenPrizeBySort(rateList);

        BigDecimal curOnlineNum = BigDecimal.valueOf(curPrize.getOnlineNum());
        BigDecimal adjustNum = curOnlineNum.multiply(avgRate);
        BigDecimal genOnlineNum = adjustNum.add(curOnlineNum);

        TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
        tcffcprize.setAdjustNum(adjustNum.intValue());
        tcffcprize.setOnlineNum(genOnlineNum.intValue());
        tcffcprize.setTime(nextMin);

        TCFFCPRIZE conPrize = TcffcPrizeConverter.convert2TCFFCPrize(tcffcprize);
        return conPrize;
    }
}
