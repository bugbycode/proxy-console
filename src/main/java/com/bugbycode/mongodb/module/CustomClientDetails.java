package com.bugbycode.mongodb.module;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

public class CustomClientDetails extends BaseClientDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3978883902895158812L;

	private String _id;
	
	private String name;
	
	private String alias;
	
	private Date createTime;
	
	private Date updateTime;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		try {
			json.put("_id", _id);
			json.put("name", name);
			json.put("clientId", getClientId());
			json.put("clientSecret", getClientSecret());
			json.put("scope", getScope());
			json.put("grantType", getAuthorizedGrantTypes());
			json.put("alias", alias);
			json.put("createTime", createTime);
			json.put("updateTime", updateTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	
}
