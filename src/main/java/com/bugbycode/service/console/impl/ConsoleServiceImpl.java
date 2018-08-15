package com.bugbycode.service.console.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbycode.https.HttpsClient;
import com.bugbycode.module.config.OauthConfig;
import com.bugbycode.module.host.ProxyHost;
import com.bugbycode.service.console.ConsoleService;
import com.bugbycode.service.host.ProxyHostService;

@Service("consoleService")
public class ConsoleServiceImpl implements ConsoleService {

	@Autowired
	private HttpsClient httpsClient;
	
	@Autowired
	private ProxyHostService proxyHostService;
	
	@Autowired
	private OauthConfig oauthConfig;
	
	@Override
	public JSONObject getChannelHost() {
		String tokenResult = httpsClient.getToken(oauthConfig.getOauthUri(), "client_credentials", oauthConfig.getClientId(),
				oauthConfig.getSecret(), "console");
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = jsonToMap(new JSONObject(tokenResult));
			if(map.containsKey("error")) {
				throw new RuntimeException("Get token error");
			}
			String token = map.get("access_token").toString();
			
			int count = -1;
			String ip = "";
			int port = 0;
			List<ProxyHost> list = proxyHostService.query(null);
			if(!(list == null || list.isEmpty())) {
				for(ProxyHost host : list) {
					Map<String,Object> data = new HashMap<String,Object>();
					try {
						String countStr = httpsClient.getResource("https://" + host.getIp() + "/cloud_proxy/api/getConnCount", token, data);
						int tmp = Integer.valueOf(countStr);
						if(count == -1) {
							count = tmp;
							ip = host.getIp();
							port = host.getPort();
						}
						if(count > tmp) {
							count = tmp;
							ip = host.getIp();
							port = host.getPort();
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(port > 0) {
					json.put("ip", ip);
					json.put("port", port);
				}
			}
			return json;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public JSONObject getChannelInfo(String clientId,String host,int port,boolean closeApp) {
		String proxyIp = "";
		int proxyPort = 0;
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("clientId", clientId);
		data.put("host", host);
		data.put("port", port);
		data.put("closeApp", closeApp);
		String tokenResult = httpsClient.getToken(oauthConfig.getOauthUri(), "client_credentials", oauthConfig.getClientId(),
				oauthConfig.getSecret(), "console");
		JSONObject json = new JSONObject();
		try {
			Map<String, Object> map = jsonToMap(new JSONObject(tokenResult));
			if(map.containsKey("error")) {
				throw new RuntimeException("Get token error");
			}
			String token = map.get("access_token").toString();
			List<ProxyHost> list = proxyHostService.query(null);
			if(!(list == null || list.isEmpty())) {
				for(ProxyHost proxyHost : list) {
					try {
						String result = httpsClient.getResource("https://" + proxyHost.getIp() + "/cloud_proxy/api/getChannel", token, data);
						JSONObject resultJson = new JSONObject(result);
						int code = resultJson.getInt("code");
						if(code == 0) {
							proxyIp = proxyHost.getIp();
							proxyPort = resultJson.getInt("port");
							break;
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			if(proxyPort > 0) {
				json.put("host", proxyIp);
				json.put("port", proxyPort);
			}
			
			return json;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private Map<String,Object> jsonToMap(JSONObject json){
		Map<String,Object> map = new HashMap<String,Object>();
		@SuppressWarnings("unchecked")
		Iterator<String> it = json.keys();
		while(it.hasNext()) {
			String key = it.next();
			try {
				if("authorities".equals(key)) {
					JSONArray arr = json.getJSONArray(key);
					int len =arr.length();
					Collection<String> collection = new ArrayList<String>();
					for(int index = 0;index < len;index++) {
						collection.add(arr.getString(index));
					}
					map.put(key, collection);
				}else {
					map.put(key, json.get(key));
				}
			}catch (JSONException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return map;
	}

}
