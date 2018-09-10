package com.bugbycode.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bugbycode.mongodb.module.CustomProxyHost;
import com.bugbycode.mongodb.service.host.CustomHostService;
import com.util.StringUtil;
import com.util.page.SearchResult;

@RequestMapping("/api")
@Controller
public class HostController {
	
	@Autowired
	private CustomHostService customHostService;
	
	@RequestMapping("/host/query")
	@ResponseBody
	public SearchResult<CustomProxyHost> query(String keyword,
			@RequestParam(name="startIndex",defaultValue="0")
			int startIndex,
			@RequestParam(name="pageSize",defaultValue="-1")
			int pageSize){
		SearchResult<CustomProxyHost> sr = null;
		if(pageSize == -1) {
			sr = new SearchResult<CustomProxyHost>();
			List<CustomProxyHost> list = customHostService.query(keyword);
			sr.setList(list);
		}else {
			sr = customHostService.query(keyword, startIndex, pageSize);
		}
		return sr;
	}
	
	@RequestMapping("/host/create")
	@ResponseBody
	public Map<String,Object> create(String name,String ip,
			@RequestParam(name="port",defaultValue="50000")
			int port){
		Map<String,Object> map = new HashMap<String,Object>();
		CustomProxyHost host = new CustomProxyHost();
		host.setIp(ip);
		host.setName(name);
		host.setPort(port);
		
		CustomProxyHost tmp = customHostService.queryByName(name);
		String _id = "";
		if(tmp == null) {
			_id = customHostService.insert(host);
		}
		int code = 0;
		String msg = "Create host success.";
		if(StringUtil.isBlank(_id)) {
			code = 1;
			msg = "Create host failed.";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
	
	@RequestMapping("/host/updateById")
	@ResponseBody
	public Map<String,Object> updateById(
			@RequestParam(name="_id",defaultValue="")
			String _id,
			String name,String ip,
			@RequestParam(name="port",defaultValue="50000")
			int port) {
		Map<String,Object> map = new HashMap<String,Object>();
		CustomProxyHost host = customHostService.queryById(_id);
		long row = 0;
		if(host != null) {
			CustomProxyHost tmp = customHostService.queryByName(name);
			if(tmp == null || tmp.get_id().equals(_id)) {
				host.setIp(ip);
				host.setName(name);
				host.setPort(port);
				if(StringUtil.isNotBlank(_id)) {
					row = customHostService.update(host);
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
		CustomProxyHost host = customHostService.queryByName(name);
		long row = 0;
		if(host != null) {
			String id = host.get_id();
			row = customHostService.delete(id);
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
		CustomProxyHost host = customHostService.queryByName(name);
		int code = 0;
		if(host == null) {
			code = 1;
		}
		map.put("code", code);
		map.put("data", host);
		return map;
	}
	
	@RequestMapping("/host/queryById")
	@ResponseBody
	public Map<String,Object> queryById(
			@RequestParam(name="_id",defaultValue="")
			String _id){
		Map<String,Object> map = new HashMap<String,Object>();
		CustomProxyHost host = customHostService.queryById(_id);
		int code = 0;
		if(host == null) {
			code = 1;
		}
		map.put("code", code);
		map.put("data", host);
		return map;
	}
	
	@RequestMapping("/host/deleteById")
	@ResponseBody
	public Map<String,Object> deleteById(
			@RequestParam(name="_id",defaultValue="")
			String _id){
		Map<String,Object> map = new HashMap<String,Object>();
		CustomProxyHost host = customHostService.queryById(_id);
		int code = 0;
		String msg = "Delete host success.";
		if(host != null) {
			long row = customHostService.delete(_id);
			if(row == 0) {
				code = 1;
				msg = "Delete host failed.";
			}
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
}
