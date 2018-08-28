package com.ds.zxm.controller;

import com.ds.zxm.constants.BaseConstants;
import com.ds.zxm.model.CurNoModel;
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
	public Map getLatestGenPrize(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "signCode") String signCode) {
		Map<String,Object> result = new HashMap<>();
		if (StringUtils.isEmpty(lotteryCode)) {
			result.put("code", 201);
			return result;
		}
		if (StringUtils.isEmpty(signCode) || !signCode.equals("201613gn7ew")) {
			result.put("code", 202);
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

	@ResponseBody
	@RequestMapping(value = "/getScheData", method = {RequestMethod.GET})
	public Map getScheData(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "signCode") String signCode
			, @RequestParam(required = true, value = "type") String type) {
		Map<String,Object> result = new HashMap<>();
		StringBuffer strResult = new StringBuffer();
		if (StringUtils.isEmpty(lotteryCode)) {
			result.put("code", 201);
			return result;
		}
		if (StringUtils.isEmpty(signCode) || !signCode.equals("111222")) {
			result.put("code", 202);
			return result;
		}
		try {
			Map<String, Object> map= new HashMap<>();
			map.put("lotteryCode", lotteryCode);
			map.put("type", type);
			GenPrizeModel genPrizeModel = lotteryGenService.getLatestGenPrize(map);
			if (null != genPrizeModel) {
				strResult.append("[0000]");
				strResult.append("["+genPrizeModel.getNo()+"]");
				String nums = "";
				if(type.equals(BaseConstants.WF_TYPE_DWD_GE_JC)){

					nums = "[定位胆后一]";
					String prize = genPrizeModel.getGenPrize().replace(","," ");
					nums = nums +"|["+ prize+"]";
				}
				result.put("msg", "[成功]");
				result.put("no", "["+genPrizeModel.getNo()+"]");
				result.put("code", "[0000]");
				result.put("lastPrizedStatus", "["+ genPrizeModel.getIsPrized()+"]");
				result.put("nums", nums);
				strResult.append(nums);

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

	@ResponseBody
	@RequestMapping(value = "/getLatestPrize", method = {RequestMethod.GET})
	public Map getLatestPrize(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "signCode") String signCode) {
		Map<String,Object> result = new HashMap<>();
		if (StringUtils.isEmpty(lotteryCode)) {
			result.put("code", 201);
			return result;
		}
		if (StringUtils.isEmpty(signCode) || !signCode.equals("ok")) {
			result.put("code", 202);
			return result;
		}
		try {
			CurNoModel prize = prizeService.queryCurNoPrize(lotteryCode);
			if (null != prize) {
				result.put("no", prize.getCurNo());
				result.put("prize", prize.getPrize());
				result.put("code", 200);
				result.put("msg", "成功");
			} else  {
				result.put("code", 199);
				result.put("msg", "尚未出号，等等");
			}
		} catch (Exception e) {
			result.put("code", 202);
			log.error("getLatestPrize error",e );
		}
		return result;
	}
}
