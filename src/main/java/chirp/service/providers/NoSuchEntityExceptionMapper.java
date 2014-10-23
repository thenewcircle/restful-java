package chirp.service.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import chirp.model.NoSuchEntityException;

@Provider
public class NoSuchEntityExceptionMapper implements
		ExceptionMapper<NoSuchEntityException> {

	@Override
	public Response toResponse(NoSuchEntityException nse) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
