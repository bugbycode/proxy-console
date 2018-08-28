package com.bugbycode.dao.client.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.bugbycode.dao.base.OauthBaseDao;
import com.bugbycode.dao.client.ClientDao;
import com.bugbycode.module.client.ProxyClientDetail;

@Repository("clientDao")
public class ClientDaoImpl extends OauthBaseDao implements ClientDao {

	@Override
	public List<ProxyClientDetail> query(Map<String, Object> map) {
		return getSqlSession().selectList("client.query", map);
	}

	@Override
	public List<ProxyClientDetail> query(Map<String, Object> map, RowBounds rb) {
		return getSqlSession().selectList("client.query", map, rb);
	}

	@Override
	public int count(Map<String, Object> map) {
		return getSqlSession().selectOne("client.count", map);
	}

	@Override
	public int insert(ProxyClientDetail client) {
		return getSqlSession().insert("client.insert", client);
	}

	@Override
	public int update(ProxyClientDetail client) {
		return getSqlSession().update("client.update", client);
	}

	@Override
	public int delete(String clientId) {
		return getSqlSession().delete("client.delete", clientId);
	}

	@Override
	public ProxyClientDetail queryByClientId(String clientId) {
		return getSqlSession().selectOne("client.queryByClientId", clientId);
	}

	@Override
	public int deleteByAlias(String alias) {
		return getSqlSession().delete("client.deleteByAlias", alias);
	}

	@Override
	public ProxyClientDetail queryByName(Map<String,Object> map) {
		return getSqlSession().selectOne("client.queryByName", map);
	}
	
}
