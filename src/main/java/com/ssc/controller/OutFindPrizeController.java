package com.ssc.controller;

import com.ssc.constants.BaseConstants;
import com.ssc.model.CurNoModel;
import com.ssc.model.GenPrizeModel;
import com.ssc.model.GenPrizeModelCondition;
import com.ssc.service.LotteryGenService;
import com.ssc.service.LotteryPrizeScheduleService;
import com.ssc.util.LotteryUtil;
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
	public Map getLatestGenPrize(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "signCode") String signCode,
								 @RequestParam(required = true, value = "no") String no, @RequestParam(required = true, value = "type") String type) {
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
			GenPrizeModelCondition condition = new GenPrizeModelCondition();
			condition.createCriteria().andLotteryCodeEqualTo(lotteryCode).andNoEqualTo(no).andTypeEqualTo(type);
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
	@RequestMapping(value = "/getScheData2s", method = {RequestMethod.GET})
	public Map getScheData(@RequestParam(required = true, value = "lotteryCode") String lotteryCode, @RequestParam(required = true, value = "signCode") String signCode
			, @RequestParam(required = true, value = "type") String type) {
		Map<String,Object> result = new HashMap<>();
		StringBuffer strResult = new StringBuffer();
		if (StringUtils.isEmpty(lotteryCode)) {
			result.put("code", 201);
			return result;
		}
		if (StringUtils.isEmpty(signCode) || !signCode.equals("q123")) {
			result.put("code", 202);
			return result;
		}
		try {
			Map<String, Object> map= new HashMap<>();
			map.put("lotteryCode", lotteryCode);
			map.put("type",  type);
			GenPrizeModel genPrizeModel = lotteryGenService.getLatestGenPrize(map);
			if (null != genPrizeModel) {
				strResult.append("[0000]");
				strResult.append("["+genPrizeModel.getNo()+"]");
				String nums = "";
				if(type.equals(BaseConstants.WF_TYPE_DWD_GE_JC)){
					nums = "[定位胆后一]";
					String prize = genPrizeModel.getGenPrize().replace(","," ");
					nums = nums +"|["+ prize+"]";
				}else if(type.equals(BaseConstants.WF_TYPE_DWD_GE2_JC)){
					nums = "[定位胆后一]";
					String prize = LotteryUtil.getRandomNums(9, 7);
					nums = nums +"|["+ prize+"]";
					genPrizeModel.setIsPrized("...");
				}else if(type.equals(BaseConstants.WF_TYPE_DWD_SHI_JC)){
                    nums = "[定位胆-十]";
                    String prize = LotteryUtil.getRandomNums(9, 7);
                    nums = nums +"|["+ prize+"]";
                    genPrizeModel.setIsPrized("...");
                }else if(type.equals(BaseConstants.WF_TYPE_DWD_SHI2_JC)){
					nums = "[定位胆-十]";
					String prize = LotteryUtil.getRandomNums(9, 7);
					nums = nums +"|["+ prize+"]";
					genPrizeModel.setIsPrized("...");
				}else if(type.equals(BaseConstants.WF_TYPE_DWD_BAI_JC)){
                    nums = "[定位胆-百]";
                    String prize = LotteryUtil.getRandomNums(9, 7);
                    nums = nums +"|["+ prize+"]";
                    genPrizeModel.setIsPrized("...");
                }else if(type.equals(BaseConstants.WF_TYPE_DWD_BAI2_JC)){
					nums = "[定位胆-百]";
					String prize = LotteryUtil.getRandomNums(9, 7);
					nums = nums +"|["+ prize+"]";
					genPrizeModel.setIsPrized("...");
				}else if(type.equals(BaseConstants.WF_TYPE_S_DWD_QIAN_JC)){
                    nums = "[定位胆-千]";
					String prize = genPrizeModel.getGenPrize().replace(","," ");
					nums = nums +"|["+ prize+"]";
                }else if(type.equals(BaseConstants.WF_TYPE_DWD_QIAN2_JC)){
					nums = "[定位胆-千]";
					String prize = LotteryUtil.getRandomNums(9, 7);
					nums = nums +"|["+ prize+"]";
					genPrizeModel.setIsPrized("...");
				}else if(type.equals(BaseConstants.WF_TYPE_DWD_QIAN_JC)){
					nums = "[定位胆-千]";
					String prize = LotteryUtil.getRandomNums(9, 7);
					nums = nums +"|["+ prize+"]";
					genPrizeModel.setIsPrized("...");
				}else if(type.equals(BaseConstants.WF_TYPE_DWD_WAN_JC)){
					nums = "[定位胆-万]";
					String prize = LotteryUtil.getRandomNums(9, 7);
					nums = nums +"|["+ prize+"]";
					genPrizeModel.setIsPrized("...");
				}else if(type.equals(BaseConstants.WF_TYPE_DWD_WAN2_JC)){
					nums = "[定位胆-万]";
					String prize = LotteryUtil.getRandomNums(9, 7);
					nums = nums +"|["+ prize+"]";
					genPrizeModel.setIsPrized("...");
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

	public static void main(String[] args) {
		int[] nums = new int[10];
		int lucky;
		for (int i = 0; i < nums.length; i++) {
			do {
				lucky = (int)(Math.random()*20) + 1;
			} while (isExist(nums, lucky));
			nums[i] = lucky;
		}
		for (int i : nums) {
			System.out.print(i+ " ");
		}
	}
	// 判断是否已经在数组中存在该元素
	public static boolean isExist(int[] array, int key){
		for (int i = 0; i < array.length; i++) {
			if (array[i] == key) {
				return true;
			}
		}
		return false;
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
