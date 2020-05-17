package com.czl.entity;

import com.alibaba.fastjson.JSONObject;
import com.czl.config.MessageListenerConfig;

public class ResultEntity {

	public static final String RESULT_CODE = "resCode";

	public static final String RESULT_MESSAGE = "resMessage";

	public static final String RESULT_DATA = "resInfo";

	public static final String RESULT_CODE_OK = "0000";

	public static final String RESULT_CODE_ERROR = "1111";

	private String resCode;

	private String resMessage;

	private Object resInfo;

	public ResultEntity() {
	}

	public ResultEntity(String resCode) {
		this.resCode = resCode;
	}

	public ResultEntity(String resCode, String resMessage) {
		this.resCode = resCode;
		this.resMessage = resMessage;
	}

	public ResultEntity(String resCode, String msgCode, String[] msgParams) {
		this.resCode = resCode;
		// 到资源文件中查找消息并设定
		this.resMessage = MessageListenerConfig.getProperty(msgCode, msgParams);
	}

	public ResultEntity(String resCode, String msgCode, String[] msgParams, Object resInfo) {
		this.resCode = resCode;
		// 到资源文件中查找消息并设定
		this.resMessage = MessageListenerConfig.getProperty(msgCode, msgParams);
		this.resInfo = resInfo;
	}

	public ResultEntity(String resCode, String resMessage, Object resInfo) {
		this.resCode = resCode;
		this.resMessage = resMessage;
		this.resInfo = resInfo;
	}

	public String toJson() {
		return JSONObject.toJSONString(this, true);
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResMessage() {
		return resMessage;
	}

	public void setResMessage(String resMessage) {
		this.resMessage = resMessage;
	}

	public Object getResInfo() {
		return resInfo;
	}

	public void setResInfo(Object resInfo) {
		this.resInfo = resInfo;
	}

}
