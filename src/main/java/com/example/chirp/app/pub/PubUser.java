package com.example.chirp.app.pub;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubUser {

	private final String username;
	private final String realname;

	private final URI self;
	private final URI chirpsLink;

	@JsonInclude(Include.NON_EMPTY)
	private final PubChirps chirps;

	public PubUser(URI chirpsLink, URI self, String username, String realname) {
		this(chirpsLink, self, username, realname, null);
	}

	@JsonCreator
	public PubUser(@JsonProperty("chirpsLInk") URI chirpsLink, @JsonProperty("self") URI self, @JsonProperty("username") String username,
			@JsonProperty("realname") String realname, @JsonProperty("chirps") PubChirps chirps) {

		this.self = self;
		this.chirpsLink = chirpsLink;
		this.username = username;
		this.realname = realname;

		this.chirps = chirps;
	}

	public PubChirps getChirps() {
		return chirps;
	}

	public URI getChirpsLink() {
		return chirpsLink;
	}

	public URI getSelf() {
		return self;
	}

	public String getUsername() {
		return username;
	}

	public String getRealname() {
		return realname;
	}

	@Override
	public String toString() {
		return "PubUser [username=" + username + ", realname=" + realname + "]";
	}
}
