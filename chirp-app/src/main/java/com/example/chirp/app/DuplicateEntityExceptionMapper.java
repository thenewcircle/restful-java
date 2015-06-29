package com.example.chirp.app;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.chirp.kernel.exceptions.DuplicateEntityException;

@Provider
public class DuplicateEntityExceptionMapper implements ExceptionMapper<DuplicateEntityException>{

	@Override
	public Response toResponse(DuplicateEntityException exception) {
		String msg = exception.getMessage();
		return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
	}

}
