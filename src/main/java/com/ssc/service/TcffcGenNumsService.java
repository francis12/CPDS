package com.ssc.service;

import com.ssc.constants.BaseConstants;
import com.ssc.mapper.CurNoModelDAO;
import com.ssc.mapper.GenPrizeModelDAO;
import com.ssc.mapper.TCFFCPRIZEDAO;
import com.ssc.mapper.TecentTimeDAO;
import com.ssc.model.*;
import com.ssc.prizeschedule.BaseGenPrize;
import com.ssc.util.DateUtils;
import com.ssc.util.LotteryUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Scope("prototype")
public class TcffcGenNumsService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private CurNoModelDAO curNOModelDAO;
    @Resource
    private GenPrizeModelDAO genPrizeModelDAO;
    @Resource
    private TecentTimeDAO tecentTimeDAO;
    @Resource(name="qianDwdGenPrize")
    private BaseGenPrize qianDwdGenPrize;
    @Resource(name="wuxingGenPrize")
    private BaseGenPrize wuxingGenPrize;
    @Resource(name="geBdDwdGenPrize")
    private BaseGenPrize geBdDwdGenPrize;

    @Resource(name="geBdDxDwdGenPrize")
    private BaseGenPrize geBdDxDwdGenPrize;


    @Resource(name = "wanDwdGenPrize3")
    protected BaseGenPrize wanDwdGenPrize3;
    @Resource(name="wanDwdGenPrize2")
    private BaseGenPrize wanDwdGenPrize2;




    Logger log = Logger.getLogger(TcffcGenNumsService.class);

    private TCFFCPRIZE genPrize = null;
    private List<TCFFCPRIZE> genPrizeList = null;
    String genStr = "";

    public void checkStrategy(List<BaseGenPrize> list) {

    }

    public Map<String, Object> noticeGenNumsService(TCFFCPRIZE  curPrize) {
        this.updateCurPrize(curPrize);
        Date nextMin = DateUtils.addMinutes(1, curPrize.getTime());
        updateCurNO(nextMin);

        Map<String, Object> result = new HashMap<>();
        //result.putAll(wuxingGenPrize.run(curPrize));
        //result.putAll(wanDwdGenPrize2.run(curPrize));
       // result.putAll(wanDwdGenPrize3.run(curPrize));
        //result.putAll(qianDwdGenPrize.run(curPrize));
       // result.putAll(geBdDwdGenPrize.run(curPrize));
       // result.putAll(geBdDxDwdGenPrize.run(curPrize));
        //result.putAll(geBdjcDwdGenPrize.run(curPrize));
        //result.putAll(qianBdDxDwdGenPrize.run(curPrize));
//        result.putAll(housanBdGenPrize.run(curPrize));
//        result.putAll(wanDwdGenPrize.run(curPrize));
//        result.putAll(wuxingBdwGenPrize.run(curPrize));
        //result.putAll(houSiBdwBodongGenPrize.run(curPrize));
        //result.putAll(geBdDwdGenPrize.run(curPrize));
        //result.putAll(baiBdDwdGenPrize.run(curPrize));
        //result.putAll(wuxingGenPrize.run(curPrize));
        //result.putAll(zhongsanGenPrize.run(curPrize));
        //result.putAll(zongheGenPrize.run(curPrize));
        //result.putAll(houSiGenPrize.run(curPrize));
        //result.putAll(housanGenPrize.run(curPrize));
        //result.putAll(qh4GenPrize.run(curPrize));
        //result.putAll(qh4GenwuPrize.run(curPrize));

//        StringBuffer sb = new StringBuffer();
//        int wan =((curPrize.getAdjustNum()%100000)/10000)%2==0?0:1;
//        int qian =((curPrize.getAdjustNum()%10000)/1000)%2==0?0:1;
//        int bai =((curPrize.getAdjustNum()%1000)/100)%2==0?0:1;
//        int shi =((curPrize.getAdjustNum()%100)/10)%2==0?0:1;
//        int ge =(curPrize.getAdjustNum()%10)%2==0?0:1;
//        sb.append("全波动：" +  qian + bai + shi + ge + "");
        //log.info(sb.toString());
        return result;
    }
    //更新开奖数据
    private void updateCurPrize(TCFFCPRIZE  curPrize) {
        Date nextMin = DateUtils.addMinutes(1, curPrize.getTime());
        CurNoModel curNOModel = new CurNoModel();
        curNOModel.setLotteryCode("TCFFC");
        String nextNO = TcffcPrizeConverter.genNofromTime(nextMin);
        curNOModel.setNextNo(nextNO);
        curNOModel.setCurNo(curPrize.getNo());
        curNOModel.setPrize(curPrize.getPrize());
        CurNoModelCondition curNOModelCondition = new CurNoModelCondition();
        curNOModelCondition.createCriteria().andLotteryCodeEqualTo("TCFFC");
        int cnt = curNOModelDAO.countByCondition(curNOModelCondition);
        if (cnt > 0) {
            curNOModelDAO.updateByConditionSelective(curNOModel, curNOModelCondition);
        } else {
            curNOModelDAO.insert(curNOModel);
        }
    }
    public Map<String, Boolean> generateNextNums2(TCFFCPRIZE  curPrize) {
        Map<String, Boolean> winResult = new HashMap<>();
        String prePath = BaseConstants.OUTPUT_PATH + File.separator ;
                //前2
        File file = new File(prePath+ "qian2AllFile.txt");
        File file2 = new File(prePath+ "qian2File.txt");

        //中3定位胆
        File zhong3File = new File(prePath+ "zhong3File.txt");
        File zhong3AllFile = new File(prePath+ "zhong3AllFile.txt");

        //前3定位胆
        File qian3File = new File(prePath+ "qian3File.txt");
        File qian3AllFile = new File(prePath+ "qian3AllFile.txt");

        //千位定位胆
        File qianFile = new File(prePath+ "qianFile.txt");
        File qianAllFile = new File(prePath+ "qianAllFile.txt");

        //五星
        File wuXingFile = new File(prePath+ "wuXingFile.txt");
        File wuXingAllFile = new File(prePath+ "wuXingAllFile.txt");

        try {
            boolean isQian3Prized = false;
            boolean isZhong3Prized = false;
            boolean isQian2Prized = false;
            boolean isQianPrized = false;
            boolean iaWuXingPrized = false;
            boolean iaWuXingTstPrized = false;


            if (genPrize != null || genPrizeList != null ) {
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
                if (Math.abs( curPrize.getAdjustNum() - genPrize.getAdjustNum() ) <= 25000) {
                    iaWuXingTstPrized = true;
                }
                if (null != genPrizeList && genPrizeList.size() > 0) {
                    for (TCFFCPRIZE item : genPrizeList) {
                        String prize = item.getPrize();
                        if (StringUtils.isNotEmpty(prize) && prize.equals(curPrize.getPrize())) {
                            iaWuXingPrized = true;
                        }
                    }
                }
            }
            winResult.put("isQian2Prized", isQian2Prized);
            winResult.put("isQianPrized", isQianPrized);
            winResult.put("isQian3Prized", isQian3Prized);
            winResult.put("isZhong3Prized", isZhong3Prized);
            winResult.put("iaWuXingPrized", iaWuXingPrized);
            //TCFFCPRIZE conPrize = this.calGenPrizeByRate(curPrize);
            TCFFCPRIZE conPrize = this.calGenPrizeByRateNum(curPrize, 4);

            String adjustStr = "实际：" + curPrize.getAdjustNum()  +(iaWuXingTstPrized?"中":"挂") + "\r\n预测" + conPrize.getNo() + " : "  + conPrize.getAdjustNum();
            try {
                FileUtils.writeStringToFile(new File(BaseConstants.OUTPUT_PATH + File.separator + "adjust.txt"), adjustStr, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //this.calGenPrizeBySort();
            Date nextMin = DateUtils.addMinutes(1, curPrize.getTime());
            //String outPutStr ="实际调整值:" + curPrize.getAdjustNum() + "\r\n" + "第" + curPrize.getNo() + "期在线人数为:" + curPrize.getOnlineNum() +  ",预测第" + TcffcPrizeConverter.genNofromTime(nextMin) + "期的调整值为:" + avgAdjustNum.intValue() ;

            //前2  8*8
            String qian2GenStr = " zhuan(" + LotteryUtil.genPyPost4NumStr(conPrize.getWan()) + "*" + LotteryUtil.genPyPost4NumStr(conPrize.getQian()) + ")zhuan ";
            String result = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(isQian2Prized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + conPrize.getPrize() + qian2GenStr;
            FileUtils.writeStringToFile(file, result, true);
            FileUtils.writeStringToFile(file2, result, false);

            String qianGenStr = " zhuan(" + LotteryUtil.genPy3NumStr(conPrize.getQian()) + ")zhuan ";
            String qianStrResult = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(isQianPrized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + conPrize.getPrize() + qianGenStr;
            FileUtils.writeStringToFile(qianAllFile, qianStrResult, true);
            FileUtils.writeStringToFile(qianFile, qianStrResult, false);

            //中3 8*9*9
            String zhong3GenStr = " zhuan(" + LotteryUtil.genPyPost4NumStr(conPrize.getQian()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getBai()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getShi())  + ")zhuan ";
            String zhong3StrResult = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(isZhong3Prized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + conPrize.getPrize() + zhong3GenStr;
            FileUtils.writeStringToFile(zhong3AllFile, zhong3StrResult, true);
            FileUtils.writeStringToFile(zhong3File, zhong3StrResult, false);

            //前3 9*8*9
            String qian3GenStr = " zhuan(" + LotteryUtil.genPy4NumStr(conPrize.getWan()) + "*" + LotteryUtil.genPyPost4NumStr(conPrize.getQian()) + "*" + LotteryUtil.genPy4NumStr(conPrize.getBai())  + ")zhuan ";
            String qian3StrResult = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(isQian3Prized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" + conPrize.getPrize() + qian3GenStr;
            FileUtils.writeStringToFile(qian3AllFile, qian3StrResult, true);
            FileUtils.writeStringToFile(qian3File, qian3StrResult, false);

            //五星根据计算的波动值前后加减25000，算出五星号码
            List<TCFFCPRIZE> tcffcprizeList = genNextWuXingPrizeByGen(curPrize, conPrize);
            Collections.sort(tcffcprizeList);

            StringBuffer wuXingSb = new StringBuffer();
            if(null != tcffcprizeList && tcffcprizeList.size() > 0) {
                tcffcprizeList.stream().forEach(item -> {
                    wuXingSb.append(item.getPrize() + " ");
                });
            }
            String wuXingGenStr = " zhuan(" + wuXingSb.toString() + ")zhuan ";
            String wuxingStrResult = curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(iaWuXingTstPrized?"中":"挂") + "\r\n预测" + TcffcPrizeConverter.genNofromTime(nextMin) + ":" +  wuXingGenStr;
            FileUtils.writeStringToFile(wuXingAllFile, wuxingStrResult, true);
            FileUtils.writeStringToFile(wuXingFile, wuXingSb.toString(), false);
            System.out.println(curPrize.getNo() + "实际：" + curPrize.getPrize() + " "+(iaWuXingTstPrized?"中":"挂"));

            updateCurNO(nextMin);
            //updateGenPrizeResult(conPrize, curPrize);

            genPrize = conPrize;
            genPrizeList = tcffcprizeList;
        } catch (Exception e) {
            log.error("generateNextNums error", e);
        }
        return winResult;
    }
    //根据当前期生成下期五星计划
    private List<TCFFCPRIZE> genNextWuXingPrizeByGen(TCFFCPRIZE conPrize, TCFFCPRIZE genPrize) {
        List<TCFFCPRIZE> result = null;
        Date nextMin = DateUtils.addMinutes(-1, conPrize.getTime());

        int startPost = genPrize.getAdjustNum()-25000;
        int endPost = genPrize.getAdjustNum() + 25000;
        result = this.createPrizeNumList2(startPost, endPost, nextMin);
        return result;

    }
    //根据当前期生成下期五星计划
    private List<TCFFCPRIZE> genNextWuXingPrize(TCFFCPRIZE conPrize) {
        List<TCFFCPRIZE> result = null;
        Date nextMin = DateUtils.addMinutes(-1, conPrize.getTime());

        TecentTimeCondition tecentTimeDOCondition = new TecentTimeCondition();
        tecentTimeDOCondition.createCriteria().andStartTimeLessThanOrEqualTo(nextMin).andEndTimeGreaterThan(nextMin);

        List<TecentTime> tecentTimeDOList = tecentTimeDAO.selectByCondition(tecentTimeDOCondition);
        TecentTime tecentTime = null;
        if (null != tecentTimeDOList && tecentTimeDOList.size() > 0) {
            tecentTime = tecentTimeDOList.get(0);
        } else {
            log.error("获取" + nextMin + "调整数据异常");
            return result;
        }
        int start = tecentTime.getStart();
        int end = tecentTime.getEnd();
        int curPostNum = conPrize.getOnlineNum() / 10000 * 10000;

        int startPost = start + curPostNum;
        int endPost = end + curPostNum;

        result = this.createPrizeNumList2(startPost, endPost, nextMin);

        return result;

    }

    static String[] excludes = new String[] {"0000", "1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999"};
    //生成投注号码
    public static List<TCFFCPRIZE> createPrizeNumList2(int preStart, int preEnd, Date time) {
        List<TCFFCPRIZE> toPrizeNumList = new ArrayList<>();

        for(int i = 0; i<9999; i++) {
            for(int j = preStart; j <= preEnd; j= j+10000) {
                int num = i + j;
                TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
                tcffcprize.setTime(time);
                tcffcprize.setOnlineNum(num);
                tcffcprize.setAdjustNum(j);
                tcffcprize.setLotteryCode("TCFFC");
                TCFFCPRIZE convertResult = TcffcPrizeConverter.convert2TCFFCPrize(tcffcprize);
                toPrizeNumList.add(convertResult);
            }
        }
        return toPrizeNumList;
    }
        public static List<String> createPrizeNumList(int preStart, int preEnd) {
        List<String> toPrizeNumList = new ArrayList<>();
        for(int i = 0; i<9999; i++) {
            String postfourStr = ("000" + i).substring(("000" + i).length() -4);
            for(int j = preStart; j <= preEnd; j++) {
                String imageNextNum = j + "" + postfourStr;
                String last4 = imageNextNum.substring(imageNextNum.length() -4);

                char[] chars = imageNextNum.toCharArray();
                int sum = 0;
                for(int k=0; k <chars.length; k++) {
                    int src = Integer.valueOf("" + chars[k]);
                    sum = sum + src;
                }
                String toPrize = ("" + sum).substring(("" + sum).length() - 1) + last4;
                boolean isNotExlude = true;
                for(String item : excludes) {
                    if (toPrize.startsWith(item) || toPrize.endsWith(item)) {
                        isNotExlude = false;
                        break;
                    }
                }
                if (isNotExlude) {
                    toPrizeNumList.add(toPrize);
                }
            }
        }
        return toPrizeNumList;
    }

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
        return  conPrize;
    }
    public void updateCurNO(Date date) {
        CurNoModel curNOModel = new CurNoModel();
        curNOModel.setLotteryCode("TCFFC");
        String nextNO = TcffcPrizeConverter.genNofromTime(date);
        curNOModel.setNextNo(nextNO);
        CurNoModelCondition curNOModelCondition = new CurNoModelCondition();
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
        genPrizeModel.setType(BaseConstants.WF_TYPE_DWD_GE_JC);

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
        //log.info("过滤前输入为：" + input.toString());
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
        //log.info("过滤后输出为：" + input.toString());
        //log.info("------------");

        BigDecimal result = maxTotal.divide(BigDecimal.valueOf(items.size()), 12, BigDecimal.ROUND_FLOOR);
        return  result;
    }

    private BigDecimal calGenPrizeBySort(List<BigDecimal> items) {

        if(null == items || items.size() == 0) {
            return null;
        }
        int size = items.size();
        this.sortOnlineRateList(items);
        //delete  half
        for(int i=0; i< (size/4);i++) {
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
       // Date next2Min = DateUtils.addMinutes(2, curTime);
        Date preMin = DateUtils.addMinutes(-1, curTime);


        //当天过去三分钟的开奖数据
        List<Date> dateList = new ArrayList();

        for(int i=1;i<= num;i++) {
            dateList.add(DateUtils.addMinutes(-i, curTime));
            dateList.add(DateUtils.addDate(-i, curTime));
            dateList.add(DateUtils.addDate(-i, nextMin));
            //dateList.add(DateUtils.addDate(-i, next2Min));
            dateList.add(DateUtils.addDate(-i, preMin));
        }
        dateList.add(curTime);
        List<String> noList = new ArrayList<>();

        for(Date dateItem : dateList) {
            noList.add(TcffcPrizeConverter.genNofromTime(dateItem));
        }

        Collections.sort(noList);
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
        return  conPrize;
    }
}
