package com.example.chirp.app.pub;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubUser {

	private final String username;
	private final String realname;

	private final URI self;
	private final URI chirpsLink;

	@JsonCreator
	public PubUser(@JsonProperty("chirpsLInk") URI chirpsLink, @JsonProperty("self") URI self, @JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		this.self = self;
		this.chirpsLink = chirpsLink;
		this.username = username;
		this.realname = realname;
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
