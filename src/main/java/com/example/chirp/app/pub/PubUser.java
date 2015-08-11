package com.example.chirp.app.pub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubUser {

	private final String username;
	private final String realName;

	public PubUser(String username, String realName, boolean justTesting) {
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
	public PubUser(@JsonProperty("username") String username, 
			       @JsonProperty("realName") String realName) {
		this.username = username;
		this.realName = realName;
	}

	public String getUsername() {
		return username;
	}

	public String getRealName() {
		return realName;
	}
	
}
