package com.example.chirp.app;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.example.chirp.app.stores.UserStore;

@Path("/users")
public class UserResource {

	private final UserStore userStore = ChirpApplication.userStore;

	@PUT
	@Path("/{username}")
	public void createUser(@PathParam("username") String username, @FormParam("realname") String realname) {

		userStore.createUser(username, realname);
	}
}
