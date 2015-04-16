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

		// return Response.status(404).entity(exception.getMessage()).build();
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(exception.getMessage()).build();
	}
}