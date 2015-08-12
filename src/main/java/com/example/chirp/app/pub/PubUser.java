package com.example.chirp.app.pub;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubUser {

	private final String username;
	private final String realName;
	private final URI self;

	@JsonInclude(Include.NON_EMPTY)
	private final PubChirps chirps;
	
	public PubUser(@JsonProperty("self") URI self, 
				   @JsonProperty("username") String username, 
			       @JsonProperty("realName") String realName,
			       @JsonProperty("chirps") PubChirps pubChirps) {
		this.self = self;
		this.username = username;
		this.realName = realName;
		this.chirps = pubChirps;
	}

	public PubChirps getChirps() {
		return chirps;
	}

	public URI getSelf() {
		return self;
	}
	
	public String getUsername() {
		return username;
	}

	public String getRealName() {
		return realName;
	}
	
}
