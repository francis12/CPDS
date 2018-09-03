package com.ds.zxm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.model.TcffcPrizeConverter;
import com.ds.zxm.strategy.BaseStrategy;
import com.ds.zxm.strategy.DwdQianStrategy;
import com.ds.zxm.strategy.QianSiStrategy;
import com.ds.zxm.strategy.SanXinHotStrategy;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.HttpUtil;
import com.ds.zxm.util.LotteryUtil;
import com.ds.zxm.vo.BetingDetail;
import com.ds.zxm.vo.TradeSchedule;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class LotteryStrategyService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private TcffcGenNumsService tcffcGenNumsService;
    //@Resource(name="sanXinHotStrategy")
    @Resource(name="geQianStrategy")
    private BaseStrategy baseStrategy;
    public static Executor executor = Executors.newFixedThreadPool(1);
    Logger log = Logger.getLogger(LotteryStrategyService.class);

    private static int c1 = 0;
    private static int c2 = 0;

    public static void main(String[] args) {
        //new LotteryPrizeScheduleService().fetchTcffcPrizeFrom77Org();
    }

    static Map<String, TradeSchedule> scheduleMap = new HashMap<>();
    static {
        //定位胆7码6期倍投
        TradeSchedule tradeSchedule1 = new TradeSchedule(1, 1,2,1);
        TradeSchedule tradeSchedule2 = new TradeSchedule(2, 1,3,4);
        TradeSchedule tradeSchedule3 = new TradeSchedule(3, 1,4,17);
        TradeSchedule tradeSchedule4 = new TradeSchedule(4, 1,5,74);
        TradeSchedule tradeSchedule5 = new TradeSchedule(5, 1,6,321);
        TradeSchedule tradeSchedule6 = new TradeSchedule(6, 1,1,1391);

        scheduleMap.put("1", tradeSchedule1);
        scheduleMap.put("2", tradeSchedule2);
        scheduleMap.put("3", tradeSchedule3);
        scheduleMap.put("4", tradeSchedule4);
        scheduleMap.put("5", tradeSchedule5);
        scheduleMap.put("6", tradeSchedule6);

        //中3 7期倍投
        /*TradeSchedule tradeSchedule1 = new TradeSchedule(1, 1,2,1);
        TradeSchedule tradeSchedule2 = new TradeSchedule(2, 1,3,3);
        TradeSchedule tradeSchedule3 = new TradeSchedule(3, 1,4,12);
        TradeSchedule tradeSchedule4 = new TradeSchedule(4, 1,5,45);
        TradeSchedule tradeSchedule5 = new TradeSchedule(5, 1,6,170);
        TradeSchedule tradeSchedule6 = new TradeSchedule(6, 1,7,641);
        TradeSchedule tradeSchedule7 = new TradeSchedule(6, 1,1,2417);


        scheduleMap.put("1", tradeSchedule1);
        scheduleMap.put("2", tradeSchedule2);
        scheduleMap.put("3", tradeSchedule3);
        scheduleMap.put("4", tradeSchedule4);
        scheduleMap.put("5", tradeSchedule5);
        scheduleMap.put("6", tradeSchedule6);
        scheduleMap.put("7", tradeSchedule7);*/

        //后4 7期倍投
        /*TradeSchedule tradeSchedule1 = new TradeSchedule(1, 1,2,1);
        TradeSchedule tradeSchedule2 = new TradeSchedule(2, 1,3,3);
        TradeSchedule tradeSchedule3 = new TradeSchedule(3, 1,4,12);
        TradeSchedule tradeSchedule4 = new TradeSchedule(4, 1,5,46);
        TradeSchedule tradeSchedule5 = new TradeSchedule(5, 1,6,175);
        TradeSchedule tradeSchedule6 = new TradeSchedule(6, 1,7,669);
        TradeSchedule tradeSchedule7 = new TradeSchedule(6, 1,1,2556);*/

//        //后3 666注7期倍投
//        TradeSchedule tradeSchedule1 = new TradeSchedule(1, 1,2,1);
//        TradeSchedule tradeSchedule2 = new TradeSchedule(2, 1,3,4);
//        TradeSchedule tradeSchedule3 = new TradeSchedule(3, 1,4,14);
//        TradeSchedule tradeSchedule4 = new TradeSchedule(4, 1,5,44);
//        TradeSchedule tradeSchedule5 = new TradeSchedule(5, 1,6,139);
//        TradeSchedule tradeSchedule6 = new TradeSchedule(6, 1,7,437);
//        TradeSchedule tradeSchedule7 = new TradeSchedule(6, 1,1,1371);
//
//        scheduleMap.put("1", tradeSchedule1);
//        scheduleMap.put("2", tradeSchedule2);
//        scheduleMap.put("3", tradeSchedule3);
//        scheduleMap.put("4", tradeSchedule4);
//        scheduleMap.put("5", tradeSchedule5);
//        scheduleMap.put("6", tradeSchedule6);
//        scheduleMap.put("7", tradeSchedule7);
        //定位胆中跟挂停
//        TradeSchedule tradeSchedule1 = new TradeSchedule(1, 2,1,0);
//        TradeSchedule tradeSchedule2 = new TradeSchedule(2, 1,3,1);
//        TradeSchedule tradeSchedule3 = new TradeSchedule(3, 4,3,0);
//        TradeSchedule tradeSchedule4 = new TradeSchedule(4, 1,5,4);
//        TradeSchedule tradeSchedule5 = new TradeSchedule(5, 6,5,0);
//        TradeSchedule tradeSchedule6 = new TradeSchedule(6, 1,1,19);
//
//        scheduleMap.put("1", tradeSchedule1);
//        scheduleMap.put("2", tradeSchedule2);
//        scheduleMap.put("3", tradeSchedule3);
//        scheduleMap.put("4", tradeSchedule4);
//        scheduleMap.put("5", tradeSchedule5);
//        scheduleMap.put("6", tradeSchedule6);

        /*TradeSchedule tradeSchedule1 = new TradeSchedule(1, 1,1,1);
        scheduleMap.put("1", tradeSchedule1);*/

        //两期测试
        //TradeSchedule tradeSchedule1 = new TradeSchedule(1, 1,1,1);

        //scheduleMap.put("1", tradeSchedule1);
    }

    /*static {
        //3轮推波倍投
        TradeSchedule tradeSchedule1 = new TradeSchedule(1, 1,4,100);
        TradeSchedule tradeSchedule2 = new TradeSchedule(2, 3,4,139);
        TradeSchedule tradeSchedule3 = new TradeSchedule(3, 1,4,193);

        TradeSchedule tradeSchedule4 = new TradeSchedule(4, 5,7,200);
        TradeSchedule tradeSchedule5 = new TradeSchedule(5, 6,7,277);
        TradeSchedule tradeSchedule6 = new TradeSchedule(6, 1,7,384);

        TradeSchedule tradeSchedule7 = new TradeSchedule(7, 8,10,400);
        TradeSchedule tradeSchedule8 = new TradeSchedule(8, 9,10,554);
        TradeSchedule tradeSchedule9 = new TradeSchedule(9, 1,10,768);

        TradeSchedule tradeSchedule10 = new TradeSchedule(10, 11,13,1000);
        TradeSchedule tradeSchedule11 = new TradeSchedule(11, 12,13,1386);
        TradeSchedule tradeSchedule12 = new TradeSchedule(12, 1,13,1921);

        TradeSchedule tradeSchedule13 = new TradeSchedule(13, 14,16,2300);
        TradeSchedule tradeSchedule14 = new TradeSchedule(14, 15,16,3187);
        TradeSchedule tradeSchedule15 = new TradeSchedule(15, 1,16,4416);

        TradeSchedule tradeSchedule16 = new TradeSchedule(16, 17,19,5200);
        TradeSchedule tradeSchedule17 = new TradeSchedule(17, 18,19,7206);
        TradeSchedule tradeSchedule18 = new TradeSchedule(18, 1,19,9985);

        TradeSchedule tradeSchedule19 = new TradeSchedule(19, 20,22,12000);
        TradeSchedule tradeSchedule20 = new TradeSchedule(20, 21,22,16629);
        TradeSchedule tradeSchedule21 = new TradeSchedule(21, 1,22,23043);

        TradeSchedule tradeSchedule22 = new TradeSchedule(22, 23,23,27500);
        TradeSchedule tradeSchedule23 = new TradeSchedule(23, 24,24,38107);
        TradeSchedule tradeSchedule24 = new TradeSchedule(24, 1,1,52805);

        TradeSchedule tradeSchedule25 = new TradeSchedule(25, 26,26,63200);
        TradeSchedule tradeSchedule26 = new TradeSchedule(26, 27,27,87577);
        TradeSchedule tradeSchedule27 = new TradeSchedule(27, 1,1,121357);


        scheduleMap.put("1", tradeSchedule1);
        scheduleMap.put("2", tradeSchedule2);
        scheduleMap.put("3", tradeSchedule3);
        scheduleMap.put("4", tradeSchedule4);
        scheduleMap.put("5", tradeSchedule5);
        scheduleMap.put("6", tradeSchedule6);
        scheduleMap.put("7", tradeSchedule7);
        scheduleMap.put("8", tradeSchedule8);
        scheduleMap.put("9", tradeSchedule9);
        scheduleMap.put("10", tradeSchedule10);
        scheduleMap.put("11", tradeSchedule11);
        scheduleMap.put("12", tradeSchedule12);
        scheduleMap.put("13", tradeSchedule13);
        scheduleMap.put("14", tradeSchedule14);
        scheduleMap.put("15", tradeSchedule15);
        scheduleMap.put("16", tradeSchedule16);
        scheduleMap.put("17", tradeSchedule17);
        scheduleMap.put("18", tradeSchedule18);
        scheduleMap.put("19", tradeSchedule19);
        scheduleMap.put("20", tradeSchedule20);
        scheduleMap.put("21", tradeSchedule21);
        scheduleMap.put("22", tradeSchedule22);
        scheduleMap.put("23", tradeSchedule23);
        scheduleMap.put("24", tradeSchedule24);
        scheduleMap.put("25", tradeSchedule25);
        scheduleMap.put("26", tradeSchedule26);
        scheduleMap.put("27", tradeSchedule27);
    }*/

    public final String format="yyyy-MM-dd HH:mm:ss";
    public Map<String ,Integer> checkLotteryStrategy(String start, String end, String lotteryCode, String type, Integer rate) {
        BetingDetail betResult = new BetingDetail();

        Map<String ,Integer> loseCnt = new HashMap<>();
        Map<String ,Integer> winCnt = new HashMap<>();

        int totalCheckCnt = 0;
        int winCheckCnt = 0;
        TradeSchedule curSchedule = scheduleMap.get("1");
        try {
            //连挂计数
            StringBuffer zgSb = new StringBuffer();
            //连中计数
            StringBuffer lzSb = new StringBuffer();

            //连挂
            StringBuffer lgSb = new StringBuffer();
            StringBuffer zgxt = new StringBuffer();
            Date startTime = DateUtils.String2Date(start, format);
            Date endTime = DateUtils.String2Date(end, format);
            /*BigDecimal baseAmt = new BigDecimal("0.856");
            BigDecimal baseRate = new BigDecimal("1.139");*/
            /*BigDecimal baseAmt = new BigDecimal("0.648");
            BigDecimal baseRate = new BigDecimal("1.497");*/
            BigDecimal baseAmt = new BigDecimal("0.7");
            BigDecimal baseRate = new BigDecimal("1.397");
            BigDecimal curAmt = BigDecimal.ZERO;
            while (startTime.compareTo(endTime) <= 0) {
                totalCheckCnt++;
                //boolean isMatch2 = runOneNoStrategy(startTime, baseAmt, baseRate);

                boolean isMatch = false;
                String no="";
                Object calResult = null;
                TCFFCPRIZE curPrize = null;
                try {
                    calResult = baseStrategy.calBetNum(startTime);
                    no = TcffcPrizeConverter.genNofromTime(startTime);
                    TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
                    condition.createCriteria().andNoEqualTo(no);
                    List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
                    isMatch = true;
                    if (prizeList != null && prizeList.size() > 0) {
                        curPrize = prizeList.get(0);
                        isMatch = baseStrategy.isWin(calResult, prizeList.get(0));
                    }
                    zgxt.append((isMatch?"中":"挂"));
                    if(baseStrategy instanceof SanXinHotStrategy) {
                        Set<String> lastHotResult = (Set<String>) calResult;
                        baseAmt = BigDecimal.valueOf(lastHotResult.size()).divide(new BigDecimal("1000"), 8 ,BigDecimal.ROUND_FLOOR);
                        baseRate = new BigDecimal("0.94").divide(baseAmt, 8, BigDecimal.ROUND_FLOOR);
                    }
                    if(baseStrategy instanceof QianSiStrategy) {
                        if(prizeList.get(0).getAdjustNum() < 50000 && prizeList.get(0).getAdjustNum() > -50000) {
                            String[] numArr  = (String[]) calResult;
                            baseAmt = BigDecimal.valueOf(numArr.length).divide(new BigDecimal("10000"), 8 ,BigDecimal.ROUND_FLOOR);
                            baseRate = new BigDecimal("0.94").divide(baseAmt, 8, BigDecimal.ROUND_FLOOR);
                            FileUtils.writeStringToFile(new File("回测结果.txt"), "第"  + prizeList.get(0).getNo() + "投注" + numArr.length + "注:" + (isMatch?"中":"挂") + "\r\n", true);

                            log.info("第"  + prizeList.get(0).getNo() + "投注" + numArr.length + "注:" + (isMatch?"中":"挂"));
                        } else {
                            startTime = DateUtils.addMinutes(1, startTime);
                            totalCheckCnt--;
                            continue;
                        }
                    }

                    curAmt = baseAmt.multiply(BigDecimal.valueOf(curSchedule.getMultiple()));
                    betResult.setBetSum(betResult.getBetSum().add(curAmt));
                } catch (Exception e) {
                    log.error(e);
                }


                //中
                if(isMatch){
                    lzSb.append("中");
                    int lgCnt = zgSb.length();
                    if(lgCnt >= 6) {
                        log.info(startTime + "遗漏" + lgCnt);
                    }
                    betResult.setCurProfit(betResult.getCurProfit().add(curAmt.multiply(baseRate.subtract(new BigDecimal("1")))));
                    winCheckCnt++;
                    lgSb.append(zgSb.length());
                    if (betResult.getMaxLoose() < lgCnt) {
                        betResult.setMaxLoose(lgCnt);
                    }
                    if(loseCnt.get("" + lgCnt ) == null) {
                        loseCnt.put("" + lgCnt, 1);
                    } else {
                        loseCnt.put(""+lgCnt, loseCnt.get("" + lgCnt) + 1);
                    }
                    if(lgCnt >= 1) {
                        zgSb.delete(0, zgSb.length());
                    }
                }else{
                    betResult.setCurProfit(betResult.getCurProfit().subtract(curAmt));
                    zgSb.append("挂");

                    int lzCnt = lzSb.length();
                    if(winCnt.get("" + lzCnt ) == null) {
                        winCnt.put("" + lzCnt, 1);
                    } else {
                        winCnt.put(""+lzCnt, winCnt.get("" + lzCnt) + 1);
                    }
                    if(lzCnt >= 1) {
                        lzSb.delete(0, lzSb.length());
                    }
                }

                //设置最大盈利最大亏损
                if(betResult.getMaxProfit().compareTo(betResult.getCurProfit()) < 0) {
                    betResult.setMaxProfit(betResult.getCurProfit());
                }
                if(betResult.getMinProfit().compareTo(betResult.getCurProfit()) > 0) {
                    betResult.setMinProfit(betResult.getCurProfit());
                }
                log.info(String.format("第%s期开%s投注%s，(%s)当前轮次：%s,倍数：%s,总盈亏:%s", no, curPrize.getPrize(),(calResult == null? "":calResult.toString()), (isMatch?"中":"挂"),
                        String.valueOf(curSchedule.getNo()), String.valueOf(curSchedule.getMultiple()),String.valueOf(betResult.getCurProfit()) ));
                curSchedule = scheduleMap.get(isMatch?("" + curSchedule.getWinNo()) : ("" + curSchedule.getLoseNo()));
                startTime = DateUtils.addMinutes(1, startTime);
            }
            log.info("中挂形态：" + zgxt.toString());
            log.info("回测结果:总验证期数：" + totalCheckCnt + ",预测准确率：" + BigDecimal.valueOf(winCheckCnt).divide(BigDecimal.valueOf(totalCheckCnt), 4, RoundingMode.FLOOR)
                    + "总投注:" + betResult.getBetSum() + "最终盈利：" + betResult.getCurProfit() + "最大亏损：" + betResult.getMinProfit() + "最大盈利:" + betResult.getMaxProfit());
        } catch (Exception e) {
            log.error("checkLotteryStrategy", e);
        }
        return loseCnt;
    }

    //校验开奖数据
    private boolean runOneNoStrategy(Date time, double baseAmt, double baseRate) {
        try {
            Object calResult = baseStrategy.calBetNum(time);

            if(baseStrategy instanceof SanXinHotStrategy) {
                Set<String> lastHotResult = (Set<String>) calResult;
                baseAmt = (double) lastHotResult.size() / 1000;
                baseRate = (double) (0.94/baseAmt);
            }

            String no = TcffcPrizeConverter.genNofromTime(time);
            TCFFCPRIZECondition condition = new TCFFCPRIZECondition();
            condition.createCriteria().andNoEqualTo(no);
            List<TCFFCPRIZE> prizeList = tcffcprizedao.selectByCondition(condition);
            return baseStrategy.isWin(calResult, prizeList.get(0));
        } catch (Exception e) {
            log.error("runOneNoStrategy error" + time);
        }
        return true;
    }

    private void insertOnUnexist(TCFFCPRIZE curPrize) {
        TCFFCPRIZECondition conditon = new TCFFCPRIZECondition();
        conditon.createCriteria().andNoEqualTo(curPrize.getNo());
        int cnt = tcffcprizedao.countByCondition(conditon);
        if (cnt <= 0) {
            tcffcprizedao.insert(curPrize);
        }
    }
    private Date getCurTime() {
        Date now  = new Date();
        try {
            now = DateUtils.getWebsiteDatetime("http://www.baidu.com");
        } catch (Exception e) {
            log.error("get online time error!");
        }
        return now;
    }

    /**
     *  取最近lastcnt期后3的热号
     * @param time  :time之前的记录
     * @param lastCnt ： 统计最近lastcnt条记录
     * @param numCnt ：统计的结果集数目
     * @return
     */
    public List<String> queryLatestHotNums(Date time, int lastCnt, int numCnt) {
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Date endTime = DateUtils.addMinutes(-6, time);
        Date startTime = DateUtils.addMinutes(-lastCnt, endTime);
        tcffcprizeCondition.createCriteria().andTimeBetween(startTime, endTime);
        List<TCFFCPRIZE> tcffcprizeList = tcffcprizedao.selectByCondition(tcffcprizeCondition);
        Collections.sort(tcffcprizeList, new Comparator<TCFFCPRIZE>() {
            @Override
            public int compare(TCFFCPRIZE o1, TCFFCPRIZE o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        Map<String, Integer> numCntMap = new HashMap<>();
        tcffcprizeList.stream().forEach( item-> {
            String hou3 = "" + item.getBai() + item.getShi() + item.getGe();
            if(numCntMap.get(hou3) == null) {
                numCntMap.put(hou3, 1);
            } else {
                numCntMap.put(hou3, numCntMap.get(hou3)+1);
            }
        });

        Map<String, Integer> sortedMap = LotteryUtil.sortMapByValue(numCntMap,"1");
        List<String> list =  new ArrayList<>(sortedMap.keySet());
        if(list != null && list.size() >=800) {
            list = list.subList(0, 800);
        }
        return list;
    }
}
