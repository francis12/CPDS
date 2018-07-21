package com.ds.zxm.controller;

import com.ds.zxm.model.GenPrizeModel;
import com.ds.zxm.service.ChartService;
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
@RequestMapping("/chart")
public class ChartController {

	Logger log = Logger.getLogger(ChartController.class);
	@Autowired
	private ChartService chartService;

	@ResponseBody
	@RequestMapping(value = "/tecentOnlineData", method = {RequestMethod.GET})
	public Map<String, Object> tecentOnlineData( @RequestParam(required = true, value = "limit") Integer limit) {
		Map<String, Object> option = null;
		try {
			option = chartService.queryTecentOnlineData2Option(limit);
		} catch (Exception e) {
			log.error("tecentOnlineData error");
		}
		return option;
	}
}
