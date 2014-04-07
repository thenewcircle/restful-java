package com.example.util;

public class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 3838258174781719127L;

	public BaseRuntimeException() {
		super();
	}
	
	public BaseRuntimeException(String msg) {
		super(msg);
	}
	
	public BaseRuntimeException(String messagePattern, Object... args) {
		super(String.format(messagePattern, args));
	}

	@Deprecated
	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseRuntimeException(Throwable cause, String message) {
		super(message, cause);
	}

	public BaseRuntimeException(Throwable cause, String messagePattern, Object... args) {
		super(String.format(messagePattern, args), cause);
	}

	public BaseRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
