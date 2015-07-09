package com.example.chirp.app.providers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

@Component
@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	  public static final String DEFAULT = toHttpAuth("admin", "secret");
	
	  // Load one time from the file system, spring, DB, System Properties, wherever.
	  public final java.util.List<String> validClients = new ArrayList<>();
	  
	  private List<String> unsecuredPaths = Arrays.asList("", "/", "application.wadl");

	  @Context
	  private UriInfo uriInfo;

	  public AuthenticationFilter() {
		  validClients.add(DEFAULT);
	  }
	  
	  public void setValidClients(String values) {
			String[] items = values.split(",");
			for (String item : items) {
				String[] pairs = item.split(":");
				validClients.add(toHttpAuth(pairs[0], pairs[1]));
			}
	  }
	  
	  @Override
	  public void filter(ContainerRequestContext requestContext) throws IOException {
	    String authHeader = requestContext.getHeaderString("Authorization");

	    String path = uriInfo.getPath();
	    if (unsecuredPaths.contains(path)) {
	      return;
	    }

	    if (validClients.contains(authHeader) == false) {
	      throw new NotAuthorizedException("BASIC");
	    }
	  }

	  public static String toHttpAuth(String username, String password) {
	    byte[] value = (username + ":" + password).getBytes();
	    return "Basic " + DatatypeConverter.printBase64Binary(value);
	  }
}
