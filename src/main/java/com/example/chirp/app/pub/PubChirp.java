package com.example.chirp.app.pub;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PubChirp {

	private final String id;
	private final String content;
	
	private final URI self;
	private final URI chirpsLink;
	private final URI userLink;

	public PubChirp(@JsonProperty("id") String id, 
					@JsonProperty("content") String content, 
					@JsonProperty("self") URI self, 
					@JsonProperty("chirpsLink") URI chirpsLink, 
					@JsonProperty("userLink") URI userLink) {

		this.id = id;
		this.content = content;
		this.self = self;
		this.chirpsLink = chirpsLink;
		this.userLink = userLink;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public URI getSelf() {
		return self;
	}

	public URI getChirpsLink() {
		return chirpsLink;
	}

	public URI getUserLink() {
		return userLink;
	}
}
