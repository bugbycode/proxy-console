package com.bugbycode.mongodb.service.host.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bugbycode.mongodb.base.MongoSuportTemplate;
import com.bugbycode.mongodb.module.CustomProxyHost;
import com.bugbycode.mongodb.service.host.CustomHostService;
import com.util.StringUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("customHostService")
public class CustomHostServiceImpl extends MongoSuportTemplate implements CustomHostService{

	private final String CONLLECTION_NAME = "proxy_host";
	
	@Override
	public List<CustomProxyHost> query(String keyword) {
		Query query = new Query();
		if(StringUtil.isNotBlank(keyword)) {
			Criteria c = Criteria.where("name").regex(keyword, "i").orOperator(Criteria.where("ip").regex(keyword, "i"));
			query.addCriteria(c);
		}
		return mongoTemplate.find(query, CustomProxyHost.class, CONLLECTION_NAME);
	}

	@Override
	public SearchResult<CustomProxyHost> query(String keyword, int startIndex, int pageSize) {
		SearchResult<CustomProxyHost> sr = new SearchResult<CustomProxyHost>();
		Query query = new Query();
		if(StringUtil.isNotBlank(keyword)) {
			Criteria c = Criteria.where("name").regex(keyword, "i").orOperator(Criteria.where("ip").regex(keyword, "i"));
			query.addCriteria(c);
		}
		Page page = new Page(pageSize, startIndex);
		long totalCount = mongoTemplate.count(query, CONLLECTION_NAME);
		int total = (int) totalCount;
		page.setTotalCount(total);
		query.skip(page.getStartIndex());
		query.limit(page.getPageSize());
		List<CustomProxyHost> list = mongoTemplate.find(query, CustomProxyHost.class, CONLLECTION_NAME);
		sr.setPage(page);
		sr.setList(list);
		return sr;
	}

	@Override
	public String insert(CustomProxyHost host) {
		host.setCreateTime(new Date());
		mongoTemplate.insert(host, CONLLECTION_NAME);
		return host.get_id();
	}

	@Override
	public long update(CustomProxyHost host) {
		Update update = new Update();
		update.set("name", host.getName());
		update.set("ip", host.getIp());
		update.set("port", host.getPort());
		update.set("updateTime", new Date());
		return mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(host.get_id())), update, CONLLECTION_NAME).getModifiedCount();
	}

	@Override
	public long delete(String _id) {
		return mongoTemplate.remove(new Query(Criteria.where("_id").is(_id)), CONLLECTION_NAME).getDeletedCount();
	}

	@Override
	public CustomProxyHost queryById(String _id) {
		return mongoTemplate.findOne(new Query(Criteria.where("_id").is(_id)), CustomProxyHost.class, CONLLECTION_NAME);
	}

	@Override
	public CustomProxyHost queryByName(String name) {
		return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), CustomProxyHost.class, CONLLECTION_NAME);
	}

	@Override
	public CustomProxyHost queryByIp(String ip) {
		return mongoTemplate.findOne(new Query(Criteria.where("ip").is(ip)), CustomProxyHost.class, CONLLECTION_NAME);
	}

}
