package com.bugbycode.service.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbycode.https.HttpsClient;
import com.bugbycode.module.config.OauthConfig;
import com.bugbycode.service.api.ApiService;

@Service("apiService")
public class ApiServiceImpl implements ApiService{

	@Autowired
	private HttpsClient httpsClient;
	
	@Autowired
	private OauthConfig oauthConfig;
	
	@Override
	public String getToken() {
		String tokenResult = httpsClient.getToken(oauthConfig.getOauthUri(), "client_credentials", oauthConfig.getClientId(),
				oauthConfig.getSecret(), "console");
		try {
			Map<String,Object> map = jsonToMap(new JSONObject(tokenResult));
			if(map.containsKey("error")) {
				throw new RuntimeException("Get token error");
			}
			return map.get("access_token").toString();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@Override
	public String getResource(String url, Map<String, Object> data) {
		String tokenResult = httpsClient.getToken(oauthConfig.getOauthUri(), "client_credentials", oauthConfig.getClientId(),
				oauthConfig.getSecret(), "console");
		try {
			Map<String,Object> map = jsonToMap(new JSONObject(tokenResult));
			if(map.containsKey("error")) {
				throw new RuntimeException("Get token error");
			}
			String token = map.get("access_token").toString();
			return httpsClient.getResource(url, token, data);
		}catch (Exception e) {
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
