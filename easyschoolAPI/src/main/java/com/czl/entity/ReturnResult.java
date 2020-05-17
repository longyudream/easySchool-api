package com.czl.entity;

public class ReturnResult
{
	public boolean result;
	public String message;
	protected String messageId;
	protected String code;
	
	public ReturnResult(boolean result, String message)
	{
		this.result = result;
		this.message = message;
	}
	
	public ReturnResult()
	{
		this.result = false;
		this.message = "";
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
	public boolean getResult() {
		return this.result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
