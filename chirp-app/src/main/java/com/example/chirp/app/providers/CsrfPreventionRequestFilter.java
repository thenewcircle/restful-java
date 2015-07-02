package com.example.chirp.app.providers;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Safeguard against CSRF as prescribed by http://www.nsa.gov/ia/_files/support/guidelines_implementation_rest.pdf .
 */
@PreMatching
@Priority(Priorities.AUTHENTICATION)
@Provider
public class CsrfPreventionRequestFilter implements ContainerRequestFilter {

	public static final String HEADER = "X-CSRF-Prevention";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String httpMethod = requestContext.getMethod();
		String header = requestContext.getHeaderString(HEADER);

		// We don't need to check GET because it is, by definition, read-only, right?
		// We don't need to check PUT, DELETE, HEAD, OPTIONS, TRACE, etc because a browser cannot submit those calls.
		// Which leaves us with POST...
		if (HttpMethod.POST.equals(httpMethod) == false) { 
			return;
			
		} else if (header != null && header.isEmpty() == false) {
			// We don't care what the value is, just that we have one. 
			// The basis for this is that a browser cannot specify custom headers.
			return;
		}
		
		Response error = Response.status(Response.Status.FORBIDDEN).entity("Missing X-CSRF-Prevention header.").build();
		requestContext.abortWith(error);
	}

}
