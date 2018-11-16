package com.ssc.controller;

import com.ssc.model.*;
import com.ssc.service.LotteryGenService;
import com.ssc.service.PrizeService;
import com.ssc.model.MissedPrizeResult;
import com.ssc.model.TCFFCPRIZE;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/prize")
public class PrizeController {

	Logger log = Logger.getLogger(PrizeController.class);
	@Autowired
	private PrizeService prizeService;
	@Autowired
	private LotteryGenService lotteryGenService;

	@ResponseBody
	@RequestMapping(value = "/getLatestPrize", method = {RequestMethod.GET})
	public List getLatestGenPrize(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "limit") Integer limit) {
		List<TCFFCPRIZE> result = new ArrayList<>();
		try {
			result = prizeService.queryLatestPrize(limit);
		} catch (Exception e) {
			log.error("getLatestGenPrize error",e );
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getLatestPrizeMissCnt", method = {RequestMethod.GET})
	public MissedPrizeResult getLatestPrizeMissCnt(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "limit") Integer limit,
                                                   @RequestParam(required = true, value = "type") String type, @RequestParam(required = true, value = "latest1") Integer latest1
			, @RequestParam(required = true, value = "latest2") Integer latest2
			, @RequestParam(required = true, value = "coldLimit") Integer coldLimit) {
		MissedPrizeResult  result = null;
		try {
			//result = prizeService.getLatestPrizeMissCnt(limit,type);
			result = prizeService.getLatestPrizeMissCntByorder(limit,type,latest1, latest2,coldLimit);

		} catch (Exception e) {
			log.error("getLatestGenPrize error",e );
		}
		return result;
	}

	public  static void main(String[] args) {
		System.out.println(String.valueOf(null));
	}
}
