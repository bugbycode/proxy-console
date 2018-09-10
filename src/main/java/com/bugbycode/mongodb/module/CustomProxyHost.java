package com.bugbycode.mongodb.module;

import com.bugbycode.module.host.ProxyHost;

public class CustomProxyHost extends ProxyHost {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6894405473829868737L;
	
	private String _id;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
	
}
