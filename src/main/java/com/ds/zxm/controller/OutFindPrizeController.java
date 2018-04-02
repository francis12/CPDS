package com.ds.zxm.controller;

import com.ds.zxm.model.GenPrizeModel;
import com.ds.zxm.service.LotteryGenService;
import com.ds.zxm.service.LotteryPrizeScheduleService;
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
@RequestMapping("/gen")
public class OutFindPrizeController {

	Logger log = Logger.getLogger(OutFindPrizeController.class);
	@Autowired
	private LotteryPrizeScheduleService prizeService;
	@Autowired
	private LotteryGenService lotteryGenService;

	@ResponseBody
	@RequestMapping(value = "/getLatestGenPrize", method = {RequestMethod.GET})
	public Map getLatestGenPrize(@RequestParam(required = true, value = "lotteryCode") String lotteryCode) {
		Map<String,Object> result = new HashMap<>();
		if (StringUtils.isEmpty(lotteryCode)) {
			result.put("code", 201);
			return result;
		}
		try {
			GenPrizeModel genPrizeModel = lotteryGenService.getLatestGenPrize(lotteryCode);
			if (null != genPrizeModel) {
				result.put("no", genPrizeModel.getNo());
				result.put("genPrize", genPrizeModel.getGenPrize());
				result.put("code", 200);
				result.put("msg", "成功");
			} else  {
				result.put("code", 199);
				result.put("msg", "尚未出号，等等");
			}
		} catch (Exception e) {
			result.put("code", 202);
			log.error("getLatestGenPrize error",e );
		}
		return result;
	}
}
