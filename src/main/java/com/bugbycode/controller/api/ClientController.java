package com.bugbycode.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bugbycode.module.client.ProxyClientDetail;
import com.bugbycode.service.client.ProxyClientService;
import com.util.RandomUtil;
import com.util.StringUtil;
import com.util.page.SearchResult;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
@RequestMapping("/api")
@Controller
public class ClientController {

	private BASE64Encoder encode = new BASE64Encoder();
	
	@Autowired
	private ProxyClientService proxyClientService;
	
	@RequestMapping("/client/query")
	@ResponseBody
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
	
	@RequestMapping("/client/create")
	@ResponseBody
	public Map<String,Object> create(String name,String alias){
		Map<String,Object> map = new HashMap<String,Object>();
		ProxyClientDetail client = new ProxyClientDetail();
		client.setAlias(alias);
		client.setName(name);
		client.setClientId(encode.encode(RandomUtil.GetGuid32().getBytes()));
		client.setClientSecret(encode.encode(RandomUtil.GetGuid32().getBytes()));
		client.setScope("agent");
		client.setGrantType("client_credentials");
		
		ProxyClientDetail tmp = proxyClientService.queryByName(name);
		int row = 0;
		if(tmp == null) {
			row = proxyClientService.insert(client);
		}
		
		int code = 0;
		String msg = "Create client success.";
		if(row == 0) {
			code = 1;
			msg = "Create client failed.";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
	
	@RequestMapping("/client/delete")
	@ResponseBody
	public Map<String,Object> delete(String clientId){
		Map<String,Object> map = new HashMap<String,Object>();
		int row = proxyClientService.delete(clientId);
		int code = 0;
		String msg = "Delete client success.";
		if(row == 0) {
			code = 1;
			msg = "Delete client failed.";
		}
		map.put("code", code);
		map.put("msg", msg);
		
		return map;
	}
	
	@RequestMapping("/client/queryByClientId")
	@ResponseBody
	public Map<String,Object> queryByClientId(String clientId){
		Map<String,Object> map = new HashMap<String,Object>();
		ProxyClientDetail client = proxyClientService.queryByClientId(clientId);
		int code = 0;
		if(client == null) {
			code = 1;
		}
		map.put("code", code);
		map.put("data", client);
		return map;
	}
	
	@RequestMapping("/client/queryByName")
	@ResponseBody
	public Map<String,Object> queryByName(String name){
		Map<String,Object> map = new HashMap<String,Object>();
		ProxyClientDetail client = proxyClientService.queryByName(name);
		
		int code = 0;
		if(client == null) {
			code = 1;
		}
		map.put("code", code);
		map.put("data", client);
		
		return map;
	}
	
	@RequestMapping("/client/updateByClientId")
	@ResponseBody
	public Map<String,Object> updateByClientId(String name,String clientId,String clientSecret){
		Map<String,Object> map = new HashMap<String,Object>();
		ProxyClientDetail client = proxyClientService.queryByClientId(clientId);
		int row = 0;
		if(client != null) {
			ProxyClientDetail tmp = proxyClientService.queryByName(name);
			if(tmp == null || tmp.getClientId().equals(clientId)) {
				client.setClientId(clientId);
				if(StringUtil.isNotBlank(clientSecret)) {
					client.setClientSecret(clientSecret);
				}
				if(StringUtil.isNotBlank(name)) {
					client.setName(name);
				}
				row = proxyClientService.update(client);
			}
		}
		int code = 0;
		String msg = "Update client success.";
		if(row == 0) {
			code = 1;
			msg = "Update client failed.";
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
}
