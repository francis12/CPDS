package com.ds.zxm.service;

import com.ds.zxm.mapper.BetDAO;
import com.ds.zxm.mapper.CurNoDAO;
import com.ds.zxm.mapper.TecentOnlineDAO;
import com.ds.zxm.mapper.TecentTimeDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TecentOnlineService.class);

    public static void main(String[] args) {
        try {
             new TecentOnlineService().checkIsPrized();
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
    public void fetchTecentOnlineData(int startPage, int endPage) throws Exception {
        while ( startPage <= endPage) {
            try {
                Document doc = this.fetchDateData(startPage);
                Elements els = null;
                els  = doc.select(".main_detail_bar");
                log.info(startPage + " --- " + els.size());
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
            conn.timeout(3000);
            doc = conn.post();
        }catch(Exception e){
           log.error("fetchDateData error:" + curPage ,e);
        }
        return  doc;
    }

    private void DigLotteryProc(Elements els) throws ParseException {
        for (Element row : els) {
            Elements details = row.select(".main_detail_word .col-lg-3" );

            TecentOnlineDO tecentOnlineDO = this.convertElements2Do(details);
            this.saveIfNotExist(tecentOnlineDO);
        }
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
            DigLotteryProc(els);
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
            numList.add(Integer.valueOf(item.getAdjustNum().replace(" ", "")));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xAxis", timeList);
        map.put("series", numList);
        return map;
    }
}
