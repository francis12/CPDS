package com.ds.zxm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LotteryController {

    @ResponseBody
    @RequestMapping(value = "/betCP", method = {RequestMethod.POST})
    public String initdata(@RequestParam(required = true, value = "caipiao") String caipiao,
                           @RequestParam(required = true, value = "no") String no,
                           @RequestParam(required = true, value = "data") String data) {

        String result = "";
        try {

            System.out.println(no + "投注:" + data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
