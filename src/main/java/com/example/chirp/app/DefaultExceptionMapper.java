package com.example.chirp.app;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.chirp.app.pub.ExceptionInfo;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Response toResponse(Exception exception) {
		log.error("Exception processing request", exception);
		String message = exception.getMessage() != null ?  exception.getMessage() : exception.getClass().getName();
		ExceptionInfo info = new ExceptionInfo(500, message);
		return Response.status(500).entity(info).build();
	}
}
