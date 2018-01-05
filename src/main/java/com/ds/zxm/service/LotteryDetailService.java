package com.ds.zxm.service;

import com.ds.zxm.mapper.LotteryDetailMapper;
import com.ds.zxm.mapper.StrategyDAO;
import com.ds.zxm.mapper.StrategyDetailDAO;
import com.ds.zxm.mapper.TecentOnlineDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.ArraysUtil;
import com.ds.zxm.util.DateUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Service
public class LotteryDetailService {

    @Autowired
    private LotteryDetailMapper lotteryDetailMapper;
    @Autowired
    private TecentOnlineDAO tecentOnlineDAO;
    @Autowired
    private StrategyDAO strategyDAO;
    @Autowired
    private StrategyDetailDAO strategyDetailDAO;

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LotteryDetailService.class);
    static List<String> prizes;

    static {
        prizes = new ArrayList<>();
        prizes.add("0");
        prizes.add("3");
        prizes.add("6");
        prizes.add("9");
    }


    public void batchGdwDetails(String time) {

        TecentOnlineDOCondition tecentOnlineDOCondition = new TecentOnlineDOCondition();
        tecentOnlineDOCondition.setOrderByClause(" time asc");
        tecentOnlineDOCondition.createCriteria().andTimeLike(time + "%");
        List<TecentOnlineDO> tecentOnlineDOList = tecentOnlineDAO.selectByCondition(tecentOnlineDOCondition);
        for (int i = 2; i < tecentOnlineDOList.size(); i++) {


            Long pre2Num = tecentOnlineDOList.get(i - 2).getOnlineNum();
            Long preNum = tecentOnlineDOList.get(i - 1).getOnlineNum();

            //
            String num = "";
            Long preNum43 = Long.valueOf((preNum + "").substring((preNum + "").length() - 4, (preNum + "").length() - 3));
            Long pre2Num43 = Long.valueOf((pre2Num + "").substring((pre2Num + "").length() - 4, (pre2Num + "").length() - 3));

            Long num4 = Long.valueOf((preNum + "").substring((preNum + "").length() - 4, (preNum + "").length() - 3));

            LotteryDetail lotteryDetail = new LotteryDetail();
            log.info(tecentOnlineDOList.get(i).getTime() + "finished");
            lotteryDetailMapper.insert(lotteryDetail);
        }
    }

    private boolean isPrized(LotteryDetail detail) {
        return false;
    }

    public Map<String, Object> testBatchLotteryDetails(String time) {

        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause(" alias_no asc");
        lotteryDetailCondition.createCriteria().andAliasNoLike(time + "%");
        List<LotteryDetail> list = lotteryDetailMapper.queryAllTestData(lotteryDetailCondition);

        /*for (LotteryDetail item : list) {
            if (isPrized(item)) {
                prizeList.add(item);
            }
        }*/
        Map<String, Integer> result = new HashMap<>();
        List<Integer> disList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            String curNo = list.get(i).getAliasNo();
            String preNo = list.get(i - 1).getAliasNo();

            int distance = Integer.valueOf(curNo.substring(curNo.length() - 4)) - Integer.valueOf(preNo.substring(preNo.length() - 4)) - 1;

            timeList.add(curNo);
            disList.add(distance);
            // result.put(curNo, distance);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xAxis", timeList);
        map.put("series", disList);
        return map;
    }

    public static Map<String, TradeSchedule> scheMap = null;
