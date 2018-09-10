package com.bugbycode.mongodb.service.host;

import java.util.List;

import com.bugbycode.mongodb.module.CustomProxyHost;
import com.util.page.SearchResult;

public interface CustomHostService {

	public List<CustomProxyHost> query(String keyword);
	
	public SearchResult<CustomProxyHost> query(String keyword,int startIndex,int pageSize);
	
	public String insert(CustomProxyHost host);
	
	public long update(CustomProxyHost host);
	
	public long delete(String _id);
	
	public CustomProxyHost queryById(String _id);
	
	public CustomProxyHost queryByName(String name);
	
	public CustomProxyHost queryByIp(String ip);
}
