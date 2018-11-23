package com.bugbycode.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bugbycode.service.console.ConsoleService;

@RequestMapping("/api")
@Controller
public class ScanController {
	
	@Autowired
	private ConsoleService consoleService;
	
	@RequestMapping("/scan/scanOs")
	@ResponseBody
	public String scanOs(HttpServletRequest req,String clientId,String host) {
		return consoleService.scanHost(req.getServerName(),clientId, host);
	}
}
