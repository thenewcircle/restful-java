package com.example.chirp.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.chirp.model.NoSuchEntityException;

@Provider
public class NoSuchEntityExceptionMapper implements ExceptionMapper<NoSuchEntityException> {

	@Override
	public Response toResponse(NoSuchEntityException exception) {
		Response response = Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
		return response;
	}

}
