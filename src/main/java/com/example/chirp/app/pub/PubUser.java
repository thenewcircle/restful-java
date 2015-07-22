package com.example.chirp.app.pub;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubUser {

	private final String username;
	private final String realname;

	private final URI self;
	private final URI chirpsLink;

	public PubUser(URI chirpsLink, URI self, String username, String realname) {
		this(chirpsLink, self, username, realname, Collections.<PubChirp> emptyList(), Collections.<URI> emptyList());
	}

	@JsonCreator
	public PubUser(@JsonProperty("chirpsLInk") URI chirpsLink, @JsonProperty("self") URI self, @JsonProperty("username") String username,
			@JsonProperty("realname") String realname, @JsonProperty("chirps") List<PubChirp> chirps, @JsonProperty("chirpLinks") List<URI> chirpLinks) {

		this.self = self;
		this.chirpsLink = chirpsLink;
		this.username = username;
		this.realname = realname;

		this.chirps = Collections.unmodifiableList(chirps);
		this.chirpLinks = Collections.unmodifiableList(chirpLinks);
	}

	public List<URI> getChirpLinks() {
		return chirpLinks;
	}

	public List<PubChirp> getChirps() {
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
