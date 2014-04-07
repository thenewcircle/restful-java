package com.example.util;

public class BaseException extends Exception {

	private static final long serialVersionUID = 3838258174781719127L;

	public BaseException() {
		super();
	}
	
	public BaseException(String msg) {
		super(msg);
	}
	
	public BaseException(String messagePattern, Object... args) {
		super(String.format(messagePattern, args));
	}

	@Deprecated
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause, String message) {
		super(message, cause);
	}

	public BaseException(Throwable cause, String messagePattern, Object... args) {
		super(String.format(messagePattern, args), cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	
}
