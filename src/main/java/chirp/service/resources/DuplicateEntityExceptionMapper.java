package chirp.service.resources;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import chirp.model.DuplicateEntityException;

@Provider
public class DuplicateEntityExceptionMapper
		implements ExceptionMapper<DuplicateEntityException>{

	@Override
	public Response toResponse(DuplicateEntityException e) {
		return Response.status(Status.CONFLICT).entity("Object already exists")
				.type(MediaType.TEXT_PLAIN).build();
	}

}
