package com.bugbycode.mongodb.service.client.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bugbycode.mongodb.base.MongoSuportTemplate;
import com.bugbycode.mongodb.module.CustomClientDetails;
import com.bugbycode.mongodb.service.client.CustomClientService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.util.StringUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("customClientService")
public class CustomClientServiceImpl extends MongoSuportTemplate implements CustomClientService {

	private final String CONLLECTION_NAME = "oauth_client_details";
	
	@Override
	public List<CustomClientDetails> query(String alias, String keyword) {
		Criteria c = new Criteria();
		if(StringUtil.isNotBlank(alias)) {
			c.and("alias").is(alias);
		}
		if(StringUtil.isNotBlank(keyword)) {
			c.andOperator(Criteria.where("name").regex(keyword, "i"));
		}
		Query query = new Query();
		query.addCriteria(c);
		return mongoTemplate.find(query, CustomClientDetails.class, CONLLECTION_NAME);
	}

	@Override
	public SearchResult<CustomClientDetails> query(String alias, String keyword, int startIndex, int pageSize) {
		SearchResult<CustomClientDetails> sr = new SearchResult<CustomClientDetails>();
		Criteria c = new Criteria();
		if(StringUtil.isNotBlank(alias)) {
			c.and("alias").is(alias);
		}
		if(StringUtil.isNotBlank(keyword)) {
			c.andOperator(Criteria.where("name").regex(keyword, "i"));
		}
		Query query = new Query();
		query.addCriteria(c);
		
		long totalCount = mongoTemplate.count(query, CustomClientDetails.class, CONLLECTION_NAME);
		Page page = new Page(pageSize, startIndex);
		page.setTotalCount((int)totalCount);
		
		query.skip(page.getStartIndex());
		query.limit(page.getPageSize());
		List<CustomClientDetails> list = mongoTemplate.find(query, CustomClientDetails.class, CONLLECTION_NAME);
		sr.setList(list);
		sr.setPage(page);
		return sr;
	}

	@Override
	public String insert(CustomClientDetails client) {
		client.setCreateTime(new Date());
		mongoTemplate.insert(client, CONLLECTION_NAME);
		return client.get_id();
	}

	@Override
	public long update(CustomClientDetails client) {
		Update update = new Update();
		update.set("name", client.getName());
		update.set("clientSecret", client.getClientSecret());
		update.set("updateTime", new Date());
		UpdateResult rs = mongoTemplate.updateFirst(new Query(Criteria.where("clientId").is(client.getClientId())), update, CONLLECTION_NAME);
		return rs.getModifiedCount();
	}

	@Override
	public long delete(String clientId) {
		DeleteResult rs = mongoTemplate.remove(new Query(Criteria.where("clientId").is(clientId)), CONLLECTION_NAME);
		try {
			mongoTemplate.remove(new Query(Criteria.where("client_id").is(clientId)), CONLLECTION_NAME);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rs.getDeletedCount();
	}

	@Override
	public long deleteByAlias(String alias) {
		DeleteResult rs = mongoTemplate.remove(new Query(Criteria.where("alias").is(alias)), CONLLECTION_NAME);
		return rs.getDeletedCount();
	}

	@Override
	public CustomClientDetails queryByClientId(String clientId) {
		return mongoTemplate.findOne(new Query(Criteria.where("clientId").is(clientId)), CustomClientDetails.class, CONLLECTION_NAME);
	}

	@Override
	public CustomClientDetails queryByName(String name, String alias) {
		Query query = new Query(Criteria.where("name").is(name).and("alias").is(alias));
		return mongoTemplate.findOne(query, CustomClientDetails.class, CONLLECTION_NAME);
	}

}
