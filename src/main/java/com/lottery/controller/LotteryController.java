package com.lottery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.service.ILotteryService;
import com.lottery.strategy.ILotteryStrategy;

@Controller
@RequestMapping("/lottery")
public class LotteryController {

	@Autowired
	private ILotteryService lotteryService;   
    @Autowired
    @Qualifier("Last1LotteryStrategy")
    private ILotteryStrategy lotteryStrategy;
	@RequestMapping
	private ModelAndView init(ModelMap map){
		ModelAndView mv = new ModelAndView("stockList");
		return mv;
	}
	@RequestMapping("/fetchLottery")
	private void fetchLottery(
            @RequestParam("code") String code, @RequestParam("no") String no) {
		try {
			lotteryService.fetchLotteryInfo(code, no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/runStrategy")
	private void runStrategy(
            @RequestParam("startNo") String startNo, @RequestParam("endNo") String endNo) {
		try {
			lotteryStrategy.runStrategy(startNo, endNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
