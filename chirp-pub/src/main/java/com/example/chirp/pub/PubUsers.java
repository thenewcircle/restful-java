package com.example.chirp.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PubUsers {

	private final URI self;
	
	private final List<PubUser> users = new ArrayList<>();

	public PubUsers(URI self, Collection<PubUser> users) {
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
