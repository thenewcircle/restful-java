package com.example.chirp.pub;

import java.net.URI;
import java.net.URL;

public class PubChirp {

	private final URI self;
	private final URI user;
	private final String id;
	private final String content;

	public PubChirp(URI self, URI user, String id, String content) {
		super();
		this.self = self;
		this.user = user;
		this.id = id;
		this.content = content;
	}

	public URI getUser() {
		return user;
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
