package com.example.util;


public class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -8141313986628055547L;

	public BaseRuntimeException() {
		super();
	}

	public BaseRuntimeException(Throwable cause, String pattern, Object... arguments) {
		super(String.format(pattern, arguments), cause);
	}
	
	public BaseRuntimeException(Throwable cause, String msg) {
		super(msg, cause);
	}
	
	public BaseRuntimeException(String msg) {
		super(msg);
	}
	
	public BaseRuntimeException(String pattern, Object... arguments) {
		super(String.format(pattern, arguments));
	}
	
	@Deprecated
	public BaseRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
