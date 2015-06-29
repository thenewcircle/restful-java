package com.example.chirp.app.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.example.chirp.kernel.stores.UserStore;

@Path("/users")
public class UserResource {

	UserStore usersStore = MemoryStoreUtil.usersStore;
	
	@POST
	public Response createUser(@FormParam("username") String username, 
							   @FormParam("realName") String realName) {
		
		usersStore.createUser(username, realName);
		
		return Response.noContent().build();
	}
	
}
