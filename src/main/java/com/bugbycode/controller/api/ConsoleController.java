package com.bugbycode.controller.api;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
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
	public String getConnHost(HttpServletRequest req) {
		JSONObject json = consoleService.getChannelHost();
		if(json.length() == 0) {
			try {
				json.put("ip", req.getServerName());
				json.put("port", 50000);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return json.toString();
	}
	
	@RequestMapping("/getProxyInfo")
	@ResponseBody
	public String getProxyInfo(HttpServletRequest req,String clientId,
			String host,int port,boolean closeApp) {
		logger.info("clientId : " + clientId);
		String serverName = req.getServerName();
		JSONObject json = new JSONObject();
		try {
			json = consoleService.getChannelInfo(serverName,clientId, host, port, closeApp);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
