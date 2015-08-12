package com.example.chirp.app.pub;


public class ExceptionInfo {

	private final int status;
	private final String message;
	
	public ExceptionInfo(int status, Exception ex) {
		this.status = status;

		if (ex.getMessage() == null) {
			this.message = ex.getClass().getName();
		} else {
			this.message = ex.getMessage();
		}
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
