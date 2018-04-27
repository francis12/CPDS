package com.ds.zxm.service;

import com.ds.zxm.mapper.CurNOModelDAO;
import com.ds.zxm.mapper.GenPrizeModelDAO;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
        //前2
        File file = new File("qian2AllFile.txt");
        File file2 = new File("qian2File.txt");

        //中3定位胆
        File zhong3File = new File("zhong3File.txt");
        File zhong3AllFile = new File("zhong3AllFile.txt");

        //前3定位胆
        File qian3File = new File("qian3File.txt");
        File qian3AllFile = new File("qian3AllFile.txt");

        //千位定位胆
        File qianFile = new File("qianFile.txt");
        File qianAllFile = new File("qianAllFile.txt");

        try {
            boolean isQian3Prized = false;
            boolean isZhong3Prized = false;
            boolean isQian2Prized = false;
            boolean isQianPrized = false;
            if (genPrize != null) {
                if(LotteryUtil.judgeIsmatchBetweenPost4(genPrize.getWan(), curPrize.getWan()) &&
                        LotteryUtil.judgeIsmatchBetweenPost4(genPrize.getQian(), curPrize.getQian())) {
                    isQian2Prized = true;
                }
                if ( LotteryUtil.judgeIsmatchBetween3(genPrize.getQian(), curPrize.getQian())) {
                    isQianPrized = true;
                }
                if ( LotteryUtil.judgeIsmatchBetween4(genPrize.getWan(), curPrize.getWan())
                        && LotteryUtil.judgeIsmatchBetweenPost4(genPrize.getQian(), curPrize.getQian())
                        &&LotteryUtil.judgeIsmatchBetween4(genPrize.getBai(), curPrize.getBai())) {
                    isQian3Prized = true;
                }
                if (  LotteryUtil.judgeIsmatchBetweenPost4(genPrize.getQian(), curPrize.getQian())
                        &&LotteryUtil.judgeIsmatchBetween4(genPrize.getBai(), curPrize.getBai())
                        &&LotteryUtil.judgeIsmatchBetween4(genPrize.getShi(), curPrize.getShi())) {
                    isZhong3Prized = true;
                }
            }
            TCFFCPRIZE conPrize = this.calGenPrizeByRate(curPrize);
            //this.calGenPrizeBySort();
            Date nextMin = DateUtils.addMinutes(1, curPrize.getTime());
            //String outPutStr ="实际调整值:" + curPrize.getAdjustNum() + "\r\n" + "第" + curPrize.getNo() + "期在线人数为:" + curPrize.getOnlineNum() +  ",预测第" + TcffcPrizeConverter.genNofromTime(nextMin) + "期的调整值为:" + avgAdjustNum.intValue() ;

            //前2  8*8
            String qian2GenStr = " qian2(" + LotteryUtil.genPyPost4NumStr(conPrize.getWan()) + "*" + LotteryUtil.genPyPost4NumStr(conPrize.getQian()) + ")qian2 ";
            String result = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(isQian2Prized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + conPrize.getPrize() + qian2GenStr;
            FileUtils.writeStringToFile(file, result, true);
            FileUtils.writeStringToFile(file2, result, false);

            String qianGenStr = " qian(" + LotteryUtil.genPy3NumStr(conPrize.getQian()) + ")qian ";
            String qianStrResult = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(isQianPrized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + conPrize.getPrize() + qianGenStr;
            FileUtils.writeStringToFile(qianAllFile, qianStrResult, true);
            FileUtils.writeStringToFile(qianFile, qianStrResult, false);

            //中3 8*9*9
            String zhong3GenStr = " zhong3(" + LotteryUtil.genPyPost4NumStr(conPrize.getQian()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getBai()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getShi())  + ")zhong3 ";
            String zhong3StrResult = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(isZhong3Prized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + conPrize.getPrize() + zhong3GenStr;
            FileUtils.writeStringToFile(zhong3AllFile, zhong3StrResult, true);
            FileUtils.writeStringToFile(zhong3File, zhong3StrResult, false);

            //前3 9*8*9
            String qian3GenStr = " qian3(" + LotteryUtil.genPy4NumStr(conPrize.getWan()) + "*" + LotteryUtil.genPyPost4NumStr(conPrize.getQian()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getBai())  + ")qian3 ";
            String qian3StrResult = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(isQian3Prized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + conPrize.getPrize() + qian3GenStr;
            FileUtils.writeStringToFile(qian3AllFile, qian3StrResult, true);
            FileUtils.writeStringToFile(qian3File, qian3StrResult, false);


            updateCurNO(nextMin);
            updateGenPrizeResult(conPrize, curPrize);

            genPrize = conPrize;
        } catch (Exception e) {
            log.error("generateNextNums error", e);
        }
    }

    //通过累加最近几天，最近几期的数据计算
    private TCFFCPRIZE calGenPrize(TCFFCPRIZE curPrize) {
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
    private TCFFCPRIZE calGenPrizeByRate(TCFFCPRIZE curPrize) {
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

        for(Date dateItem : dateList) {
            noList.add(TcffcPrizeConverter.genNofromTime(dateItem));
        }

        TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
        condition.createCriteria().andNoIn(noList);

        List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
        prizeList.add(curPrize);

        List<BigDecimal> rateList = new ArrayList<>();
        for(TCFFCPRIZE tcffcprize : prizeList) {

            BigDecimal adjustNum = BigDecimal.valueOf(tcffcprize.getAdjustNum());
            BigDecimal onlineNum = BigDecimal.valueOf(tcffcprize.getOnlineNum());
            BigDecimal rate = adjustNum.divide(onlineNum,12,BigDecimal.ROUND_FLOOR);
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

        String adjustStr = "实际：" + curPrize.getAdjustNum() + "\r\n预测" + conPrize.getNo() + " : "  + conPrize.getAdjustNum();
        try {
            FileUtils.writeStringToFile(new File("adjust.txt"), adjustStr, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  conPrize;
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
    private BigDecimal calAvgNum(List<BigDecimal> items) {

        StringBuffer input = new StringBuffer();
        items.forEach(
                (BigDecimal avgItem) -> {
                    input.append(avgItem.toString()+ ",");
                }
        );
        log.info("过滤前输入为：" + input.toString());
        if (items.size() == 0) {
            return BigDecimal.ZERO;
        } else if (items.size() == 1) {
            return  items.get(0);
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
            BigDecimal leftAvg = leftSum.divide(BigDecimal.valueOf(items.size()-1), 12, BigDecimal.ROUND_FLOOR);
            BigDecimal xcNum = curItem.subtract(leftAvg).abs();
            if(maxXcnum.compareTo(xcNum) <= 0) {
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
                    output.append(avgItem.toString()+ ",");
                }
        );
        log.info("过滤后输出为：" + input.toString());
        log.info("------------");

        BigDecimal result = maxTotal.divide(BigDecimal.valueOf(items.size()), 12, BigDecimal.ROUND_FLOOR);
        return  result;
    }

    private BigDecimal calGenPrizeBySort(List<BigDecimal> items) {

        if(null == items || items.size() == 0) {
            return null;
        }
        this.sortOnlineRateList(items);
        if(items.size() > 2) {
            items.remove(0);
            items.remove(items.size()-1);
        }
        if(items.size() > 2) {
            items.remove(0);
            items.remove(items.size()-1);
        }
        BigDecimal sum = BigDecimal.ZERO;
        for(BigDecimal item : items) {
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
}
