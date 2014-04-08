package com.example.chirp.providers;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		String httpMethod = requestContext.getMethod();
		if (!HttpMethod.POST.equals(httpMethod))
			return;
		String header = requestContext.getHeaderString(HEADER);
		if (header != null && !header.isEmpty())
			return;
		Response error = Response.status(Status.FORBIDDEN).entity("Missing X-CSRF-Prevention header.").build();
		requestContext.abortWith(error);
	}

}
