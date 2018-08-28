package com.bugbycode.service.client;

import java.util.List;

import com.bugbycode.module.client.ProxyClientDetail;
import com.util.page.SearchResult;

public interface ProxyClientService {
	
	public List<ProxyClientDetail> query(String alias,String keyword);
	
	public SearchResult<ProxyClientDetail> query(String alias,String keyword,int startIndex,int pageSize);
	
	public int insert(ProxyClientDetail client);
	
	public int update(ProxyClientDetail client);
	
	public int delete(String clientId);
	
	public int deleteByAlias(String alias);
	
	public ProxyClientDetail queryByClientId(String clientId);
	
	public ProxyClientDetail queryByName(String name,String alias);
}
