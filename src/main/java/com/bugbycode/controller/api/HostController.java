package com.bugbycode.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bugbycode.module.host.ProxyHost;
import com.bugbycode.service.host.ProxyHostService;
import com.util.page.SearchResult;

@RequestMapping("/api")
@Controller
public class HostController {
	
	@Autowired
	private ProxyHostService proxyHostService;
	
	@RequestMapping("/host/query")
	@ResponseBody
	public SearchResult<ProxyHost> query(String keyword,
			@RequestParam(name="startIndex",defaultValue="0")
			int startIndex,
			@RequestParam(name="pageSize",defaultValue="-1")
			int pageSize){
		SearchResult<ProxyHost> sr = null;
		if(pageSize == -1) {
			sr = new SearchResult<ProxyHost>();
			List<ProxyHost> list = proxyHostService.query(keyword);
			sr.setList(list);
		}else {
			sr = proxyHostService.query(keyword, startIndex, pageSize);
		}
		return sr;
	}
	
	@RequestMapping("/host/create")
	@ResponseBody
	public Map<String,Object> create(String name,String ip,
			@RequestParam(name="port",defaultValue="50000")
			int port){
		Map<String,Object> map = new HashMap<String,Object>();
		ProxyHost host = new ProxyHost();
		host.setIp(ip);
		host.setName(name);
		host.setPort(port);
		
		ProxyHost tmp = proxyHostService.queryByName(name);
		int row = 0;
		if(tmp == null) {
			row = proxyHostService.insert(host);
		}
		int code = 0;
		String msg = "Create host success.";
		if(row == 0) {
			code = 1;
			msg = "Create host failed.";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
	
	@RequestMapping("/host/updateByName")
	@ResponseBody
	public Map<String,Object> updateByName(
			@RequestParam(name="id",defaultValue="0")
			int id,
			String name,String ip,
			@RequestParam(name="port",defaultValue="50000")
			int port) {
		Map<String,Object> map = new HashMap<String,Object>();
		ProxyHost host = proxyHostService.queryById(id);
		int row = 0;
		if(host != null) {
			ProxyHost tmp = proxyHostService.queryByName(name);
			if(tmp == null || tmp.getId() == id) {
				host.setIp(ip);
				host.setName(name);
				host.setPort(port);
				if(id > 0) {
					row = proxyHostService.update(host);
				}
			}
		}
		int code = 0;
		String msg = "Update host success.";
		if(row == 0) {
			code = 1;
			msg = "Update host failed.";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
	
	@RequestMapping("/host/deleteByName")
	@ResponseBody
	public Map<String,Object> deleteByName(
			String name){
		Map<String,Object> map = new HashMap<String,Object>();
		ProxyHost host = proxyHostService.queryByName(name);
		int row = 0;
		if(host != null) {
			int id = host.getId();
			if(id > 0) {
				row = proxyHostService.delete(id);
			}
		}
		int code = 0;
		String msg = "Delete host success.";
		if(row == 0) {
			code = 1;
			msg = "Delete host failed.";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
	
	@RequestMapping("/host/queryByName")
	@ResponseBody
	public Map<String,Object> queryByName(String name){
		Map<String,Object> map = new HashMap<String,Object>();
		ProxyHost host = proxyHostService.queryByName(name);
		int code = 0;
		if(host == null) {
			code = 1;
		}
		map.put("code", code);
		map.put("data", host);
		return map;
	}
}
