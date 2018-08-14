package com.bugbycode.module.client;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户端实体信息类
 * @author zhigongzhang
 *
 */
public class ProxyClientDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1808255581159573114L;
	
	private String clientId;		//客户端ID
	
	private String clientSecret;	//客户端私钥
	
	private String scope;			//范围
	
	private String grantType;		//授权方式
	
	private String alias;			//租户标识
	
	private Date createTime;		//创建时间
	
	private Date updateTime;		//修改时间

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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
