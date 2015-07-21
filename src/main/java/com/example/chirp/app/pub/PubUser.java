package com.example.chirp.app.pub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubUser {

	private String username;
	private String realname;

	private PubUser() {
	}

	@JsonCreator
	public PubUser(@JsonProperty("username") String username, @JsonProperty("realname") String realname) {
		this.username = username;
		this.realname = realname;
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
