package com.example.chirp.app.providers;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
@DeprecatedRequest
public class DeprecatedRequestFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		// The value really doesn't matter here, it's probably good enough to know that the header even exists.
		responseContext.getHeaders().putSingle("Deprecated", "This message will be removed in a future version");
	}
}
