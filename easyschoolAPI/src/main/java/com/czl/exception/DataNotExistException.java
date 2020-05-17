package com.czl.exception;

public class DataNotExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataNotExistException() {
		super();
	}

	public DataNotExistException(String comment) {
		super(comment);
	}
}
