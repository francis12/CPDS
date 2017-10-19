package com.ds.zxm.controller;

import com.alibaba.fastjson.JSON;
import com.ds.zxm.mapper.BetRecordDAO;
import com.ds.zxm.model.*;
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
import java.io.UnsupportedEncodingException;
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
                           @RequestParam(required = true, value = "data") String data) {

        String result = "";
        try {

            System.out.println(no + "投注:" + data);
            String next1 = LotteryUtil.getNextAwardNo(no, caipiao);
            String next2 = LotteryUtil.getNextAwardNo(next1, caipiao);
            String next3 = LotteryUtil.getNextAwardNo(next2, caipiao);
            String next4 = LotteryUtil.getNextAwardNo(next3, caipiao);
            String next5 = LotteryUtil.getNextAwardNo(next4, caipiao);
            String next6 = LotteryUtil.getNextAwardNo(next5, caipiao);

            String startPostfix = next1.substring(next1.length() - 3);
            String endPostfix = next6.substring(next6.length() - 3);
            log.info("第" + startPostfix + "-" + endPostfix + "期" + data + " ---");
            //FileUtils.write(new File("C:" + File.separator + "Users"+ File.separator + "zxm" + File.separator + "log" + File.separator + "198.txt"), "第" + startPostfix + "-" + endPostfix + "期" + data + " ---"  + "\r",false);
            //由于文件无法实时更新，需要自己写接口连接网站投注

            //FileUtils.writeStringToFile(new File("c://tz.txt"),startPostfix + "-" + endPostfix+"期" + data  + " ---" + "\r",true);
            BetDO bet = new BetDO();
            bet.setSeqNo(next1 + UUID.randomUUID().toString() + next6);
            bet.setStartNo(next1);
            bet.setEndNo(next6);
            bet.setLotteryCode(caipiao);
            bet.setStatus("1");
            bet.setBetType("3");
            bet.setBetNo(data);
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
            log.error("initdata error", e);        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/checkRecall", method = {RequestMethod.POST})
    public boolean checkRecall(@RequestParam(required = true, value = "caipiao") String caipiao) {
        //判断前台需要重新出号的条件.1.已中。2过了追号期
        boolean result = true;
        try {
            Thread.sleep(5 * 1000);
            updateLotteryStatus(caipiao);

            if ("chongqing".equals(caipiao)) {
                BetDOCondition betDOCondition = new BetDOCondition();
                betDOCondition.createCriteria().andLotteryCodeEqualTo(caipiao).andStatusEqualTo("1");
                List<BetDO> betDOList = betService.queryBetList(betDOCondition);
                if (betDOList != null && betDOList.size() > 0) {
                    result = false;
                }
            }
        } catch (Exception e) {
            result = false;
            log.error("checkRecall error", e);
        }
        if (result) {
            log.info("当期方案已完成，刷新页面");
            try {
                //冲掉上次方案，防止赚投误取
                FileUtils.write(new File("C:" + File.separator + "Users"+ File.separator + "zxm" + File.separator + "log" + File.separator + "198.txt"), "等待前台刷新方案中..."  + "\r",false);
            } catch (IOException e) {
               log.error("write tmp file data er", e);
            }
        }
        return result;
    }

    /**
     * 暂时只支持一个方案
     *
     * @param caipiao
     * @throws ParseException
     */
    private void updateLotteryStatus(String caipiao) throws ParseException {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("caipiao", caipiao);
            String result = HttpUtil.doPost("http://www.ds018.com/caipiao/kline/init", map, "utf-8", DsUtil.genRequestHeaderMap(caipiao));

            //更新彩票状态
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap = JSON.parseObject(result, Map.class);
            if ("0".equals(resultMap.get("ret").toString())) {
                String curNO = resultMap.get("peroid").toString();
                String prize = resultMap.get("prize").toString();

               /* BetDO betDO = new BetDO();
                betDO.setLotteryCode(caipiao);
                betDO.setBetType("3");
                //未开奖
                betDO.setStatus("1");*/
                //List<BetDO> betDOList = betService.queryBetUnprizeInfo(betDO, curNO);
                BetDOCondition betDOCondition = new BetDOCondition();
                //查询所有status为1的记录
                betDOCondition.createCriteria().andBetTypeEqualTo("3").andLotteryCodeEqualTo(caipiao).andStatusEqualTo("1");
                List<BetDO> betDOList = betService.queryBetList(betDOCondition);

                //BetDOCondition bdd = new BetDOCondition();
                for (BetDO item : betDOList) {
                   // bdd.createCriteria().andIdEqualTo(item.getId());
                    //匹配当期期是否中奖或者已经是最后一期
                    if ("3".equals(item.getBetType())) {
                        //历史记录置为2
                        if (LotteryUtil.compareCQAwardNO(item.getEndNo(), curNO) < 0
                                || LotteryUtil.compareCQAwardNO(item.getStartNo(), LotteryUtil.getNextAwardNo(curNO,caipiao)) > 0) {
                            item.setStatus("2");
                            betService.updateBetDO(item);
                        } else {
                            //方案正在进行中
                            BetRecordDOCondition betRecordDOCondition = new BetRecordDOCondition();
                            betRecordDOCondition.createCriteria().andLotteryCodeEqualTo(caipiao).andBetNoEqualTo(curNO).andStatusEqualTo("1");
                            List<BetRecordDO> betRecordDOList = betRecordDAO.selectByCondition(betRecordDOCondition);
                            for (BetRecordDO betRecordDOItem : betRecordDOList) {
                                {
                                    if (item.getBetNo().indexOf(prize.substring(prize.length() - 3)) > 0) {
                                        item.setStatus("3");
                                        item.setPrizeNo(curNO);
                                        log.info(item.getSeqNo() + "已中奖");
                                        betService.updateBetDO(item);

                                        BetRecordDO betRecordDO = new BetRecordDO();
                                        betRecordDO.setId(betRecordDOItem.getId());
                                        betRecordDO.setStatus("3");
                                        betRecordDAO.updateByPrimaryKeySelective(betRecordDO);
                                    } else {
                                        //投注记录更新成未中奖
                                        BetRecordDO betRecordDO = new BetRecordDO();
                                        betRecordDO.setId(betRecordDOItem.getId());
                                        betRecordDO.setStatus("2");
                                        betRecordDAO.updateByPrimaryKeySelective(betRecordDO);
                                        if (!curNO.equals(item.getEndNo())) {
                                            //投注下一期
                                            String nextNo = LotteryUtil.getNextAwardNo(curNO, caipiao);
                                            String curScheduleNo = betRecordDOItem.getScheduleNo();
                                            TradeSchedule tradeSchedule = scheMap.get(curScheduleNo);
                                            int nextscheNo = tradeSchedule.getLoseNo();
                                            TradeSchedule nextschedule = scheMap.get("" + nextscheNo);
                                            BetRecordDO betRecordDO2 = new BetRecordDO();
                                            betRecordDO2.setStatus("1");
                                            betRecordDO2.setSeqNo(betRecordDOItem.getSeqNo());
                                            betRecordDO2.setBetNo(nextNo);
                                            betRecordDO2.setBetWebsite(betRecordDOItem.getBetWebsite());
                                            betRecordDO2.setLotteryCode(betRecordDOItem.getLotteryCode());
                                            betRecordDO2.setCreateTime(new Date());
                                            betRecordDO2.setScheduleNo("" + nextscheNo);
                                            betRecordDAO.insert(betRecordDO2);

                                            String originNo = nextNo.substring(0, 8) + "-" + nextNo.substring(8);
                                            betLottery(originNo, nextschedule.getMultiple(), item.getBetNo());
                                        } else {
                                            //跑完最后一期未中奖，更新方案状态
                                            log.info(item.getSeqNo() + "未中奖");
                                            item.setStatus("2");
                                            betService.updateBetDO(item);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ParseException e) {
            log.error("updateLotteryStatus error", e);
        }
    }
    static Map<String,TradeSchedule> scheMap = null;
    static  {
        scheMap = new HashMap<String,TradeSchedule>();
        TradeSchedule tradeSchedule1 =new TradeSchedule();
        tradeSchedule1.setWinNo(1);
        tradeSchedule1.setLoseNo(2);
        tradeSchedule1.setMultiple(8);
        scheMap.put("1", tradeSchedule1);

        TradeSchedule tradeSchedule2 =new TradeSchedule();
        tradeSchedule2.setWinNo(1);
        tradeSchedule2.setLoseNo(3);
        tradeSchedule2.setMultiple(13);
        scheMap.put("2", tradeSchedule2);

        TradeSchedule tradeSchedule3 =new TradeSchedule();
        tradeSchedule3.setWinNo(1);
        tradeSchedule3.setLoseNo(4);
        tradeSchedule3.setMultiple(19);
        scheMap.put("3", tradeSchedule3);

        TradeSchedule tradeSchedule4 =new TradeSchedule();
        tradeSchedule4.setWinNo(1);
        tradeSchedule4.setLoseNo(5);
        tradeSchedule4.setMultiple(29);
        scheMap.put("4", tradeSchedule4);

        TradeSchedule tradeSchedule5 =new TradeSchedule();
        tradeSchedule5.setWinNo(1);
        tradeSchedule5.setLoseNo(6);
        tradeSchedule5.setMultiple(45);
        scheMap.put("5", tradeSchedule5);

        TradeSchedule tradeSchedule6 =new TradeSchedule();
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

    public  static  void main(String[] args){
        try {
            while (true) {
                FileUtils.write(new File("C:" + File.separator + "Users"+ File.separator + "zxm" + File.separator + "log" + File.separator + "198.txt"), String.valueOf(new Date()) + "\r",true);
                Thread.sleep(1000 *1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