/*002 001 1
        003 001 2
        004 001 4
        005 001 8
        006 001 16
        007 001 31
        008 001 60
        009 001 116
        001 001 225
        011 001 436
        012 001 846
        001 001 1641*/
    static {
        scheMap = new HashMap<String, TradeSchedule>();
        TradeSchedule tradeSchedule1 = new TradeSchedule();
        tradeSchedule1.setWinNo(2);
        tradeSchedule1.setLoseNo(1);
        tradeSchedule1.setMultiple(1);
        scheMap.put("1", tradeSchedule1);

        TradeSchedule tradeSchedule2 = new TradeSchedule();
        tradeSchedule2.setWinNo(3);
        tradeSchedule2.setLoseNo(1);
        tradeSchedule2.setMultiple(2);
        scheMap.put("2", tradeSchedule2);

        TradeSchedule tradeSchedule3 = new TradeSchedule();
        tradeSchedule3.setWinNo(4);
        tradeSchedule3.setLoseNo(1);
        tradeSchedule3.setMultiple(4);
        scheMap.put("3", tradeSchedule3);

        TradeSchedule tradeSchedule4 = new TradeSchedule();
        tradeSchedule4.setWinNo(5);
        tradeSchedule4.setLoseNo(1);
        tradeSchedule4.setMultiple(8);
        scheMap.put("4", tradeSchedule4);

        TradeSchedule tradeSchedule5 = new TradeSchedule();
        tradeSchedule5.setWinNo(6);
        tradeSchedule5.setLoseNo(1);
        tradeSchedule5.setMultiple(16);
        scheMap.put("5", tradeSchedule5);

        TradeSchedule tradeSchedule6 = new TradeSchedule();
        tradeSchedule6.setWinNo(7);
        tradeSchedule6.setLoseNo(1);
        tradeSchedule6.setMultiple(31);
        scheMap.put("6", tradeSchedule6);

        TradeSchedule tradeSchedule7 = new TradeSchedule();
        tradeSchedule7.setWinNo(8);
        tradeSchedule7.setLoseNo(1);
        tradeSchedule7.setMultiple(60);
        scheMap.put("7", tradeSchedule7);

        TradeSchedule tradeSchedule8 = new TradeSchedule();
        tradeSchedule8.setWinNo(9);
        tradeSchedule8.setLoseNo(1);
        tradeSchedule8.setMultiple(116);
        scheMap.put("8", tradeSchedule8);

        TradeSchedule tradeSchedule9 = new TradeSchedule();
        tradeSchedule9.setWinNo(1);
        tradeSchedule9.setLoseNo(1);
        tradeSchedule9.setMultiple(225);
        scheMap.put("9", tradeSchedule9);
    }

    //个位匹配
    String[] nums = {"1", "4", "3", "8", "9"};
    public String runStrategy(String start, String end) {

        StrategyDO result = new StrategyDO();
        result.setStartNo(start);
        result.setEndNo(end);
        result.setLotteryCode("QQFFC");

        //批量验证数据
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause("alias_no asc ");
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andAliasNoBetween(start, end).andLotteryDateIsNotNull();
        List<LotteryDetail> list = lotteryDetailMapper.selectByCondition(lotteryDetailCondition);

        BigDecimal curProfit = BigDecimal.ZERO;
        BigDecimal totalThrow = BigDecimal.ZERO;
        //最大
        BigDecimal maxProfit = BigDecimal.ZERO;
        //最大
        BigDecimal minProfit = BigDecimal.ZERO;

        BigDecimal baseAmt = new BigDecimal("1");
        BigDecimal baseProfit = new BigDecimal("0.93");

        TradeSchedule curSchedule = null;
        curSchedule = scheMap.get("1");

        List<StrategyDetailDO> strategyDetailDOList = new ArrayList<>();

        StrategyDetailDO strategyDetailDO = new StrategyDetailDO();
        strategyDetailDO.setLotteryCode("QQFFC");

        for (LotteryDetail detail : list) {
            //tz
            BigDecimal tzAmt = baseAmt.multiply(new BigDecimal(curSchedule.getMultiple()));
            totalThrow = totalThrow.add(tzAmt);

            String num1 = detail.getNum1();
            int nextNo = 1;
            if (Arrays.asList(nums).contains(num1)) {
                curProfit = curProfit.add(tzAmt.multiply(baseProfit));
                nextNo = curSchedule.getWinNo();
                strategyDetailDO.setStatus("中");
            } else {
                curProfit = curProfit.subtract(tzAmt);
                nextNo = curSchedule.getLoseNo();
                strategyDetailDO.setStatus("挂");
            }
            maxProfit = maxProfit.compareTo(curProfit) > 0 ? maxProfit : curProfit;
            minProfit = minProfit.compareTo(curProfit) < 0 ? minProfit : curProfit;
            curSchedule = scheMap.get("" + nextNo);

            strategyDetailDO.setNo(detail.getAliasNo());
            strategyDetailDO.setAmt(tzAmt);
            strategyDetailDO.setCurProfit(curProfit);
            strategyDetailDOList.add(strategyDetailDO);
        }
        result.setCurProfit(curProfit);
        result.setMaxProfit(maxProfit);
        result.setMinProfit(minProfit);
        result.setTotalAmt(totalThrow);

        strategyDAO.insert(result);
        batchInsertStartegyDetail(strategyDetailDOList);
        System.out.println("curProfit:" + curProfit + ", maxProfit:" + maxProfit + ",minProfit:" + minProfit);
        return null;
    }

    private void batchInsertStartegyDetail(List<StrategyDetailDO> list) {
        List<List<StrategyDetailDO>> groupedList = ArraysUtil.groupListByQuantity(list, 1000);
        groupedList.forEach(
                item -> {
                    strategyDetailDAO.insertBatch(item);
                }
        );
    }

    //极限推波验证
    public void batchCheckData(String start, String end) throws ParseException {
        //个位匹配
        String[] nums = {"1", "4", "3", "8", "9"};
        //批量验证数据
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause("alias_no asc ");
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andAliasNoBetween(start, end).andLotteryDateIsNotNull().andNum1In(Arrays.asList(nums));
        List<LotteryDetail> list = lotteryDetailMapper.selectByCondition(lotteryDetailCondition);

        int maxLooseDistance = 0;
        int maxWinDistance = 0;
        int curWin = 1;
        Map<String, Integer> winDistance = new HashMap<>();

        Map<String, Integer> winCnt = new HashMap<>();
        Map<String, Integer> looseCnt = new HashMap<>();

        List<LotteryDetail> p9List = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            LotteryDetail current = list.get(i);
            LotteryDetail pre = list.get(i - 1);

            int distance = this.calQQffcDistance(current.getAliasNo(), pre.getAliasNo());
            winDistance.put(current.getAliasNo(), distance);
            if (null == looseCnt.get("" + distance)) {
                looseCnt.put("" + distance, 1);
            } else {
                looseCnt.put("" + distance, looseCnt.get("" + distance) + 1);
            }
            maxLooseDistance = distance > maxLooseDistance ? distance : maxLooseDistance;
            if (distance == 0) {
                curWin++;
                if (curWin == 9) {
                    p9List.add(current);
                }
            } else {
                if (null == winCnt.get("" + curWin)) {
                    winCnt.put("" + curWin, 1);
                } else {
                    winCnt.put("" + curWin, winCnt.get("" + curWin) + 1);
                }
                maxWinDistance = curWin > maxWinDistance ? curWin : maxWinDistance;

                curWin = 1;
            }
        }

        for (int i = 1; i < p9List.size(); i++) {
            LotteryDetail p9current = p9List.get(i);
            LotteryDetail p9pre = p9List.get(i - 1);

            int p9distance = this.calQQffcDistance(p9current.getAliasNo(), p9pre.getAliasNo());
            System.out.println(p9current.getAliasNo() + "/" + p9distance);
        }

        System.out.println(String.format("(%s--%s)总共中：%s,最大连挂:%s,最大连中:%s", start, end, list.size(), maxLooseDistance, maxWinDistance));

        /*for (Map.Entry<String, Integer> entry : winDistance.entrySet()) {
            System.out.print( entry.getValue() + ",");
        }
        System.out.println("------");*/
    }

    private int calQQffcDistance(String curAliasNo, String preAliasNo) throws ParseException {
        int dayDistance = DateUtils.calculateDaysBetween(curAliasNo.substring(0, 8), preAliasNo.substring(0, 8));
        int noDistance = Integer.valueOf(curAliasNo.substring(9, 13)) - Integer.valueOf(preAliasNo.substring(9, 13));

        return dayDistance * 1440 + noDistance - 1;
    }

    public void batchLotteryDetails(String time) {

        TecentOnlineDOCondition tecentOnlineDOCondition = new TecentOnlineDOCondition();
        tecentOnlineDOCondition.setOrderByClause(" time asc");
        tecentOnlineDOCondition.createCriteria().andTimeLike(time + "%");
        List<TecentOnlineDO> tecentOnlineDOList = tecentOnlineDAO.selectByCondition(tecentOnlineDOCondition);
        for (int i = 2; i < tecentOnlineDOList.size(); i++) {
            Long pre2Num = tecentOnlineDOList.get(i - 2).getOnlineNum();
            Long preNum = tecentOnlineDOList.get(i - 1).getOnlineNum();
            //
            String num = "";
            Long preNum43 = Long.valueOf((preNum + "").substring((preNum + "").length() - 4, (preNum + "").length() - 3));
            Long pre2Num43 = Long.valueOf((pre2Num + "").substring((pre2Num + "").length() - 4, (pre2Num + "").length() - 3));

            Long num4 = Long.valueOf((preNum + "").substring((preNum + "").length() - 4, (preNum + "").length() - 3));
            if (preNum43 - pre2Num43 >= -2 && preNum43 - pre2Num43 <= 2) {
                //adjust in 5
                if (2 <= num4 && num4 <= 7) {
                    num = ((num4 - 2) + " ")
                            + ((num4 - 1) + " ")
                            + ((num4 - 0) + " ")
                            + ((num4 + 1) + " ")
                            + ((num4 + 2) + " ");
                } else if (num4 == 1) {
                    num = "9 0 1 2 3";
                } else if (num4 == 0) {
                    num = "8 9 0 1 2";
                } else if (num4 == 8) {
                    num = "6 7 8 9 0";
                } else if (num4 == 9) {
                    num = "7 8 9 0 1";
                }
            } else {
                if (num4 == 0) {
                    num = "3 4 5 6 7";
                } else if (num4 == 1) {
                    num = "4 5 6 7 8";
                } else if (num4 == 2) {
                    num = "5 6 7 8 9";
                } else if (num4 == 3) {
                    num = "6 7 8 9 0";
                } else if (num4 == 4) {
                    num = "7 8 9 0 1";
                } else if (num4 == 5) {
                    num = "0 1 2 8 9";
                } else if (num4 == 6) {
                    num = "9 0 1 2 3";
                } else if (num4 == 7) {
                    num = "0 1 2 3 4";
                } else if (num4 == 8) {
                    num = "1 2 3 4 5";
                } else if (num4 == 9) {
                    num = "2 3 4 5 6";
                }
            }

            Long curNum = tecentOnlineDOList.get(i).getOnlineNum();
            LotteryDetail lotteryDetail = new LotteryDetail();
            lotteryDetail.setLotteryCode("QQFFC");
            lotteryDetail.setAliasNo(tecentOnlineDOList.get(i).getTime());
            lotteryDetail.setNo(num);
            String prizeNum = (curNum + "").substring((curNum + "").length() - 4, (curNum + "").length() - 3);
            lotteryDetail.setNum4(prizeNum);

            if (num.indexOf(prizeNum) >= 0) {
                lotteryDetail.setNum8("3");
            } else {
                lotteryDetail.setNum8("2");
            }
            log.info(tecentOnlineDOList.get(i).getTime() + "finished");
            lotteryDetailMapper.insert(lotteryDetail);
        }
    }

    public static void main(String[] args) {
        System.out.println("1 2 3".indexOf("2"));
    }

    public Map<String, Object> analysisQQffcData(String type, String start, String end) {
        Map<String, Object> map = new HashMap<>();
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause("alias_no asc ");
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andLotteryDateBetween(start, end);
        List<LotteryDetail> list = lotteryDetailMapper.selectByConditionWithH3(lotteryDetailCondition);
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            LotteryDetail next = list.get(i);
            LotteryDetail pre = list.get(i - 1);
            int nextNum = Integer.valueOf(next.getAliasNo().substring(next.getAliasNo().length() - 4));
            int preNum = Integer.valueOf(pre.getAliasNo().substring(pre.getAliasNo().length() - 4));
            timeList.add(next.getAliasNo());
            if (next.getAliasNo().substring(0, 8).equals(pre.getAliasNo().substring(0, 8))) {
                numList.add(nextNum - preNum);
            } else {
                numList.add(0);
            }
        }

        map.put("xAxis", timeList);
        map.put("series", numList);
        return map;
    }

    public Map<String, Object> analysis2Start2qzh3Data(String type, String start, String end) {
        Map<String, Object> map = new HashMap<>();
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause("alias_no asc ");
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andLotteryDateBetween(start, end);
        List<LotteryDetail> list = lotteryDetailMapper.selectByConditionWithH3(lotteryDetailCondition);
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            LotteryDetail next = list.get(i);
            LotteryDetail pre = list.get(i - 1);
            int nextNum = Integer.valueOf(next.getAliasNo().substring(next.getAliasNo().length() - 4));
            int preNum = Integer.valueOf(pre.getAliasNo().substring(pre.getAliasNo().length() - 4));
            timeList.add(next.getAliasNo());
            if (next.getAliasNo().substring(0, 8).equals(pre.getAliasNo().substring(0, 8))) {
                numList.add(nextNum - preNum);
            } else {
                numList.add(0);
            }
        }

        map.put("xAxis", timeList);
        map.put("series", numList);
        return map;
    }

    public void fetchQQffcData(Date startDate, Date endDate) throws Exception {
        while (startDate.compareTo(endDate) <= 0) {
            String date = DateUtils.date2String(startDate, "yyyyMMdd");
            this.fetchDateData(date);
            log.info("完成处理:" + date);
            startDate = DateUtils.addDate(1, startDate);
        }
    }

    public void fetchDateData(String date) throws Exception {
        try {
            Connection conn = Jsoup.connect("http://www.tx-ffc.com/txffc/kj-" + date + ".html");
            conn.timeout(3000);
            Document doc = conn.get();

            Elements els = null;
            els = doc.select(".klist .kj_list").select(".kj_code");
            log.info(date + " kj_code size:" + els.size());
            DigLotteryProc(els, date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DigLotteryProc(Elements els, String date) {
        // 处理数字彩票,从中间抓取取双色球、大乐透和七乐彩的开奖结果，每个彩种在table中都是<tbody>的一行<tr>
        LotteryDetail lotteryDetail = null;
        List<LotteryDetail> toInsert = new ArrayList<>();

        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andLotteryDateEqualTo(date);
        lotteryDetailMapper.deleteByCondition(lotteryDetailCondition);
        for (Element kjTitleElement : els) {
            Elements kjElements = kjTitleElement.select(".kj_title");
            for (Element row : kjElements) {

                String kjTime = row.getElementsByClass("a1").text();
                String curNo = row.getElementsByClass("a2").text();
                String kjNo = row.getElementsByClass("a3").text();
                try {
                    lotteryDetail = new LotteryDetail();
                    lotteryDetail.setLotteryCode("QQFFC");
                    lotteryDetail.setLotteryDate(date);
                    lotteryDetail.setTime(DateUtils.String2Date(date + kjTime, "yyyyMMddhh:mm"));
                    lotteryDetail.setNo(date + "-" + curNo.substring(0, curNo.length() - 1));
                    lotteryDetail.setAliasNo(date + "-" + curNo.substring(0, curNo.length() - 1));

                    lotteryDetail.setNum1(kjNo.substring(4));
                    lotteryDetail.setNum2(kjNo.substring(3, 4));
                    lotteryDetail.setNum3(kjNo.substring(2, 3));
                    lotteryDetail.setNum4(kjNo.substring(1, 2));
                    lotteryDetail.setNum5(kjNo.substring(0, 1));
                    toInsert.add(lotteryDetail);
                } catch (Exception e) {
                    log.error(date + curNo + " lotteryDetail  error, continue nextday", e);
                }
            }
        }
        this.batchCommit(toInsert);

    }

    private void batchCommit(List<LotteryDetail> list) {
        lotteryDetailMapper.insertBatch(list);
    }

}
