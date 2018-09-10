package com.bugbycode.dao.host.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.bugbycode.dao.base.HostBaseDao;
import com.bugbycode.dao.host.HostDao;
import com.bugbycode.module.host.ProxyHost;

//@Repository("hostDao")
public class HostDaoImpl extends HostBaseDao implements HostDao {

	@Override
	public List<ProxyHost> query(Map<String, Object> map) {
		return getSqlSession().selectList("host.query", map);
	}

	@Override
	public List<ProxyHost> query(Map<String, Object> map, RowBounds rb) {
		return getSqlSession().selectList("host.query", map, rb);
	}

	@Override
	public int count(Map<String, Object> map) {
		return getSqlSession().selectOne("host.count", map);
	}

	@Override
	public int insert(ProxyHost host) {
		return getSqlSession().insert("host.insert", host);
	}

	@Override
	public int update(ProxyHost host) {
		return getSqlSession().update("host.update",host);
	}

	@Override
	public int delete(int id) {
		return getSqlSession().delete("host.delete", id);
	}

	@Override
	public ProxyHost queryById(int id) {
		return getSqlSession().selectOne("host.queryById", id);
	}

	@Override
	public ProxyHost queryByName(String name) {
		return getSqlSession().selectOne("host.queryByName", name);
	}

	@Override
	public ProxyHost queryByIp(String ip) {
		return getSqlSession().selectOne("host.queryByIp", ip);
	}

}
