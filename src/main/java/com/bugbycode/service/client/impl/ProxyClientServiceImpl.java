package com.bugbycode.service.client.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbycode.dao.client.ClientDao;
import com.bugbycode.module.client.ProxyClientDetail;
import com.bugbycode.service.client.ProxyClientService;
import com.util.StringUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

//@Service("proxyClientService")
public class ProxyClientServiceImpl implements ProxyClientService {

	@Autowired
	private ClientDao clientDao;
	
	@Override
	public List<ProxyClientDetail> query(String alias,String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtil.isNotBlank(alias)) {
			map.put("alias", alias);
		}
		if(StringUtil.isNotBlank(keyword)) {
			map.put("keyword", keyword);
		}
		return clientDao.query(map);
	}

	@Override
	public SearchResult<ProxyClientDetail> query(String alias,String keyword, int startIndex, int pageSize) {
		Page page = new Page(pageSize, startIndex);
		SearchResult<ProxyClientDetail> sr = new SearchResult<ProxyClientDetail>();
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtil.isNotBlank(alias)) {
			map.put("alias", alias);
		}
		if(StringUtil.isNotBlank(keyword)) {
			map.put("keyword", keyword);
		}
		int totalCount = clientDao.count(map);
		
		page.setTotalCount(totalCount);
		
		RowBounds rb = new RowBounds(page.getStartIndex(), page.getPageSize());
		List<ProxyClientDetail> list = clientDao.query(map, rb);
		
		sr.setPage(page);
		sr.setList(list);
		
		return sr;
	}

	@Override
	public int insert(ProxyClientDetail client) {
		client.setCreateTime(new Date());
		return clientDao.insert(client);
	}

	@Override
	public int update(ProxyClientDetail client) {
		client.setUpdateTime(new Date());
		return clientDao.update(client);
	}

	@Override
	public int delete(String clientId) {
		return clientDao.delete(clientId);
	}

	@Override
	public ProxyClientDetail queryByClientId(String clientId) {
		return clientDao.queryByClientId(clientId);
	}

	@Override
	public int deleteByAlias(String alias) {
		return clientDao.deleteByAlias(alias);
	}

	@Override
	public ProxyClientDetail queryByName(String name,String alias) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("alias", alias);
		return clientDao.queryByName(map);
	}

}
