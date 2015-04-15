package chirp.service.providers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import chirp.model.NoSuchEntityException;

@Provider
public class NoSuchEntityExceptionMapper implements
		ExceptionMapper<NoSuchEntityException> {

	@Override
	public Response toResponse(NoSuchEntityException exception) {
		
		
		return Response.status(404).entity(exception.getMessage()).build();

	}

}
