package chirp.service.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Response toResponse(Exception exception) {
		log.info(exception.getMessage());
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}

}