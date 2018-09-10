package com.bugbycode.controller.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bugbycode.mongodb.module.CustomClientDetails;
import com.bugbycode.mongodb.service.client.CustomClientService;
import com.util.RandomUtil;
import com.util.StringUtil;
import com.util.page.SearchResult;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
@RequestMapping("/api")
@Controller
public class ClientController {

	private static final Logger logger = LogManager.getLogger(ClientController.class);
	
	private BASE64Encoder encode = new BASE64Encoder();
	
	@Autowired
	private CustomClientService customClientService;
	
	@RequestMapping("/client/query")
	@ResponseBody
	public SearchResult<CustomClientDetails> query(String alias,String keyword,
			@RequestParam(name="startIndex",defaultValue="0")
			int startIndex,
			@RequestParam(name="pageSize",defaultValue="-1")
			int pageSize){
		
		SearchResult<CustomClientDetails> sr = null;
		
		if(pageSize == -1) {
			sr = new SearchResult<CustomClientDetails>();
			List<CustomClientDetails> list = customClientService.query(alias, keyword);
			sr.setList(list);
		}else {
			sr = customClientService.query(alias, keyword, startIndex, pageSize);
		}
		
		return sr;
	}
	
	@RequestMapping("/client/create")
	@ResponseBody
	public Map<String,Object> create(String name,String alias){
		Map<String,Object> map = new HashMap<String,Object>();
		CustomClientDetails client = new CustomClientDetails();
		client.setAlias(alias);
		client.setName(name);
		client.setClientId(encode.encode(RandomUtil.GetGuid32().getBytes()));
		client.setClientSecret(encode.encode(RandomUtil.GetGuid32().getBytes()));
		Set<String> scop = new HashSet<String>();
		scop.add("agent");
		client.setScope(scop);
		
		Set<String> crantType = new HashSet<String>();
		crantType.add("client_credentials");
		
		client.setAuthorizedGrantTypes(crantType);
		
		CustomClientDetails tmp = customClientService.queryByName(name,alias);
		String _id = null;
		if(tmp == null) {
			_id = customClientService.insert(client);
		}
		
		int code = 0;
		String msg = "Create client success.";
		if(StringUtil.isBlank(_id)) {
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
		long row = customClientService.delete(clientId);
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
		CustomClientDetails client = customClientService.queryByClientId(clientId);
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
	public Map<String,Object> queryByName(String name,String alias){
		Map<String,Object> map = new HashMap<String,Object>();
		CustomClientDetails client = customClientService.queryByName(name,alias);
		
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
		CustomClientDetails client = customClientService.queryByClientId(clientId);
		long row = 0;
		if(client != null) {
			CustomClientDetails tmp = customClientService.queryByName(name,client.getAlias());
			if(tmp == null || tmp.getClientId().equals(clientId)) {
				client.setClientId(clientId);
				if(StringUtil.isNotBlank(clientSecret)) {
					client.setClientSecret(clientSecret);
				}
				if(StringUtil.isNotBlank(name)) {
					client.setName(name);
				}
				row = customClientService.update(client);
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
