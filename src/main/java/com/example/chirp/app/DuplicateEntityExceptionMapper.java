package com.example.chirp.app;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.chirp.app.kernel.exceptions.DuplicateEntityException;
import com.example.chirp.app.pub.ExceptionInfo;

@Provider
public class DuplicateEntityExceptionMapper implements ExceptionMapper<DuplicateEntityException> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Response toResponse(DuplicateEntityException exception) {
		log.error("Exception processing request", exception);
		String message = exception.getMessage() != null ?  exception.getMessage() : exception.getClass().getName();
		ExceptionInfo info = new ExceptionInfo(403, message);
		return Response.status(403).entity(info).build();
	}
}
