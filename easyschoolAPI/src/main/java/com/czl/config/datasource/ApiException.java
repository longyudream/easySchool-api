package com.czl.config.datasource;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiException() {
		super();
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(String message, Throwable exception) {
		super(message, exception);
	}

	public ApiException(Throwable exception) {
		super(exception);
	}
}
