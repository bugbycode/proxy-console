package com.bugbycode.dao.host.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.bugbycode.dao.base.HostBaseDao;
import com.bugbycode.dao.host.HostDao;
import com.bugbycode.module.host.ProxyHost;

@Repository("hostDao")
public class HostDaoImpl extends HostBaseDao implements HostDao {

	@Override
	public List<ProxyHost> query(String keyword) {
		return getSqlSession().selectList("host.query", keyword);
	}

	@Override
	public List<ProxyHost> query(String keyword, RowBounds rb) {
		return getSqlSession().selectList("host.query", keyword, rb);
	}

	@Override
	public int count(String keyword) {
		return getSqlSession().selectOne("host.count", keyword);
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
