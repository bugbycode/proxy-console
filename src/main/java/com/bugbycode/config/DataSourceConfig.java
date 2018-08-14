package com.bugbycode.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DataSourceConfig {
	
	@Bean("oauthDataSource")
	@ConfigurationProperties(prefix="spring.oauth.datasource")
	public DataSource getOauthDataSource() {
		return DataSourceBuilder.create(BasicDataSource.class.getClassLoader()).build();
	}
	
	@Bean("hostDataSource")
	@ConfigurationProperties(prefix="spring.host.datasource")
	public DataSource getHostDataSource() {
		return DataSourceBuilder.create(BasicDataSource.class.getClassLoader()).build();
	}
	
	@Bean("oauthTransactionManager")
	@Resource(name="oauthDataSource")
	public DataSourceTransactionManager getOauthTransactionManager(DataSource oauthDataSource) {
		return new DataSourceTransactionManager(oauthDataSource);
	}
	
	@Bean("hostTransactionManager")
	@Resource(name="hostDataSource")
	public DataSourceTransactionManager getHotTransactionManager(DataSource hostDataSource) {
		return new DataSourceTransactionManager(hostDataSource);
	}
	
	@Bean("oauthSqlSessionFactory")
	@Resource(name="oauthDataSource")
	public SqlSessionFactory getOauthSqlSessionFactory(DataSource oauthDataSource) throws Exception {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
		sf.setDataSource(oauthDataSource);
		sf.setConfigLocation(resolver.getResource("classpath:mybatis/oauth/config/mybatis-config.xml"));
		sf.setMapperLocations(resolver.getResources("classpath:mybatis/oauth/mapper/*/*.xml"));
		return sf.getObject();
	}
	
	@Bean("hostSqlSessionFactory")
	@Resource(name="hostDataSource")
	public SqlSessionFactory getHostSqlSessionFactory(DataSource hostDataSource) throws Exception {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
		sf.setDataSource(hostDataSource);
		sf.setConfigLocation(resolver.getResource("classpath:mybatis/host/config/mybatis-config.xml"));
		sf.setMapperLocations(resolver.getResources("classpath:mybatis/host/mapper/*/*.xml"));
		return sf.getObject();
	}
}
