package com.example.chirp.pub;

import java.net.URI;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubChirp {

	private final URI self;
	private final String id;
	private final String content;

	@JsonCreator
	public PubChirp(@JsonProperty("self") URI self, 
					@JsonProperty("id") String id, 
					@JsonProperty("content") String content) {
		super();
		this.self = self;
		this.id = id;
		this.content = content;
	}

	public URI getSelf() {
		return self;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
