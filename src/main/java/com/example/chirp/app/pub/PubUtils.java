package com.example.chirp.app.pub;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.kernel.Chirp;

public abstract class PubUtils {

	public static PubChirp toPubChirp(UriInfo uriInfo, Chirp chirp) {
		URI self = uriInfo.getBaseUriBuilder()
				.path("chirps")
				.path(chirp.getId().getId())
				.build();
		
		URI userLink = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(chirp.getUser().getUsername())
				.build();

		URI chirpsLink = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(chirp.getUser().getUsername())
				.path("chirps")
				.build();
		
		return new PubChirp(chirp.getId().getId(), 
				            chirp.getContent(), 
				            self, 
				            chirpsLink, 
				            userLink);
	}
	
}
