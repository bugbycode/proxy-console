package com.bugbycode.dao.client;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.bugbycode.module.client.ProxyClientDetail;

public interface ClientDao {
	
	/**
	 * 查询所有客户端信息
	 * @return
	 */
	public List<ProxyClientDetail> query(Map<String, Object> map);
	
	/**
	 * 自定义条件查询所有客户端信息
	 * @param map
	 * @param rb
	 * @return
	 */
	public List<ProxyClientDetail> query(Map<String,Object> map,RowBounds rb);
	
	/**
	 * 自定义条件统计所有客户端总记录数
	 * @param map
	 * @param rb
	 * @return
	 */
	public int count(Map<String,Object> map);
	
	/**
	 * 新建客户端
	 * @param client
	 * @return
	 */
	public int insert(ProxyClientDetail client);
	
	/**
	 * 修改客户端
	 * @param client
	 * @return
	 */
	public int update(ProxyClientDetail client);
	
	/**
	 * 删除客户端
	 * @param clientId
	 * @return
	 */
	public int delete(String clientId);
	
	
	public int deleteByAlias(String alias);
	
	/**
	 * 根据客户端ID查询客户端
	 * @param clientId
	 * @return
	 */
	public ProxyClientDetail queryByClientId(String clientId);
	
	public ProxyClientDetail queryByName(String name);
}
