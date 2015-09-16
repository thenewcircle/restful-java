package chirp.service.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.NoSuchEntityException;

@Provider
public class NoSuchEntityExceptionMapper implements ExceptionMapper<NoSuchEntityException> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Response toResponse(NoSuchEntityException exception) {
		log.info(exception.getMessage());
		return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
	}
	
}
