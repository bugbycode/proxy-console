package com.bugbycode.module.config;

public class OauthConfig {
	
	private String oauthUri;
	
	private String checkTokenUri;
	
	private String clientId;
	
	private String secret;
	
	private String proxyHttpsPort;

	public String getOauthUri() {
		return oauthUri;
	}

	public void setOauthUri(String oauthUri) {
		this.oauthUri = oauthUri;
	}

	public String getCheckTokenUri() {
		return checkTokenUri;
	}

	public void setCheckTokenUri(String checkTokenUri) {
		this.checkTokenUri = checkTokenUri;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getProxyHttpsPort() {
		return proxyHttpsPort;
	}

	public void setProxyHttpsPort(String proxyHttpsPort) {
		this.proxyHttpsPort = proxyHttpsPort;
	}
}
