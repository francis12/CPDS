package com.ds.zxm.controller;

import com.alibaba.fastjson.JSON;
import com.ds.zxm.mapper.BetRecordDAO;
import com.ds.zxm.model.BetDO;
import com.ds.zxm.model.BetDOCondition;
import com.ds.zxm.model.BetRecordDO;
import com.ds.zxm.model.TradeSchedule;
import com.ds.zxm.service.BetService;
import com.ds.zxm.util.DsUtil;
import com.ds.zxm.util.HttpUtil;
import com.ds.zxm.util.LotteryUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;


@Controller
public class LotteryController {

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LotteryController.class);
    @Autowired
    private BetService betService;
    @Autowired
    private BetRecordDAO betRecordDAO;

    @ResponseBody
    @RequestMapping(value = "/betCP", method = {RequestMethod.POST})
    public String initdata(@RequestParam(required = true, value = "caipiao") String caipiao,
                           @RequestParam(required = true, value = "no") String no,
                           @RequestParam(required = true, value = "data") String data,
                           @RequestParam(required = true, value = "id") String id) {

        String result = "";
        try {
            String next1 = LotteryUtil.getNextAwardNo(no, caipiao);
            try {
                Map<String, String> map = new HashMap<String, String>();
                map.put("caipiao", caipiao);
                String result2 = HttpUtil.doPost("http://www.ds018.com/caipiao/kline/init", map, "utf-8", DsUtil.genRequestHeaderMap(caipiao));
                //更新彩票状态
                //201710200456
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap = JSON.parseObject(result2, Map.class);
                if ("0".equals(resultMap.get("ret").toString())) {
                    String nextid = resultMap.get("nextid").toString();
                    if (LotteryUtil.compare19860AwardNO(nextid, next1) > 0) {
                        //如果开奖号码大于开始号码重新出号
                        log.info("已过开奖号" + caipiao + "/" + id);
                        return result;
                    }
                }
            } catch (ParseException e) {
                log.error("比较当前开奖号和倍投开始号码失败,继续执行");
            }
            //有未开奖记录
            BetDOCondition betDOCondition = new BetDOCondition();
            betDOCondition.createCriteria().andLotteryCodeEqualTo(caipiao).andStatusEqualTo("1").andEndNoGreaterThan(no).andGenIdEqualTo(id);
            List<BetDO> betDOList = betService.queryBetList(betDOCondition);
            if (betDOList != null && betDOList.size() > 0) {
                return result;
            }
            String next2 = LotteryUtil.getNextAwardNo(next1, caipiao);
            String next3 = LotteryUtil.getNextAwardNo(next2, caipiao);
            String next4 = LotteryUtil.getNextAwardNo(next3, caipiao);
            String next5 = LotteryUtil.getNextAwardNo(next4, caipiao);
            String next6 = LotteryUtil.getNextAwardNo(next5, caipiao);

            String startPostfix = next1.substring(next1.length() - 3);
            String endNo = "";
            String endPostfix = "";
            if("chongqing".equals(caipiao)) {
                endPostfix  = next6.substring(next6.length() - 3);
                endNo = next6;
            } else if("flb90s".equals(caipiao)) {
                //flb做三期计划平刷
                endPostfix  = next3.substring(next3.length() - 3);
                endNo = next3;
            }

            log.info(id + "/" + caipiao + "--" + "第" + startPostfix + "-" + endPostfix + "期" + data + " ---");
            //FileUtils.write(new File(LotteryUtil.noPath + caipiao + id + ".txt"), "第" + startPostfix + "-" + endPostfix + "期" + data + " ---" + "\r", false);
            FileUtils.write(new File(LotteryUtil.noPath +  caipiao + id + ".txt"), "第" + startPostfix + "-" + endPostfix + "期" + data + " ---"  + "\r",false);

            BetDO bet = new BetDO();
            bet.setSeqNo(next1 + UUID.randomUUID().toString().substring(0,8) + endNo);
            bet.setStartNo(next1);
            bet.setEndNo(endNo);
            bet.setLotteryCode(caipiao);
            bet.setStatus("1");
            bet.setBetType("3");
            bet.setBetNo(data);
            bet.setGenId(id);
            bet.setCreateTime(new Date());
            betService.insert(bet);

            BetRecordDO betRecordDO = new BetRecordDO();
            betRecordDO.setLotteryCode(caipiao);
            betRecordDO.setBetNo(next1);
            betRecordDO.setBetWebsite("198");
            betRecordDO.setSeqNo(bet.getSeqNo());
            betRecordDO.setStatus("1");
            betRecordDO.setCreateTime(new Date());
            TradeSchedule startSchedule = scheMap.get("1");
            betRecordDO.setScheduleNo("1");

            betRecordDAO.insert(betRecordDO);
            String originNo = next1.substring(0, 8) + "-" + next1.substring(8);
            betLottery(originNo, startSchedule.getMultiple(), data);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("initdata error");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkRecall", method = {RequestMethod.POST})
    public boolean checkRecall(@RequestParam(required = true, value = "caipiao") String caipiao, @RequestParam(required = true, value = "id") String id) {
        //判断前台需要重新出号的条件.1.已中。2过了追号期
        boolean result = true;
        try {
            Thread.sleep(1 * 1000);
            //updateLotteryStatus(caipiao);

            BetDOCondition betDOCondition = new BetDOCondition();
            betDOCondition.createCriteria().andLotteryCodeEqualTo(caipiao).andStatusEqualTo("1").andGenIdEqualTo(id);
            List<BetDO> betDOList = betService.queryBetList(betDOCondition);
            if (betDOList != null && betDOList.size() > 0) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
            log.error("checkRecall error");
        }
        if (result) {
            LotteryUtil.writeTmpTxt2PrizeFile(caipiao, id);
            log.info("当期" + caipiao + "/" + id + "方案已完成，刷新页面");
        }
        return result;
    }

    public static Map<String, TradeSchedule> scheMap = null;

    static {
        scheMap = new HashMap<String, TradeSchedule>();
        TradeSchedule tradeSchedule1 = new TradeSchedule();
        tradeSchedule1.setWinNo(1);
        tradeSchedule1.setLoseNo(2);
        tradeSchedule1.setMultiple(8);
        scheMap.put("1", tradeSchedule1);

        TradeSchedule tradeSchedule2 = new TradeSchedule();
        tradeSchedule2.setWinNo(1);
        tradeSchedule2.setLoseNo(3);
        tradeSchedule2.setMultiple(13);
        scheMap.put("2", tradeSchedule2);

        TradeSchedule tradeSchedule3 = new TradeSchedule();
        tradeSchedule3.setWinNo(1);
        tradeSchedule3.setLoseNo(4);
        tradeSchedule3.setMultiple(19);
        scheMap.put("3", tradeSchedule3);

        TradeSchedule tradeSchedule4 = new TradeSchedule();
        tradeSchedule4.setWinNo(1);
        tradeSchedule4.setLoseNo(5);
        tradeSchedule4.setMultiple(29);
        scheMap.put("4", tradeSchedule4);

        TradeSchedule tradeSchedule5 = new TradeSchedule();
        tradeSchedule5.setWinNo(1);
        tradeSchedule5.setLoseNo(6);
        tradeSchedule5.setMultiple(45);
        scheMap.put("5", tradeSchedule5);

        TradeSchedule tradeSchedule6 = new TradeSchedule();
        tradeSchedule6.setWinNo(1);
        tradeSchedule6.setLoseNo(1);
        tradeSchedule6.setMultiple(68);
        scheMap.put("6", tradeSchedule6);
    }

    private void betLottery(String originNo, int multi, String nums) {
        /*try {
            betService.betto198("-1", originNo, multi + "", nums);
        } catch (UnsupportedEncodingException e) {
            log.error("betLottery", e);
        }*/
    }

    public static void main(String[] args) {
        try {
            while (true) {
                //FileUtils.write(new File("C:" + File.separator + "Users"+ File.separator + "zxm" + File.separator + "log" + File.separator + "198.txt"), String.valueOf(new Date()) + "\r",true);
                FileUtils.write(new File("D:" + File.separator + "198.txt"), String.valueOf(new Date()) + "\r\n" + "test", false);

                Thread.sleep(1000 * 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
