package com.ssc.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QQController {

	Logger log = Logger.getLogger(QQController.class);

	@RequestMapping(value = "/qq", method = {RequestMethod.GET})
	public String tecentOnlineData() {
		return "qq";
	}
}
