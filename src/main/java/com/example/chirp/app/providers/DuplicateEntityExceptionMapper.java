package com.example.chirp.app.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.chirp.app.kernel.exceptions.DuplicateEntityException;
import com.example.chirp.app.pub.ExceptionInfo;

@Component
@Provider
public class DuplicateEntityExceptionMapper
             implements ExceptionMapper<DuplicateEntityException>{
  private final Logger log = LoggerFactory.getLogger(getClass());
  @Override
  public Response toResponse(DuplicateEntityException exception) {

	  ExceptionInfo info = new ExceptionInfo(403, exception);
	  
      log.info(info.getMessage(), exception);

    return Response.status(403).entity(info).build();
  }
}