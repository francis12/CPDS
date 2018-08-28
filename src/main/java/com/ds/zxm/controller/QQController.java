package com.ds.zxm.controller;

import com.ds.zxm.service.ChartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class QQController {

	Logger log = Logger.getLogger(QQController.class);

	@RequestMapping(value = "/qq", method = {RequestMethod.GET})
	public String tecentOnlineData() {
		return "qq";
	}
}
