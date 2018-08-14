package com.bugbycode.module.host;

import java.io.Serializable;
import java.util.Date;

/**
 * 代理服务器信息
 * @author zhigongzhang
 *
 */
public class ProxyHost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4030550583201854125L;

	private int id;
	
	private String name;
	
	private String ip;
	
	private int port;
	
	private Date createTime;
	
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
