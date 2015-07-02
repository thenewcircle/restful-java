package com.example.chirp.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//ignore anything the client sends that I don't know about
@JsonIgnoreProperties(ignoreUnknown=true)
public class PubUsers {

	private final URI self;
	
	private final List<PubUser> users = new ArrayList<>();

	@JsonCreator
	public PubUsers(@JsonProperty("self") URI self, 
			        @JsonProperty("users") Collection<PubUser> users) {
		this.self = self;

		if (users != null) {
			this.users.addAll(users);
		}
	}

	public URI getSelf() {
		return self;
	}

	public List<PubUser> getUsers() {
		return users;
	}
	
	
}
