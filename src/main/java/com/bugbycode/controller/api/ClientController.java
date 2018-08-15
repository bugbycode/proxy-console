package com.bugbycode.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bugbycode.module.client.ProxyClientDetail;
import com.bugbycode.service.client.ProxyClientService;
import com.util.page.SearchResult;

@RequestMapping("/api")
@Controller
public class ClientController {
	
	@Autowired
	private ProxyClientService proxyClientService;
	
	@RequestMapping("/query")
	public SearchResult<ProxyClientDetail> query(String alias,String keyword,
			@RequestParam(name="startIndex",defaultValue="0")
			int startIndex,
			@RequestParam(name="pageSize",defaultValue="-1")
			int pageSize){
		SearchResult<ProxyClientDetail> sr = null;
		
		if(pageSize == -1) {
			sr = new SearchResult<ProxyClientDetail>();
			List<ProxyClientDetail> list = proxyClientService.query(alias, keyword);
			sr.setList(list);
		}else {
			sr = proxyClientService.query(alias, keyword, startIndex, pageSize);
		}
		
		return sr;
	}
}
