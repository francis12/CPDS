package com.ds.zxm.service;

import com.ds.zxm.mapper.*;
import com.ds.zxm.model.*;
import com.ds.zxm.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mockito.internal.util.collections.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class TecentOnlineService {

    @Autowired
    private BetDAO betDAO;
    @Autowired
    private TecentOnlineDAO tecentOnlineDAO;
    @Autowired
    private CurNoDAO curNoDAO;
    @Autowired
    private TecentTimeDAO tecentTimeDAO;
    @Autowired
    private StrategyDetailDAO strategyDetailDAO;

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TecentOnlineService.class);

    public static void main(String[] args) {


        Map<String,Integer> map = new HashMap<>();
        for(int i1=1;i1<3;i1++) {
            for(int i2=0;i2<10;i2++) {
                for(int i3=0;i3<10;i3++) {
                    for(int i4=0;i4<10;i4++) {
                        for(int i5=0;i5<10;i5++) {
                            for(int i6=0;i6<10;i6++) {
                                for(int i7=0;i7<10;i7++) {
                                    for(int i8=0;i8<10;i8++) {
                                        for(int i9=0;i9<10;i9++) {
                                            int sum = i1+i2+i3+i4+i5+i6+i7+i8+i9;
                                            int result = sum % 10;

                                            if (null == map.get("" + result) ) {
                                                map.put(""+result, 1);
                                            }else {
                                                map.put(""+result, map.get(""+result) + 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for(Map.Entry<String, Integer> entry :map.entrySet()) {
            System.out.println(entry.getKey() + "---" + entry.getValue());
        }
        try {
            // new TecentOnlineService().checkIsPrized();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  List<String> generateNextPrizeNo() throws Exception {
        List<String> toPrizeNumList = new ArrayList<>();
        //获取当期在线人数，获取当期开奖号码，根据时间段判断调整值，计算综合以上出下一期五星号码。五星号码分拆成前2和后3
        Document doc = this.fetchDateData(1);
        Elements details = doc.select(".main_detail_bar").get(0).select(".main_detail_word .col-lg-3");
        TecentOnlineDO tecentOnlineDO = this.convertElements2Do(details);
        CurNoDOCondition curNoDOCondition = new CurNoDOCondition();
        curNoDOCondition.createCriteria().andLotteryCodeEqualTo("QQFFC");
        List<CurNoDO> curNOs = curNoDAO.selectByCondition(curNoDOCondition);

        if(null == curNOs) {
            CurNoDO curNoDO = new CurNoDO();
            curNoDO.setLotteryCode("QQFFC");
            curNoDO.setCurNo(tecentOnlineDO.getTime());
            curNoDAO.insert(curNoDO);
        } else if(null != curNOs && curNOs.size() == 1) {
            CurNoDO curNoDO = curNOs.get(0);
            String onlineTime = tecentOnlineDO.getTime();
            String existTime = curNoDO.getCurNo();
            Date onlineDate = DateUtils.String2Date(onlineTime, "yyyy-MM-dd HH:mm:ss");
            Date existDate = DateUtils.String2Date(existTime, "yyyy-MM-dd HH:mm:ss");

            if (onlineDate.compareTo(existDate) > 0) {
                //检查是否中奖，并更新状态
                boolean isPrized = this.checkIsPrized();
                log.info(onlineTime + (isPrized?"已中奖":"未中奖"));
                BetDO betDO = new BetDO();
                betDO.setStatus(isPrized?"3":"2");
                BetDOCondition betDOCondition = new BetDOCondition();
                betDOCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andStartNoEqualTo(onlineTime);
                betDAO.updateByConditionSelective(betDO, betDOCondition);

                log.info("开始计算下一期...");
                //当前期已经开奖，计算下一期
                curNoDO.setCurNo(onlineTime);
                curNoDAO.updateByPrimaryKey(curNoDO);
            } else {
                log.info("开奖号未更新...");
                return toPrizeNumList;
            }
        } else {
            log.info("数据异常...");
            return toPrizeNumList;
        }
        String onlineNumStr = tecentOnlineDO.getOnlineNum() + "";
        //164727657
        Map<String,Integer>  nextOnlineMap = createNextOnlineNum(onlineNumStr, tecentOnlineDO.getTime());
        if (null == nextOnlineMap || null == nextOnlineMap.get("start")) {
            log.info("数据库调整数据异常...");
            return toPrizeNumList;
        }
        //去除最后4位
        Integer preStart = Integer.valueOf((nextOnlineMap.get("start") + "").substring(0, (nextOnlineMap.get("start") + "").length()-4 ));
        Integer preEnd = Integer.valueOf((nextOnlineMap.get("end") + "").substring(0, (nextOnlineMap.get("end") + "").length()-4 ));
        toPrizeNumList = this.createPrizeNumList(preStart, preEnd);

        Map<String, Integer> map = new HashMap<>();
        //get q3
        for(String num : toPrizeNumList) {
            String pre3 = num.substring(0,4);
            if(null == map.get(pre3)) {
                map.put(pre3,1);
            } else {
                map.put(pre3, map.get(pre3) + 1);
            }
        }
        Collections.sort(toPrizeNumList);

        String scheNo = DateUtils.date2String(DateUtils.getDateAdd(DateUtils.String2Date(tecentOnlineDO.getTime(), "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, 1), "yyyy-MM-dd HH:mm:ss" );
        BetDO betDO = new BetDO();
        betDO.setLotteryCode("QQFFC");
        betDO.setStartNo(scheNo);
        betDO.setBetType("5");
        betDO.setStatus("1");
        StringBuffer sb = new StringBuffer();
        for(String num : toPrizeNumList) {
            sb.append(num + " ");
        }
        betDO.setBetNo(sb.toString());
        betDAO.insert(betDO);
        log.info(
                scheNo + "期投注计划, 总共" + toPrizeNumList.size() + "个计划");
        return  toPrizeNumList;
    }

    private boolean checkIsPrized() throws  Exception {
        String curDate =  DateUtils.date2String(new Date(), "yyyyMMdd");
        Connection conn = Jsoup.connect("http://www.tx-ffc.com/txffc/kj-" + curDate + ".html");
        conn.timeout(3000);
        Document doc = conn.get();
        Elements els = null;
        els  = doc.select(".klist .kj_list").select(".kj_code");
        Element curTd  = els.get(0).select(".kj_title").get(0);

        String kjTime = curTd.getElementsByClass("a1").text();
        String curNo = curTd.getElementsByClass("a2").text();
        String kjNo = curTd.getElementsByClass("a3").text();

        System.out.println(kjTime + " : "+ kjNo);

        BetDOCondition betDOCondition = new BetDOCondition();
        betDOCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andStartNoEqualTo( DateUtils.date2String(new Date(), "yyyy-MM-dd") + " " + kjTime +":00");
        List<BetDO> betDOList = betDAO.selectByExampleWithBLOBs(betDOCondition);
        if (null != betDOList && betDOList.size() == 1) {
            return  betDOList.get(0).getBetNo().indexOf(kjNo)> 0;
        }
        return false;
    }
    String[] excludes = new String[] {"0000", "1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999"};
    //生成投注号码
    private List<String> createPrizeNumList(int preStart, int preEnd) {
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

    static Map<String,Integer> adjustNumMap = new HashMap<>();
    static {
        adjustNumMap.put("start", -40000);
        adjustNumMap.put("end", 00000);
    }
    //根据当期在线数和时间推测下一期在线数
    private  Map<String,Integer>   createNextOnlineNum(String curNums, String time) throws ParseException {

        String postTime =DateUtils.date2String(DateUtils.String2Date(time, "yyyy-MM-dd HH:mm:ss"), "HH:mm:ss");
        Map<String,Integer>  nextOnlineMap =  new HashMap<>();
        Integer curNum = Integer.valueOf(curNums);
        TecentTimeDOCondition tecentTimeDOCondition = new TecentTimeDOCondition();
        tecentTimeDOCondition.createCriteria().andStartTimeLessThanOrEqualTo(postTime).andEndTimeGreaterThan(postTime);

        List<TecentTimeDO> tecentTimeDOList = tecentTimeDAO.selectByCondition(tecentTimeDOCondition);

        if (null != tecentTimeDOList && tecentTimeDOList.size() > 0) {
            nextOnlineMap.put("start", Integer.valueOf(tecentTimeDOList.get(0).getStart()) + curNum );
            nextOnlineMap.put("end", Integer.valueOf(tecentTimeDOList.get(0).getEnd()) + curNum );
        }
        return nextOnlineMap;
    }
    public Map<String,Object> genCurrentNumByHisRateAndPreNumWithInterval(String startTime, String endTime) {
        Map<String,Object> result = new HashMap<>();
        List<StrategyDetailDO> strategyDetailDOList = new ArrayList<>();
        try {
            Date start = DateUtils.String2Date(startTime, "yyyy-MM-dd HH:mm:ss");
            Date end = DateUtils.String2Date(endTime, "yyyy-MM-dd HH:mm:ss");

            String winOrLoseStr= "";
            //中停挂跟
            int loseCnt = 0;
            int winTotal = 0;
            Map<String, Integer> calWinOrLose = new HashMap<>();
            while (start.compareTo(end)<=0) {
                try {
                    StrategyDetailDO strategyDetailDO = new StrategyDetailDO();
                    String curTime = DateUtils.date2String(start, "yyyy-MM-dd HH:mm:ss");
                    //Map<String,Object> itemResult = this.genCurrentNumByHisRateAndPreNum(curTime);
                    Map<String,Object> itemResult = this.genCurrentNumBySameNo(curTime);
                    result.putAll(itemResult);
                    strategyDetailDO.setLotteryCode("TXFF");
                    strategyDetailDO.setNo(curTime);
                    strategyDetailDO.setStatus((String) itemResult.get(curTime));
                    strategyDetailDOList.add(strategyDetailDO);

                    winOrLoseStr = winOrLoseStr + ((String) itemResult.get(curTime));
                    if (winOrLoseStr.endsWith("中") && loseCnt == 0) {
                        winOrLoseStr = "";
                        winTotal++;
                    } else if ((winOrLoseStr.endsWith("挂")  && loseCnt == 0)) {
                        loseCnt = 1;
                    } else if ((winOrLoseStr.endsWith("中中")  && loseCnt > 0)) {
                        if (calWinOrLose.get("" + loseCnt) == null) {
                            calWinOrLose.put("" + loseCnt,1);
                        } else {
                            calWinOrLose.put("" + loseCnt,calWinOrLose.get("" + loseCnt)+ 1);
                        }
                        winOrLoseStr = "";
                        winTotal++;
                        loseCnt = 0;
                    } else if((winOrLoseStr.endsWith("中挂")  && loseCnt > 0)){
                        loseCnt++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    start = DateUtils.addMinutes(1,start);
                }
            }
            strategyDetailDAO.insertBatch(strategyDetailDOList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    //通过计算最近N期和最近N天同一期的数据得到当前期数据
    public Map<String,Object> genCurrentNumBySameNo(String time) {
        Map<String,Object> calResult = new HashMap<>();
        try {
            //查询最近7天这一期的在线人数和最近7期的在线人数取平均数
            List<String> timeList = new ArrayList<>();
            Date date = DateUtils.String2Date(time, "yyyy-MM-dd HH:mm:ss");
            String datePre1 = DateUtils.date2String(DateUtils.addMinutes(-1, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre2 = DateUtils.date2String(DateUtils.addMinutes(-2, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre3 = DateUtils.date2String(DateUtils.addMinutes(-3, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre4 = DateUtils.date2String(DateUtils.addMinutes(-4, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre5 = DateUtils.date2String(DateUtils.addMinutes(-5, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre6 = DateUtils.date2String(DateUtils.addMinutes(-6, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre7 = DateUtils.date2String(DateUtils.addMinutes(-7, date),"yyyy-MM-dd HH:mm:ss" );

            String datePre11 = DateUtils.date2String(DateUtils.addDate(-1, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre12 = DateUtils.date2String(DateUtils.addDate(-2, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre13 = DateUtils.date2String(DateUtils.addDate(-3, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre14 = DateUtils.date2String(DateUtils.addDate(-4, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre15 = DateUtils.date2String(DateUtils.addDate(-5, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre16 = DateUtils.date2String(DateUtils.addDate(-6, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre17 = DateUtils.date2String(DateUtils.addDate(-7, date),"yyyy-MM-dd HH:mm:ss" );


            timeList.add(datePre1);
            timeList.add(datePre2);
            timeList.add(datePre3);
            timeList.add(datePre4);
            timeList.add(datePre5);
            timeList.add(datePre6);
            timeList.add(datePre7);

            timeList.add(datePre11);
            timeList.add(datePre12);
            timeList.add(datePre13);
            timeList.add(datePre14);
            timeList.add(datePre15);
            timeList.add(datePre16);
            timeList.add(datePre17);


            TecentOnlineDOCondition condition1 = new TecentOnlineDOCondition();
            condition1.createCriteria().andTimeIn(timeList);
            List<TecentOnlineDO> tecentOnlineDOList = tecentOnlineDAO.selectByCondition(condition1);

            Long avgOnlineNum = 0l;
            Long avgAdjustNum = 0l;
            for (TecentOnlineDO item: tecentOnlineDOList) {
                Long onlineNum = item.getOnlineNum();
                Long adjustNum = Long.valueOf(item.getAdjustNum().replace(" ",""));;

                if (avgOnlineNum == 0l) {
                    avgOnlineNum = onlineNum;
                }else  {
                    avgOnlineNum = (avgOnlineNum + onlineNum)/2;
                }
                if (avgAdjustNum == 0l) {
                    avgAdjustNum = adjustNum;
                } else {
                    avgAdjustNum = (avgAdjustNum + adjustNum)/2;
                }
            }

            String calres = "" + (avgAdjustNum + avgOnlineNum);
            char[] calresItems = calres.toCharArray();

            TecentOnlineDOCondition condition2 = new TecentOnlineDOCondition();
            condition2.createCriteria().andTimeEqualTo(time);
            List<TecentOnlineDO> reactTecentOnline = tecentOnlineDAO.selectByCondition(condition2);
            if (reactTecentOnline == null && reactTecentOnline.size() <= 0) {
                return null;
            }
            String react = "" + reactTecentOnline.get(0).getOnlineNum();
            char[] reactItems = react.toCharArray();

            int calresSum=0;
            for(char ch : calresItems) {
                calresSum = calresSum + Integer.valueOf(String.valueOf(ch));
            }

            int reactSum=0;
            for(char ch : reactItems) {
                reactSum = reactSum +  Integer.valueOf(String.valueOf(ch));
            }

            int wanCal = calresSum%10;
            int wanRes = reactSum%10;

            log.info(time +",计算：" + calres + ", 实际：" + react +  ",计算万位:"+ wanCal + ",实际万位：" + wanRes);

            //重写，char转有问题
            int qianCal = Integer.valueOf(String.valueOf(calres.charAt(calres.length()-4)));
            int qianRes = Integer.valueOf(String.valueOf(react.charAt(react.length()-4)));

            int baiCal = Integer.valueOf(String.valueOf(calres.charAt(calres.length()-3)));
            int baiRes = Integer.valueOf(String.valueOf(react.charAt(react.length()-3)));

            if (this.isAdjustInInterval(baiCal, baiRes, 3)) {
                if(this.isAdjustInInterval(qianCal, qianRes, 3)) {
                    calResult.put(time,"中");
                } else {
                    calResult.put(time,"挂");
                }
            } else {
                calResult.put(time,"挂");
            }

            System.out.println(time + "---" + calResult.get(time));
        } catch (Exception e) {
            System.out.println(time + "计算出错");
        }
        return calResult;
    }

    //根据下一期的历史波动率和当前的在线人数，计算下一期人数
    public Map<String,Object> genCurrentNumByHisRateAndPreNum(String time) {
        Map<String,Object> calResult = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> result = null;
        try {
            Date date = DateUtils.String2Date(time, "yyyy-MM-dd HH:mm:ss");
            String curTime = DateUtils.date2String(DateUtils.addMinutes(-1, date),"yyyy-MM-dd HH:mm:ss" );
            map.put("curTime", curTime);
            map.put("nextTime", time);
            map.put("time", DateUtils.date2String(date,"HH:mm"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            result = tecentTimeDAO.selectCalResultByTime(map);



            //查询最近7天这一期的在线人数和最近7期的在线人数取平均数
            List<String> timeList = new ArrayList<>();
            Date date = DateUtils.String2Date(time, "yyyy-MM-dd HH:mm:ss");
            String datePre1 = DateUtils.date2String(DateUtils.addMinutes(-1, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre2 = DateUtils.date2String(DateUtils.addMinutes(-2, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre3 = DateUtils.date2String(DateUtils.addMinutes(-3, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre4 = DateUtils.date2String(DateUtils.addMinutes(-4, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre5 = DateUtils.date2String(DateUtils.addMinutes(-5, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre6 = DateUtils.date2String(DateUtils.addMinutes(-6, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre7 = DateUtils.date2String(DateUtils.addMinutes(-7, date),"yyyy-MM-dd HH:mm:ss" );

            String datePre11 = DateUtils.date2String(DateUtils.addDate(-1, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre12 = DateUtils.date2String(DateUtils.addDate(-2, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre13 = DateUtils.date2String(DateUtils.addDate(-3, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre14 = DateUtils.date2String(DateUtils.addDate(-4, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre15 = DateUtils.date2String(DateUtils.addDate(-5, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre16 = DateUtils.date2String(DateUtils.addDate(-6, date),"yyyy-MM-dd HH:mm:ss" );
            String datePre17 = DateUtils.date2String(DateUtils.addDate(-7, date),"yyyy-MM-dd HH:mm:ss" );


            timeList.add(datePre1);
            timeList.add(datePre2);
            timeList.add(datePre3);
            timeList.add(datePre4);
            timeList.add(datePre5);
            timeList.add(datePre6);
            timeList.add(datePre7);

            timeList.add(datePre11);
            timeList.add(datePre12);
            timeList.add(datePre13);
            timeList.add(datePre14);
            timeList.add(datePre15);
            timeList.add(datePre16);
            timeList.add(datePre17);

            TecentTimeDOCondition condition = new TecentTimeDOCondition();
            condition.createCriteria().andEndTimeIn(timeList);

            List<TecentTimeDO> tecentTimeDOList = tecentTimeDAO.selectByCondition(condition);

            if(null == result) {
                return calResult;
            }
            String calres = result.get("calres").toString();
            char[] calresItems = calres.toCharArray();
            String react = result.get("react").toString();
            char[] reactItems = react.toCharArray();

            int calresSum=0;
            for(char ch : calresItems) {
                calresSum = calresSum + Integer.valueOf(String.valueOf(ch));
            }

            int reactSum=0;
            for(char ch : reactItems) {
                reactSum = reactSum +  Integer.valueOf(String.valueOf(ch));
            }

            int wanCal = calresSum%10;
            int wanRes = reactSum%10;

            log.info(time +",计算：" + calres + ", 实际：" + react +  ",计算万位:"+ wanCal + ",实际万位：" + wanRes);

            //重写，char转有问题
            int qianCal = Integer.valueOf(String.valueOf(calres.charAt(calres.length()-4)));
            int qianRes = Integer.valueOf(String.valueOf(react.charAt(react.length()-4)));


            if (this.isAdjustInInterval(wanCal, wanRes, 3)) {
                if(this.isAdjustInInterval(qianCal, qianRes, 3)) {
                    calResult.put(time,"中奖");
                } else {
                    calResult.put(time,"未中奖");
                }
            } else {
                calResult.put(time,"未中奖");
            }

            System.out.println(time + "---" + calResult.get(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calResult;
    }

    //以basic为基准，adjust为调整基数
    private boolean isAdjustInInterval(int basic,int toJudge, int adjust) {
        adjust=3;
        switch (basic){
            case 0:
                if(toJudge==0||toJudge==1||toJudge==2||toJudge==3||toJudge==7||toJudge==8||toJudge==9) {
                    return true;
                }
                break;
            case 1:
                if(toJudge==0||toJudge==1||toJudge==2||toJudge==3||toJudge==4||toJudge==8||toJudge==9) {
                    return true;
                }
                break;
            case 2:
                if(toJudge==0||toJudge==1||toJudge==2||toJudge==3||toJudge==4||toJudge==5||toJudge==9) {
                    return true;
                }
                break;
            case 3:
                if(toJudge==0||toJudge==1||toJudge==2||toJudge==3||toJudge==4||toJudge==5||toJudge==6) {
                    return true;
                }
                break;
            case 4:
                if(toJudge==7||toJudge==1||toJudge==2||toJudge==3||toJudge==4||toJudge==5||toJudge==6) {
                    return true;
                }
                break;
            case 5:
                if(toJudge==7||toJudge==8||toJudge==2||toJudge==3||toJudge==4||toJudge==5||toJudge==6) {
                    return true;
                }
                break;
            case 6:
                if(toJudge==3||toJudge==4||toJudge==5||toJudge==6||toJudge==7||toJudge==8||toJudge==9) {
                    return true;
                }
                break;
            case 7:
                if(toJudge==0||toJudge==4||toJudge==5||toJudge==6||toJudge==7||toJudge==8||toJudge==9) {
                    return true;
                }
                break;
            case 8:
                if(toJudge==0||toJudge==1||toJudge==5||toJudge==6||toJudge==7||toJudge==8||toJudge==9) {
                    return true;
                }
                break;
            case 9:
                if(toJudge==0||toJudge==1||toJudge==2||toJudge==6||toJudge==7||toJudge==8||toJudge==9) {
                    return true;
                }
                break;
            default:;
        }
        return  false;
    }
    public void fetchTecentOnlineData(int startPage, int endPage) throws Exception {
        while ( startPage <= endPage) {
            try {
                Document doc = this.fetchDateData(startPage);

                int count = 0;
                while (null == doc && count<10) {
                        try {
                            Thread.sleep(3000);
                            doc = this.fetchDateData(startPage);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        count++;
                }
                Elements els = null;
                els  = doc.select(".main_detail_bar");
                DigLotteryProc(els);
                log.info("完成处理第" + startPage + "页" );
            } catch (Exception e) {
                log.error(startPage + " get error" , e);
            } finally {
                startPage++;
            }
        }
    }
    private Document fetchDateData(int curPage){
        Document doc = null;
        try{
            Connection conn = Jsoup.connect("http://www.cndgv.com/")
                    .data("hdPage", curPage + "")
                    .data("hdLastPage", "");
            conn.timeout(5000);
            doc = conn.post();
        }catch(Exception e){
           log.error("fetchDateData error:" + curPage ,e);
        }finally {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return  doc;
    }

    private void DigLotteryProc(Elements els) throws ParseException {

        List<TecentOnlineDO> tecentOnlineDOS = new ArrayList<>();
            for (Element row : els) {
                Elements details = row.select(".main_detail_word .col-lg-3" );
                TecentOnlineDO tecentOnlineDO = this.convertElements2Do(details);
                tecentOnlineDOS.add(tecentOnlineDO);
            }

        System.out.println("insert:" + tecentOnlineDOS.get(0).getTime() + " --- " + tecentOnlineDOS.get(tecentOnlineDOS.size()-1).getTime());
        tecentOnlineDAO.insertBatch(tecentOnlineDOS);
    }

    private TecentOnlineDO convertElements2Do(Elements details) {
        TecentOnlineDO tecentOnlineDO = new TecentOnlineDO();
        for (int i = 0; i <details.size() ; i++) {
            Element element = details.get(i);
            String text = element.text();
            //时间，在线数，波动值
            switch (i) {
                case 1:
                    tecentOnlineDO.setTime(text);
                    break;
                case 2:
                    Long num = Long.valueOf(text.replace(",", ""));
                    tecentOnlineDO.setOnlineNum(num);
                    break;
                case 3:
                    tecentOnlineDO.setAdjustNum(text.replace(",", ""));
                    break;
                default:
                    ;
            }
        }
        return tecentOnlineDO;
    }
    private void fetchDateData_old(int curPage){
        try{
            Connection conn = Jsoup.connect("http://www.77tj.org/tencent")
                    .cookie(".AspNetCore.Antiforgery.nlUjaxz2h3Y", "CfDJ8G7EgE_H0flLt8k5_Y6lrz9stboT9douoXuT06r7tNvbhRolOtnL1H98LY56qkcSKbnTkHD2aP8osxWU_tg-mtNvBKGMXBqk7voSzaTcS9_PRMZqqVho6SxaY4JeRM77YPCUImhfYN-31f04XtNgRgQ")
                    .data("PageIndex", curPage + "")
                    .data("__RequestVerificationToken", "CfDJ8G7EgE_H0flLt8k5_Y6lrz8ZVhBx7UjN6qPY0MM0G-N4mw_6OHtB0v_7iE21C6HJCdht752tpIM6mGto--Uu19TvDhGJoenvDnVM4B3FlGVd5222yu3KL7euTIOktmR4tdFuSquq5zcW9mS_8RSuk9o");
            conn.timeout(3000);
            Document doc = conn.post();

            Elements els = null;
            els  = doc.select(".gridview").select("tbody tr");
            log.info(curPage + " --- " + els.size());
            //DigLotteryProc(els);
        }catch(Exception e){
            log.error("fetchDateData error:" + curPage ,e);
        }
    }

    private void DigLotteryProc_old(Elements els) throws ParseException {
        for (Element row : els) {
            TecentOnlineDO tecentOnlineDO = new TecentOnlineDO();
            Elements tds = row.select("td");
            for (int i = 0; i <tds.size() ; i++) {
                Element element = tds.get(i);
                String text = element.text();

                if("统计时间".equals(text)
                        ||"在线人数".equals(text)
                        ||"波动值".equals(text)) {
                    continue;
                }
                //时间，在线数，波动值
                switch (i) {
                    case 0:
                        tecentOnlineDO.setTime(text);
                        break;
                    case 1:
                        Long num = Long.valueOf(text.replace(",", ""));
                        tecentOnlineDO.setOnlineNum(num);
                        break;
                    case 2:
                        tecentOnlineDO.setAdjustNum(text);
                        break;
                    default:
                        ;
                }
            }
            this.saveIfNotExist(tecentOnlineDO);
        }
    }

    private void saveIfNotExist(TecentOnlineDO tecentOnlineDO) {
        if(null == tecentOnlineDO || null == tecentOnlineDO.getTime()) {
            log.info("数据不存在"  + tecentOnlineDO.getTime());
            return;
        }
        TecentOnlineDOCondition tecentOnlineDOCondition = new TecentOnlineDOCondition();
        tecentOnlineDOCondition.createCriteria().andTimeEqualTo(tecentOnlineDO.getTime());
        int cnt = tecentOnlineDAO.countByCondition(tecentOnlineDOCondition);
        if (cnt == 0) {
            tecentOnlineDAO.insert(tecentOnlineDO);
            log.info("完成处理"  + tecentOnlineDO.getTime());
        } else {
            log.info("跳过"  + tecentOnlineDO.getTime());
        }
    }

    public Map<String, Object> queryTecentOnlineData2Option(String type, String value, String value2) throws ParseException {

        TecentOnlineDOCondition tecentOnlineDOCondition = new TecentOnlineDOCondition();
        tecentOnlineDOCondition.setOrderByClause("time asc");
        if ("1".equals(type)) {
            tecentOnlineDOCondition.createCriteria().andTimeLike("2017%" + value + ":%:%").andTimeGreaterThanOrEqualTo(value2);
        } else if ("2".equals(type)) {
            tecentOnlineDOCondition.createCriteria().andTimeBetween(value, value2);
        }
        List<TecentOnlineDO> tecentOnlineDOList = tecentOnlineDAO.selectByCondition(tecentOnlineDOCondition);
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        for(TecentOnlineDO item : tecentOnlineDOList) {
            timeList.add(item.getTime());
            //numList.add(Integer.valueOf(item.getAdjustNum().replace(" ", "")));
            String onlineNum = item.getOnlineNum().toString();
            numList.add(Integer.valueOf(onlineNum.substring(onlineNum.length()-4, onlineNum.length()-3)));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xAxis", timeList);
        map.put("series", numList);
        return map;
    }
}
