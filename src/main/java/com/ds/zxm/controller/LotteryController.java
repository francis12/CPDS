package com.ds.zxm.controller;

import com.ds.zxm.model.LotteryDO;
import com.ds.zxm.strategy.ILotteryStrategy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ds.zxm.service.LotteryService;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zl on 2015/8/27.
 */
@Controller
public class LotteryController {/*

    private Logger logger = Logger.getLogger(LotteryController.class);

    @Autowired
    @Qualifier("Last1LotteryStrategy")
    private ILotteryStrategy lotteryStrategy;
    @Autowired
    private LotteryService lotteryService;

    @RequestMapping("/getLotteryInfo")
    @ResponseBody
    public LotteryDO getUserInfo() {
        LotteryDO lottery = new LotteryDO();
        //lotteryService.getLotteryInfo();
        if(lottery!=null){
            System.out.println("user.getName():"+lottery.getLotteryName());
        }
        return lottery;
    }

    @RequestMapping("/betCP")
    @ResponseBody
    public String  betCPData(String caipiao, String no, String data) {
        System.out.println(caipiao + "," + no + "," + data);
        return "{'bet':'suc'}";
    }


    @RequestMapping
    private ModelAndView init(ModelMap map){
        ModelAndView mv = new ModelAndView("stockList");
        return mv;
    }
    @RequestMapping("/fetchLottery")
    private void fetchLottery(
            @RequestParam("code") String code, @RequestParam("no") String no) {
        try {
            lotteryService.fetchLotteryInfo(code, no);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @RequestMapping("/runStrategy")
    private void runStrategy(
            @RequestParam("startNo") String startNo, @RequestParam("endNo") String endNo) {
        try {
            lotteryStrategy.runStrategy(startNo, endNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
