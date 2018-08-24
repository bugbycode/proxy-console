package com.bugbycode.service.api;

import java.util.Map;

public interface ApiService {
	
	public String getToken();
	
	public String getResource(String url,Map<String,Object> data);
}
