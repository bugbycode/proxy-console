package com.bugbycode.dao.base;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class HostBaseDao extends SqlSessionDaoSupport {
	
	protected static final Logger logger = LogManager.getLogger(HostBaseDao.class);
	
	@Resource(name="hostSqlSessionFactory")
	public void initSqlSessionFactory(SqlSessionFactory hostSqlSessionFactory) {
		logger.info("initSqlSessionFactory ......");
		super.setSqlSessionFactory(hostSqlSessionFactory);
	}
}
