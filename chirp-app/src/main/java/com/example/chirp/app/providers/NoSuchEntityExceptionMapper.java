package com.example.chirp.app.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.chirp.kernel.exceptions.DuplicateEntityException;
import com.example.chirp.kernel.exceptions.NoSuchEntityException;

@Provider
public class NoSuchEntityExceptionMapper implements ExceptionMapper<NoSuchEntityException> {

	@Override
	public Response toResponse(NoSuchEntityException exception) {
		String msg = exception.getMessage();
		return Response.status(Response.Status.NOT_FOUND).entity(msg).build();
	}

}
