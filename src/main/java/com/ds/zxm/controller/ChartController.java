package com.ds.zxm.controller;

import com.ds.zxm.service.ChartService;
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
	public Map<String, Object> tecentOnlineData( @RequestParam(value = "limit") Integer limit,
												 @RequestParam(value = "date") String date) {
		Map<String, Object> option = null;
		try {
			option = chartService.queryTecentOnlineData2Option(limit, date,0);
		} catch (Exception e) {
			log.error("tecentOnlineData error");
		}
		return option;
	}

	@ResponseBody
	@RequestMapping(value = "/tecentOnlineDataByInterval", method = {RequestMethod.GET})
	public Map<String, Object> tecentOnlineDataByInterval( @RequestParam(value = "startNo") String startNo,
												 @RequestParam(value = "endNo") String endNo) {
		Map<String, Object> option = null;
		try {
			option = chartService.queryTecentOnlineDataByInterval2Option(startNo, endNo);
		} catch (Exception e) {
			log.error("tecentOnlineData error");
		}
		return option;
	}

	@ResponseBody
	@RequestMapping(value = "/tecentOnlineDanData", method = {RequestMethod.GET})
	public Map<String, Object> tecentOnlineDanData( @RequestParam(value = "limit") Integer limit,
												 @RequestParam(value = "date") String date) {
		Map<String, Object> option = null;
		try {
			option = chartService.queryTecentOnlineDanData2Option(limit, date,0);
		} catch (Exception e) {
			log.error("tecentOnlineData error");
		}
		return option;
	}


	@ResponseBody
	@RequestMapping(value = "/tecentOnlineGeDsData", method = {RequestMethod.GET})
	public Map<String, Object> tecentOnlineGeDsData( @RequestParam(value = "limit") Integer limit,
													 @RequestParam(value = "date") String date) {
		Map<String, Object> option = null;
		try {
			option = chartService.queryTecentOnlineData2Option(limit, date,1);
		} catch (Exception e) {
			log.error("tecentOnlineData error");
		}
		return option;
	}

	//   type:0 - 波动个位双， 1 - 开奖个位双 ， 2 - 波动十位双， 3 - 开奖百位双 ， 4 - 波动千位双， 5 - 波动千位双
	@ResponseBody
	@RequestMapping(value = "/tecentOnlineDataByType", method = {RequestMethod.GET})
	public Map<String, Object> tecentOnlineDataByType( @RequestParam(value = "limit") Integer limit,
													 @RequestParam(value = "date") String date,
													   @RequestParam(value = "type") Integer type) {
		Map<String, Object> option = new HashMap<>();
		try {
			option = chartService.queryTecentOnlineDanData2Option(limit, date,type);
		} catch (Exception e) {
			log.error("tecentOnlineData error",e );
		}
		return option;
	}
}
