package com.bugbycode.controller.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bugbycode.service.console.ConsoleService;

@RequestMapping("/api")
@Controller
public class ConsoleController {
	
	private static final Logger logger = LogManager.getLogger(ConsoleController.class);
	
	@Autowired
	private ConsoleService consoleService;
	
	@RequestMapping("/getConnHost")
	@ResponseBody
	public String getConnHost() {
		return consoleService.getChannelHost().toString();
	}
	
	@RequestMapping("/getProxyInfo")
	@ResponseBody
	public String getProxyInfo(String clientId,String host,int port,boolean closeApp) {
		logger.info("clientId : " + clientId);
		JSONObject json = new JSONObject();
		try {
			json = consoleService.getChannelInfo(clientId, host, port, closeApp);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
