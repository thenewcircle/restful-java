package chirp.service.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import chirp.model.DuplicateEntityException;

@Provider
public class DuplicateEntityExceptionMapper implements
		ExceptionMapper<DuplicateEntityException> {

	@Override
	public Response toResponse(DuplicateEntityException exception) {
		return Response.status(400).entity(exception.getMessage()).build();
	}

}
