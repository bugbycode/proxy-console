package com.bugbycode.mongodb.service.client;

import java.util.List;

import com.bugbycode.mongodb.module.CustomClientDetails;
import com.util.page.SearchResult;

public interface CustomClientService {
	
	public List<CustomClientDetails> query(String alias,String keyword);
	
	public SearchResult<CustomClientDetails> query(String alias,String keyword,int startIndex,int pageSize);
	
	public String insert(CustomClientDetails client);
	
	public long update(CustomClientDetails client);
	
	public long delete(String clientId);
	
	public long deleteByAlias(String alias);
	
	public CustomClientDetails queryByClientId(String clientId);
	
	public CustomClientDetails queryByName(String name,String alias);
}
