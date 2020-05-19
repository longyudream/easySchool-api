package com.czl.model.common;

import com.alibaba.fastjson.JSONObject;

public class RequestParamModel {

	/** 操作人 */
	private String user_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/** 请求时间 */
	private String request_time;

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	/** 请求内容 */
	private JSONObject body;

	public JSONObject getBody() {
		return body;
	}

	public void setBody(JSONObject body) {
		this.body = body;
	}

}