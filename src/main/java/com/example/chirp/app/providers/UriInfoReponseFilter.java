package com.example.chirp.app.providers;

import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.ws.rs.container.*;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Component
@Provider
@Priority(1)
public class UriInfoReponseFilter implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

    UriInfoRequestFilter.tlui.remove();

  }
}







