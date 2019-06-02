package com.ssc.controller;

import com.ssc.constants.BaseConstants;
import com.ssc.model.*;
import com.ssc.service.LotteryGenService;
import com.ssc.service.PrizeService;
import com.ssc.model.MissedPrizeResult;
import com.ssc.model.TCFFCPRIZE;
import com.ssc.service.PrizeStrategyService;
import com.ssc.vo.BetingDetail;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/prize")
public class PrizeController {

	Logger log = Logger.getLogger(PrizeController.class);
	@Autowired
	private PrizeService prizeService;
	@Autowired
	private LotteryGenService lotteryGenService;
	@Autowired
	private PrizeStrategyService prizeStrategyService;

	@ResponseBody
	@RequestMapping(value = "/getLatestPrize", method = {RequestMethod.GET})
	public List getLatestGenPrize(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "limit") Integer limit, String prizeType) {
		List<TCFFCPRIZE> result = new ArrayList<>();
		if (StringUtils.isEmpty(prizeType)) {
			prizeType = BaseConstants.PRIZE_TYPE_77TJ;
		}
		try {
			result = prizeService.queryLatestPrize(limit, prizeType);
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
			//特殊处理type ： -1  代表个位下一期号码大小单双
			result = prizeService.getLatestPrizeMissCntByorder(limit,type,latest1, latest2,coldLimit);

		} catch (Exception e) {
			log.error("getLatestGenPrize error",e );
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getLatestGeNextPrize", method = {RequestMethod.GET})
	public Map<String, List<TCFFCPRIZE>> getLatestGeNextPrize(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "limit") Integer limit,
															  @RequestParam(required = true, value = "type") String type, String prizeType) {
		Map<String, List<TCFFCPRIZE>>   result = null;
		try {
			if (StringUtils.isEmpty(prizeType)) {
				prizeType = BaseConstants.PRIZE_TYPE_77TJ;
			}
			//result = prizeService.getLatestPrizeMissCnt(limit,type);
			//特殊处理type ： -1  代表个位下一期号码大小单双
			result = prizeService.getLatestGeNextPrize(limit,type, prizeType);
		} catch (Exception e) {
			log.error("getLatestGeNextPrize error",e );
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/checkS", method = {RequestMethod.GET})
	public BetingDetail getLatestGenPrize(@RequestParam(required = true, value = "start") String start
			, @RequestParam(required = true, value = "end") String end) {
		BetingDetail betingDetail = null;
		try {
			betingDetail = prizeStrategyService.check(start, end);
		} catch (Exception e) {
			log.error("getLatestGenPrize error",e );
		}
		return betingDetail;
	}

	public  static void main(String[] args) {
		System.out.println(String.valueOf(null));
	}
}
