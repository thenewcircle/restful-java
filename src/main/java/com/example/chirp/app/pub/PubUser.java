package com.example.chirp.app.pub;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubUser {

	private final String username;
	private final String realName;
	private final URI self;
	
	public PubUser(URI self, String username, String realName, boolean justTesting) {
		this.self = self;
		this.username = username;
		this.realName = realName;
	}
/*
	{
		"user-name": "mickey",
		"realName": "Mickey Mouse"
	}
 */
	@JsonCreator
	public PubUser(@JsonProperty("self") URI self, 
					@JsonProperty("username") String username, 
			       @JsonProperty("realName") String realName) {
		this.self = self;
		this.username = username;
		this.realName = realName;
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
