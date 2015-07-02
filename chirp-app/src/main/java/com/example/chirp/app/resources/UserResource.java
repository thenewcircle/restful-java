package com.example.chirp.app.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.chirp.kernel.Chirp;
import com.example.chirp.kernel.User;
import com.example.chirp.kernel.User.Variant;
import com.example.chirp.kernel.stores.UsersStore;
import com.example.chirp.pub.PubChirp;
import com.example.chirp.pub.PubChirps;
import com.example.chirp.pub.PubUser;
import com.example.chirp.pub.PubUsers;

@Named
@Path("/users")
public class UserResource {

	@Inject
	private UsersStore usersStore;

	// This constructor is used by Spring
	public UserResource() {
	}

	// This constructor is used by by the ChirpsResource
	public UserResource(UsersStore usersStore) {
		this.usersStore = usersStore;
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
    public Response getUser(@Context UriInfo uriInfo, 
    		               @PathParam("username") String username,
    		               @QueryParam("variant") String variant) {

		User user = usersStore.getUser(username);
        // URI self = UriBuilder.fromResource(UserResource.class).path(username).build();
        // URI self = uriInfo.getAbsolutePathBuilder().build();
        // URI self = uriInfo.getAbsolutePath();
		// URI parent = uriInfo.getAbsolutePathBuilder().path("..").build();
		// URI parent = uriInfo.getAbsolutePathBuilder().path("test").build();

		URI chirpsLink = uriInfo.getAbsolutePathBuilder().path("chirps").build();
		URI allUsersLink = uriInfo.getBaseUriBuilder().path("users").build();
		
		PubUser pubUser = user.toPubUser(variant, uriInfo);
		return Response.ok(pubUser)
				.link(chirpsLink, "chirps")
				.link(allUsersLink, "all-users")
				.build();
	}
	
	@POST
    @Path("/{username}/chirps")
    public Response createChirp(@Context UriInfo uriInfo, 
    		                    @PathParam("username") String username,
    		                    String content) {

		User user = usersStore.getUser(username);
		Chirp chirp = user.createChirp(content);
		usersStore.updateUser(user);
		
		URI location = uriInfo.getBaseUriBuilder().path("chirps").path(chirp.getId().toString()).build();
		return Response.created(location).build();
	}
	
	@GET
    @Path("/{username}/chirps")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getChirps(@Context UriInfo uriInfo, 
    		                  @PathParam("username") String username, 
    		                  @QueryParam("offset") int offset, 
    		                  @QueryParam("size") int size) {

		User user = usersStore.getUser(username);
		List<PubChirp> chirps = new ArrayList<>();
		for (Chirp chirp : user.getChirps()) {
			PubChirp pubChirp = chirp.toPubChirp(uriInfo);
			chirps.add(pubChirp);
		}

		URI self = uriInfo.getAbsolutePath();
		PubChirps pubChirps = new PubChirps(self, offset, size, chirps);
		
		URI userLink = uriInfo.getBaseUriBuilder().path("users").path(username).build();
		URI allUsersLink = uriInfo.getBaseUriBuilder().path("users").build();
		
		return Response.ok(pubChirps)
				.link(userLink, "user")
				.link(allUsersLink, "all-users")
				.build();
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

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getUsers(@Context UriInfo uriInfo,
			                 @QueryParam("variant") String variant) {
		Deque<User> que = usersStore.getUsers();
		
		URI thisUri = uriInfo.getAbsolutePath();
		
		List<PubUser> users = new ArrayList<>();
		for (User user : que) {
			PubUser pubUser = user.toPubUser(variant, uriInfo);
			users.add(pubUser);
		}
		
		PubUsers pubUsers = new PubUsers(thisUri, users);
		return Response.ok(pubUsers).build();
	}
	
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
