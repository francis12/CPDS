package com.ds.zxm.controller;

import com.ds.zxm.service.LotteryDetailService;
import com.ds.zxm.service.TecentOnlineService;
import com.ds.zxm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;

@Controller
public class FetchDataController {

    @Autowired
    LotteryDetailService lotteryDetailService;
    @Autowired
    TecentOnlineService tecentOnlineService;

    @ResponseBody
    @RequestMapping(value = "/qqFetchData", method = {RequestMethod.GET})
    public String lotterdata(@RequestParam(required = true, value = "start") String start,
                             @RequestParam(required = true, value = "end") String end) {
        try {
            Date startDate = DateUtils.String2Date(start, "yyyyMMdd");
            Date endDate = DateUtils.String2Date(end, "yyyyMMdd");
            lotteryDetailService.fetchQQffcData(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "error," + e.getMessage();
        }
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = "/tecentOnlineFetchData", method = {RequestMethod.GET})
    public String tecentOnlineData(@RequestParam(required = true, value = "start") int start,
                             @RequestParam(required = true, value = "end") int end) {
        try {
            tecentOnlineService.fetchTecentOnlineData(start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return "error," + e.getMessage();
        }
        return "ok";
    }
}
