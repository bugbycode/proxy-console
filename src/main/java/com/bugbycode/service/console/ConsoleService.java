package com.bugbycode.service.console;

import org.json.JSONObject;

public interface ConsoleService {
	
	/**
	 * 获取适合连接的主机
	 * @return
	 */
	public JSONObject getChannelHost();
	
	/**
	 * 获取代理通道
	 * @param clientId
	 * @param host
	 * @param port
	 * @param closeApp
	 * @return
	 */
	public JSONObject getChannelInfo(String clientId,String host,int port,boolean closeApp);
}
