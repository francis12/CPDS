package com.ssc.controller;

import com.ssc.service.LotteryPrizeScheduleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/prize")
public class FetchPrizeData {

	Logger log = Logger.getLogger(FetchPrizeData.class);
	@Autowired
	private LotteryPrizeScheduleService prizeService;

	@ResponseBody
	@RequestMapping(value = "/fetchTcffcData", method = {RequestMethod.GET})
	public String fetchTcffcData(@RequestParam(required = true, value = "start") int start,
								 	@RequestParam(required = true, value = "end") int end) {
		String result = "";
		try {
			prizeService.batchFetchTCFFCData(start, end);
		} catch (Exception e) {
			log.error("initdata error");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/batchFetchTCFFCDATAFromTecentOnline", method = {RequestMethod.GET})
	public String batchFetchTCFFCDATAFromTecentOnline(@RequestParam(required = true, value = "start") int start,
								 @RequestParam(required = true, value = "end") int end,
								 @RequestParam(required = true, value = "size") int size) {
		String result = "";
		try {
			prizeService.batchFetchTCFFCDATAFromTecentOnline(start, end, size);
		} catch (Exception e) {
			log.error("initdata error");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/batchFetchOnlineDataFrom77OrgLogFile", method = {RequestMethod.GET})
	public String batchFetchOnlineDataFrom77OrgLogFile(@RequestParam(required = true, value = "start") String start,
													  @RequestParam(required = true, value = "end") String end) {
		String result = "";
		try {
			prizeService.batchFetchOnlineDataFrom77OrgLogFile(start, end);
		} catch (Exception e) {
			log.error("batchFetchTCFFCDATAFromTecentOnline error",e);
		}
		return result;
	}
}
