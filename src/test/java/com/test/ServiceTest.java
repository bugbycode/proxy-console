package com.test;

import java.util.List;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bugbycode.https.HttpsClient;
import com.bugbycode.module.host.ProxyHost;
import com.bugbycode.module.keystore.ServerKeyStore;
import com.bugbycode.service.client.ProxyClientService;
import com.bugbycode.service.host.ProxyHostService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ServiceTest {
	
	private final Logger logger = Logger.getLogger(ServiceTest.class);
	
	@Autowired
	private ProxyHostService proxyHostService;
	
	@Autowired
	private ProxyClientService proxyClientService;
	
	@Autowired
	private HttpsClient httpsClient;
	
	@Test
	public void testService() {
		List<ProxyHost> list = proxyHostService.query("哈哈");
		logger.debug(list);
	}
	
	@Test
	public void testQueryById() {
		proxyHostService.queryById(0);
	}
	
	@Test
	public void testInsertHost() {
		ProxyHost host = new ProxyHost();
		host.setIp("192.168.1.1");
		host.setName("fort");
		int id = proxyHostService.insert(host);
		logger.debug(id);
	}
	
	@Test
	public void testUpdateHost() {
		ProxyHost host = new ProxyHost();
		host.setId(1);
		host.setIp("192.168.1.2");
		host.setName("fort");
		proxyHostService.update(host);
	}
	
	@Test
	public void testDeleteHost() {
		proxyHostService.delete(1);
	}
	
	@Test
	public void testQueryClient() {
		proxyClientService.query(null, null);
	}
}
