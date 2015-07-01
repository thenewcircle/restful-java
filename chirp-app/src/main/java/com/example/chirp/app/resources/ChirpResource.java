package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.kernel.Chirp;
import com.example.chirp.kernel.ChirpId;
import com.example.chirp.kernel.User;
import com.example.chirp.kernel.exceptions.NoSuchEntityException;
import com.example.chirp.kernel.stores.UsersStore;
import com.example.chirp.pub.OldChirp;
import com.example.chirp.pub.PubChirp;

@Path("/chirps")
public class ChirpResource {

	private final UsersStore usersStore;

	public ChirpResource(@Context Application application) {
		this.usersStore = (UsersStore)application.getProperties().get(UsersStore.class.getName());
	}

	
	@POST
	@Path("/{username}")
	@Produces({"application/xml", "application/json"})
	public Response createChirp(@PathParam("username") String username,
								@Context Application application,
								@Context UriInfo uriInfo,
								String content) {
		
		UserResource resource = new UserResource(application);
		return resource.createChirp(uriInfo, username, content);
	}
	
	@GET
	@Path("/{id}")
	@Produces({"application/xml", "application/json",
		       "application/v2+xml", "application/v2+json",
		       "application/xml;version=2", "application/json;version=2"})
	public Response getPubChirp(@Context UriInfo uriInfo,
							    @PathParam("id") String id) {
		
		Chirp chirp = findChirp(id);
		PubChirp pubChirp = chirp.toPubChirp(uriInfo);
		
		String username = chirp.getUser().getUsername();
		URI userLink = uriInfo.getBaseUriBuilder().path("users").path(username).build();
		URI allChirps = uriInfo.getBaseUriBuilder().path("users").path(username).path("chirps").build();

		return Response.ok(pubChirp)
				.link(userLink, "user")
				.link(allChirps, "all-chirps")
				.build();
	}

	@GET
	@Path("/{id}")
	@Produces({"application/v1+xml", "application/v1+json"})
//		       "application/xml;version=1", "application/json;version=1"})
	public Response getOldChirp(@Context UriInfo uriInfo,
							 @PathParam("id") String id) {
		
		OldChirp chirp = findChirp(id).toOldChirp(uriInfo);
		return Response.ok(chirp).build();
	}

//	@Path("/{id}")
//	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//	public Response getChirp(@Context UriInfo uriInfo,
//							 @PathParam("id") String id,
//							 @HeaderParam("vsp-Version") String version) {
//		
//		if ("new".equals(version)) {
//			PubChirp chirp = findChirp(id).toPubChirp(uriInfo);
//			return Response.ok(chirp).build();
//		} else {
//			OldChirp chirp = findChirp(id).toOldChirp(uriInfo);
//			return Response.ok(chirp).build();
//		}
//	}
	
	private Chirp findChirp(String id) {
		for (User user : usersStore.getUsers()) {
			try {
				return user.getChirp(new ChirpId(id));
				
			} catch (NoSuchEntityException ignored) {
				/// ignored
			}
		}
		
		throw new NoSuchEntityException();
	}
}
