package com.example.chirp.app.providers;

import javax.ws.rs.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	  public static final String DEFAULT = toHttpAuth("admin", "secret");
	
	  // Load one time from the file system, spring, DB, System Properties, wherever.
	  public static final java.util.List<String> VALID_CLIENTS = 
			  Collections.singletonList(DEFAULT);

	  private List<String> unsecuredPaths = Arrays.asList("", "/", "application.wadl");

	  @Context
	  private UriInfo uriInfo;

	  @Override
	  public void filter(ContainerRequestContext requestContext) throws IOException {
	    String authHeader = requestContext.getHeaderString("Authorization");

	    String path = uriInfo.getPath();
	    if (unsecuredPaths.contains(path)) {
	      return;
	    }

	    if (VALID_CLIENTS.contains(authHeader) == false) {
	      throw new NotAuthorizedException("BASIC");
	    }
	  }

	  public static String toHttpAuth(String username, String password) {
	    byte[] value = (username + ":" + password).getBytes();
	    return "Basic " + DatatypeConverter.printBase64Binary(value);
	  }
}
