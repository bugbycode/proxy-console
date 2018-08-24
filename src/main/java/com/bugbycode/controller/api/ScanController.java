package com.bugbycode.controller.api;

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
	public String scanOs(String clientId,String host) {
		return consoleService.scanHost(clientId, host);
	}
}
