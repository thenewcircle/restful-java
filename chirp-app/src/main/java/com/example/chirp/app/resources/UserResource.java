package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.kernel.User;
import com.example.chirp.kernel.stores.UsersStore;
import com.example.chirp.pub.PubUser;

@Path("/users")
public class UserResource {

	private UsersStore usersStore;
	
	public UserResource(@Context Application application) {
		this.usersStore = (UsersStore)application.getProperties().get(UsersStore.class.getName());
	}
	
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//    @Path("/{username}")
//    public String getUserName(@PathParam("username") String username) {
//            return usersStore.getUser(username).getRealname();
//    }
	
	@GET
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{username}")
    public PubUser getUser(@Context UriInfo uriInfo, 
    		               @PathParam("username") String username) {
            User user = usersStore.getUser(username);
            // URI self = UriBuilder.fromResource(UserResource.class).path(username).build();
            // URI self = uriInfo.getAbsolutePathBuilder().build();
            URI self = uriInfo.getAbsolutePath();
            return user.toPubUser(self);
	}
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//    @Path("/{username}")
//    public PubUser getUserJson(@Context UriInfo uriInfo, 
//    		                   @PathParam("username") String username) {
//            User user = usersStore.getUser(username);
//            // URI self = UriBuilder.fromResource(UserResource.class).path(username).build();
//            // URI self = uriInfo.getAbsolutePathBuilder().build();
//            URI self = uriInfo.getAbsolutePath();
//            return user.toPubUser(self);
//	}
	
//	@GET
//	@Produces(MediaType.APPLICATION_XML)
//    @Path("/{username}")
//    public PubUser getUserXml(@Context UriInfo uriInfo, 
//    		                   @PathParam("username") String username) {
//            User user = usersStore.getUser(username);
//            URI self = uriInfo.getAbsolutePath();
//            return user.toPubUser(self);
//	}
	
	@PUT
	public Response createUser(@Context UriInfo uriInfo,
			                   @FormParam("username") String username, 
							   @FormParam("realName") String realName) {
		
		usersStore.createUser(username, realName);
		
        // URI newLocation = uriInfo.getAbsolutePathBuilder().path(username).build();
        // URI newLocation = uriInfo.getBaseUriBuilder().path("users").path(username).build();
		URI newLocation = UriBuilder.fromResource(UserResource.class).path(username).build();
		return Response.created(newLocation).build();
	}
}
