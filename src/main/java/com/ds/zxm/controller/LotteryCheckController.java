package com.ds.zxm.controller;

import com.ds.zxm.model.GenPrizeModel;
import com.ds.zxm.service.LotteryGenService;
import com.ds.zxm.service.LotteryPrizeScheduleService;
import com.ds.zxm.service.LotteryStrategyService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/check")
public class LotteryCheckController {

	Logger log = Logger.getLogger(LotteryCheckController.class);
	@Autowired
	private LotteryStrategyService lotteryStrategyService;
	@Autowired
	private LotteryGenService lotteryGenService;

	@ResponseBody
	@RequestMapping(value = "/checkStrategy", method = {RequestMethod.GET})
	public Map getLatestGenPrize(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "start") String start
			, @RequestParam(required = true, value = "end") String end, @RequestParam(required = true, value = "type") String type, @RequestParam(required = true, value = "rate") Integer rate) {
		Map<String ,Integer> loseCnt = null;

		try {
			loseCnt =  lotteryStrategyService.checkLotteryStrategy(start, end, lotteryCode, type, rate);
		} catch (Exception e) {
			log.error("getLatestGenPrize error",e );
		}
		return loseCnt;
	}
}
