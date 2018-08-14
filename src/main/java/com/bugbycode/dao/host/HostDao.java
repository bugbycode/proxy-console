package com.bugbycode.dao.host;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.bugbycode.module.host.ProxyHost;

public interface HostDao {
	
	public List<ProxyHost> query(String keyword);
	
	public List<ProxyHost> query(String keyword,RowBounds rb);
	
	public int count(String keyword);
	
	public int insert(ProxyHost host);
	
	public int update(ProxyHost host);
	
	public int delete(int id);
	
	public ProxyHost queryById(int id);
	
	public ProxyHost queryByName(String name);
	
	public ProxyHost queryByIp(String ip);
}
