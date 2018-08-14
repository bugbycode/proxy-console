package com.bugbycode.service.host;

import java.util.List;

import com.bugbycode.module.host.ProxyHost;
import com.util.page.SearchResult;

public interface ProxyHostService {
	
	public List<ProxyHost> query(String keyword);
	
	public SearchResult<ProxyHost> query(String keyword,int startIndex,int pageSize);
	
	public int insert(ProxyHost host);
	
	public int update(ProxyHost host);
	
	public int delete(int id);
	
	public ProxyHost queryById(int id);
	
	public ProxyHost queryByName(String name);
	
	public ProxyHost queryByIp(String ip);
}
