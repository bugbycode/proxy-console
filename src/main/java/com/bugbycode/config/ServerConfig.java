package com.bugbycode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bugbycode.https.HttpsClient;
import com.bugbycode.module.config.OauthConfig;
import com.bugbycode.module.keystore.ServerKeyStore;

@Configuration
public class ServerConfig {
	
	@Bean("serverKeyStore")
	@ConfigurationProperties(prefix="server")
	public ServerKeyStore getServerKeyStore() {
		return new ServerKeyStore();
	}
	
	@Bean("oauthConfig")
	@ConfigurationProperties(prefix="spring.oauth")
	public OauthConfig getOauthConfig() {
		return new OauthConfig();
	}
	
	@Bean("httpsClient")
	public HttpsClient httpsClient(ServerKeyStore serverKeyStore) {
		return new HttpsClient(serverKeyStore.getKeystorePath(), serverKeyStore.getKeystorePassword());
	}
}
