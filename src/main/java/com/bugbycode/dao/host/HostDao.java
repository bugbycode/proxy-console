package com.bugbycode.dao.host;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.bugbycode.module.host.ProxyHost;

public interface HostDao {
	
	public List<ProxyHost> query(Map<String,Object> map);
	
	public List<ProxyHost> query(Map<String,Object> map,RowBounds rb);
	
	public int count(Map<String,Object> map);
	
	public int insert(ProxyHost host);
	
	public int update(ProxyHost host);
	
	public int delete(int id);
	
	public int queryById(int id);
}
