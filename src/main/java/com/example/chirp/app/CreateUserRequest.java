package com.example.chirp.app;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class CreateUserRequest {

	private @Context UriInfo uriInfo;

	private @PathParam("username") String username;

	private @PathParam("lastName") String lastName;
	private @FormParam("firstName") String firstName;

	private @FormParam("realname") String realname;

	public UriInfo getUriInfo() {
		return uriInfo;
	}

	public String getUsername() {
		return username;
	}

	public String getRealname() {
		return realname;
	}

	public void validate() {
		if (username.contains(" ")) {
			throw new BadRequestException("The username cannot contain any spaces.");
		}
	}
}
