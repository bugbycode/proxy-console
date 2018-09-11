package com.bugbycode.service.console.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbycode.module.config.OauthConfig;
import com.bugbycode.module.host.ProxyHost;
import com.bugbycode.mongodb.module.CustomProxyHost;
import com.bugbycode.mongodb.service.host.CustomHostService;
import com.bugbycode.service.api.ApiService;
import com.bugbycode.service.console.ConsoleService;

@Service("consoleService")
public class ConsoleServiceImpl implements ConsoleService {

	@Autowired
	private CustomHostService customHostService;
	
	@Autowired
	private ApiService apiService;
	
	@Autowired
	private OauthConfig oauthConfig;
	
	@Override
	public JSONObject getChannelHost() {
		JSONObject json = new JSONObject();
		try {
			int count = -1;
			String ip = "";
			int port = 0;
			List<CustomProxyHost> list = customHostService.query(null);
			if(!(list == null || list.isEmpty())) {
				for(CustomProxyHost host : list) {
					Map<String,Object> data = new HashMap<String,Object>();
					try {
						String countStr = apiService.getResource("https://" + host.getIp() + ":" + oauthConfig.getProxyHttpsPort() + "/cloud_proxy/api/getConnCount", data);
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
		JSONObject json = new JSONObject();
		try {
			List<CustomProxyHost> list = customHostService.query(null);
			if(!(list == null || list.isEmpty())) {
				for(CustomProxyHost proxyHost : list) {
					try {
						String result = apiService.getResource("https://" + proxyHost.getIp() + ":" + oauthConfig.getProxyHttpsPort() + "/cloud_proxy/api/getChannel", data);
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
	
	public List<Map<String,CustomProxyHost>> getOnlineAgentInfo(){
		List<Map<String,CustomProxyHost>> clientList = new ArrayList<Map<String,CustomProxyHost>>();
		List<CustomProxyHost> list = customHostService.query(null);
		if(!(list == null || list.isEmpty())) {
			Map<String,Object> data = new HashMap<String,Object>();
			JSONObject json = null;
			for(CustomProxyHost host : list) {
				String ip = host.getIp();
				String result = apiService.getResource("https://" + ip + ":" + oauthConfig.getProxyHttpsPort() + "/cloud_proxy/api/getAllClientId", data);
				try {
					json = new JSONObject(result);
					int code = json.getInt("code");
					if(code == 0) {
						JSONArray arr = json.getJSONArray("data");
						int len = arr.length();
						if(len > 0) {
							for(int index = 0;index < len;index++) {
								String agentName = arr.getString(index);
								Map<String,CustomProxyHost> map = new HashMap<String,CustomProxyHost>();
								map.put(agentName, host);
								clientList.add(map);
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return clientList;
	}
	
	@Override
	public String scanHost(String clientId,String host) {
		List<Map<String,CustomProxyHost>> list = getOnlineAgentInfo();
		if(!list.isEmpty()) {
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("clientId", clientId);
			data.put("host", host);
			for(Map<String,CustomProxyHost> map : list) {
				ProxyHost proxy = map.get(clientId);
				if(proxy == null) {
					continue;
				}
				try {
					return apiService.getResource("https://" + proxy.getIp() + ":" + oauthConfig.getProxyHttpsPort() + "/cloud_proxy/api/scanOs", data);
				}catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return "{\"code\":0,\"data\":[]}";
	}
}
