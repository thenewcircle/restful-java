package com.example.chirp.app.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class DefaultExceptionMapper
             implements ExceptionMapper<Exception> {
  private final Logger log = LoggerFactory.getLogger(getClass());
  @Override
  public Response toResponse(Exception exception) {
    String message = (exception.getMessage() != null) ?
      exception.getMessage() : exception.getClass().getName();
    log.error(message, exception);
    return Response.status(500).entity(message).build();
  }
}