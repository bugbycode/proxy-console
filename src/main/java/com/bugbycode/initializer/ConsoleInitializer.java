package com.bugbycode.initializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.bugbycode.mongodb.module.CustomClientDetails;
import com.bugbycode.mongodb.service.client.CustomClientService;
import com.util.StringUtil;

@Component
@Configuration
public class ConsoleInitializer implements ApplicationRunner {

	private static final Logger logger = LogManager.getLogger(ConsoleInitializer.class);
	
	@Autowired
	private CustomClientService customClientService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		File file = ResourceUtils.getFile("classpath:client_details.data");
		if(file == null) {
			throw new RuntimeException("Can't find the file client_details.data");
		}
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String line = null;
		StringBuffer buff = new StringBuffer();
		try {
			in = new FileInputStream(file);
			isr = new InputStreamReader(in, "UTF-8");
			br = new BufferedReader(isr);
			while((line = br.readLine()) != null) {
				buff.append(line);
			}
		}catch (IOException e) {
			throw e;
		} finally {
			try {
				if(in != null) {
					in.close();
				}
				if(isr != null) {
					isr.close();
				}
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		String jsonStr = buff.toString();
		if(StringUtil.isBlank(jsonStr)) {
			return;
		}
		JSONArray arr = new JSONArray(jsonStr);
		int len = arr.length();
		JSONObject json = null;
		CustomClientDetails tmp = null;
		for(int index = 0;index < len;index++) {
			json = arr.getJSONObject(index);
			String clientId = json.getString("clientId");
			String clientSecret = json.getString("clientSecret");
			Set<String> scope = StringUtils.commaDelimitedListToSet(json.getString("scope"));
			Set<String> authorities = StringUtils.commaDelimitedListToSet(json.getString("authorities"));
			Set<String> authorizedGrantTypes = StringUtils.commaDelimitedListToSet(json.getString("authorizedGrantTypes"));
			Set<SimpleGrantedAuthority> grantedAuthority = new HashSet<SimpleGrantedAuthority>();
			for(String role : authorities) {
				SimpleGrantedAuthority simple = new SimpleGrantedAuthority(role);
				grantedAuthority.add(simple);
			}
			
			tmp = customClientService.queryByClientId(clientId);
			if(tmp == null) {
				CustomClientDetails client = new CustomClientDetails();
				client.setClientId(clientId);
				client.setClientSecret(clientSecret);
				client.setScope(scope);
				client.setAuthorities(grantedAuthority);
				client.setAuthorizedGrantTypes(authorizedGrantTypes);
				customClientService.insert(client);
				logger.info("Initializer client " + client.toString());
			}
		}
	}

}
