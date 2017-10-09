package com.ds.zxm.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ds.zxm.model.Lottery;
import com.ds.zxm.model.User;
import com.ds.zxm.service.LotteryService;
import com.ds.zxm.service.UserService;

/**
 * Created by zl on 2015/8/27.
 */
@Controller
public class LotteryController {

    private Logger logger = Logger.getLogger(LotteryController.class);

    @Autowired
    private LotteryService lotteryService;

    @RequestMapping("/getLotteryInfo")
    @ResponseBody
    public Lottery  getUserInfo() {
    	Lottery lottery = lotteryService.getLotteryInfo();
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
}
