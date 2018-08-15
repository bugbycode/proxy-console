package com.bugbycode.service.host.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbycode.dao.host.HostDao;
import com.bugbycode.module.host.ProxyHost;
import com.bugbycode.service.host.ProxyHostService;
import com.util.StringUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("proxyHostService")
public class ProxyHostServiceImpl implements ProxyHostService {

	@Autowired
	private HostDao hostDao;
	
	@Override
	public List<ProxyHost> query(String keyword) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtil.isNotBlank(keyword)) {
			map.put("keyword", keyword);
		}
		return hostDao.query(map);
	}

	@Override
	public SearchResult<ProxyHost> query(String keyword, int startIndex, int pageSize) {
		SearchResult<ProxyHost> sr = new SearchResult<ProxyHost>();
		Page page = new Page(pageSize, startIndex);
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtil.isNotBlank(keyword)) {
			map.put("keyword", keyword);
		}
		
		int totalCount = hostDao.count(map);
		page.setTotalCount(totalCount);
		
		RowBounds rb = new RowBounds(page.getStartIndex(), page.getPageSize());
		List<ProxyHost> list = hostDao.query(map, rb);
		
		sr.setPage(page);
		sr.setList(list);
		
		return sr;
	}

	@Override
	public int insert(ProxyHost host) {
		host.setCreateTime(new Date());
		return hostDao.insert(host);
	}

	@Override
	public int update(ProxyHost host) {
		host.setUpdateTime(new Date());
		return hostDao.update(host);
	}

	@Override
	public int delete(int id) {
		return hostDao.delete(id);
	}

	@Override
	public ProxyHost queryById(int id) {
		return hostDao.queryById(id);
	}

	@Override
	public ProxyHost queryByName(String name) {
		return hostDao.queryByName(name);
	}

	@Override
	public ProxyHost queryByIp(String ip) {
		return hostDao.queryByIp(ip);
	}

}
