package com.wangbo.test.json;

import java.util.Map;

public class ConnectParam {
	private String action;
	private Map<String, String> request;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, String> getRequest() {
		return request;
	}

	public void setRequest(Map<String, String> request) {
		this.request = request;
	}
}
