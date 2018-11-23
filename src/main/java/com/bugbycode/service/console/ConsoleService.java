package com.bugbycode.service.console;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.bugbycode.module.host.ProxyHost;
import com.bugbycode.mongodb.module.CustomProxyHost;

public interface ConsoleService {
	
	/**
	 * 获取适合连接的主机
	 * @return
	 */
	public JSONObject getChannelHost();
	
	/**
	 * 获取代理通道
	 * @param serverName 默认地址
	 * @param clientId
	 * @param host
	 * @param port
	 * @param closeApp
	 * @return
	 */
	public JSONObject getChannelInfo(String serverName,String clientId,String host,int port,boolean closeApp);
	
	/**
	 * 获取所有代理对应的服务
	 * @param serverName
	 * @return
	 */
	public List<Map<String,CustomProxyHost>> getOnlineAgentInfo(String serverName);
	
	/**
	 * 扫描内网主机
	 * @param serverName
	 * @param clientId
	 * @param host
	 * @return
	 */
	public String scanHost(String serverName,String clientId,String host);
}
