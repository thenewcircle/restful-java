package com.example.chirp.app.providers;

import com.example.chirp.app.kernel.exceptions.DuplicateEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DuplicateEntityExceptionMapper implements ExceptionMapper<DuplicateEntityException> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Response toResponse(DuplicateEntityException exception) {
        String message = exception.getMessage() == null ?
                exception.getClass().getName() :
                exception.getMessage();

        log.info(message);

        return Response.status(403).entity(message).build();
    }
}
