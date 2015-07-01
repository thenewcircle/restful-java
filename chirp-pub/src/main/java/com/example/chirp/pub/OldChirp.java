package com.example.chirp.pub;

import java.net.URI;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OldChirp {

	private final URI oldSelf;
	private final URI oldUser;
	private final String oldId;
	private final String oldContent;

	@JsonCreator
	public OldChirp(@JsonProperty("oldSelf") URI oldSelf, 
					@JsonProperty("oldUser") URI oldUser, 
					@JsonProperty("oldId") String oldId, 
					@JsonProperty("oldContent") String oldContent) {
		super();
		this.oldSelf = oldSelf;
		this.oldUser = oldUser;
		this.oldId = oldId;
		this.oldContent = oldContent;
	}

	public URI getOldSelf() {
		return oldSelf;
	}

	public URI getOldUser() {
		return oldUser;
	}

	public String getOldId() {
		return oldId;
	}

	public String getOldContent() {
		return oldContent;
	}
}
