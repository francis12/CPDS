package com.ds.zxm.controller;

import com.alibaba.fastjson.JSON;
import com.ds.zxm.model.BetDO;
import com.ds.zxm.model.BetDOCondition;
import com.ds.zxm.service.BetService;
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
import java.text.ParseException;
import java.util.*;


@Controller
public class LotteryController {

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LotteryController.class);
    @Autowired
    private BetService betService;

    @ResponseBody
    @RequestMapping(value = "/betCP", method = {RequestMethod.POST})
    public String initdata(@RequestParam(required = true, value = "caipiao") String caipiao,
                           @RequestParam(required = true, value = "no") String no,
                           @RequestParam(required = true, value = "data") String data) {

        String result = "";
        try {

            System.out.println(no + "投注:" + data);
            String next1 = LotteryUtil.getNextAwardNo(no);
            String next2 = LotteryUtil.getNextAwardNo(next1);
            String next3 = LotteryUtil.getNextAwardNo(next2);
            String next4 = LotteryUtil.getNextAwardNo(next3);
            String next5 = LotteryUtil.getNextAwardNo(next4);
            String next6 = LotteryUtil.getNextAwardNo(next5);

            String startPostfix = next1.substring(next1.length() -3);
            String endPostfix =  next6.substring(next6.length() -3);
            log.info(startPostfix + "-" + endPostfix+"期" + data  + " ---");

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
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/checkRecall", method = {RequestMethod.POST})
    public boolean checkRecall(@RequestParam(required = true, value = "caipiao") String caipiao) {
        //判断前台需要重新出号的条件.1.已中。2过了追号期
        boolean result = true;
        try {
            Thread.sleep(5*1000);
            updateLotteryStatus(caipiao);

            if("chongqing".equals(caipiao)) {
                BetDOCondition betDOCondition = new BetDOCondition();
                betDOCondition.createCriteria().andLotteryCodeEqualTo(caipiao).andStatusEqualTo("1");
                List<BetDO> betDOList = betService.queryBetList(betDOCondition);
                if(betDOList != null && betDOList.size() > 0) {
                    result = false;
                }
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        if (result) {
            log.info("当期方案已完成，刷新页面");
        }
        return result;
    }

    private void updateLotteryStatus(String caipiao) throws ParseException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("caipiao", caipiao);
        String result = HttpUtil.doPost("http://www.ds018.com/caipiao/kline/init", map, "utf-8");

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

            BetDOCondition bdd = new BetDOCondition();
            for(BetDO item : betDOList) {
                bdd.createCriteria().andIdEqualTo(item.getId());
                //匹配当期期是否中奖或者已经是最后一期
                if ("3".equals(item.getBetType())) {
                    if(curNO.equals(item.getEndNo())
                            ||LotteryUtil.compareCQAwardNO(item.getEndNo(), curNO) < 0
                            ||LotteryUtil.compareCQAwardNO(item.getStartNo(), LotteryUtil.getNextAwardNo(curNO)) > 0 ) {
                        //最后一期仍然未中奖。或者已经过了开奖期
                        item.setStatus("2");
                        log.info(item .getSeqNo() + "未中奖");
                        betService.updateBetDO(item, bdd);
                    }
                    else {
                        if (item.getBetNo().indexOf(prize.substring(prize.length()  -3) ) > 0) {
                            item.setStatus("3");
                            item.setPrizeNo(curNO);
                            log.info(item.getSeqNo() + "已中奖");
                            betService.updateBetDO(item, bdd);
                        } else {
                            log.info(item.getSeqNo() + "第" + curNO + "期未中奖，等待下一期");
                        }
                    }
                }
            }
        }
    }

}
