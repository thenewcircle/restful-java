package com.example.chirp.app.resources;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.UsersStoreAccessor;
import com.example.chirp.app.providers.DeprecatedRequest;
import com.example.chirp.kernel.Chirp;
import com.example.chirp.kernel.ChirpId;
import com.example.chirp.kernel.User;
import com.example.chirp.kernel.exceptions.NoSuchEntityException;
import com.example.chirp.kernel.stores.UsersStore;
import com.example.chirp.pub.OldChirp;
import com.example.chirp.pub.PubChirp;

@Named
@Path("/chirps")
public class ChirpResource {

	private UsersStore usersStore;

	public ChirpResource() {
	}

	@Autowired
	  @Required
	  public void setUsersStoreAccessor(UsersStoreAccessor usersStoreAccessor) {
		  this.usersStore = usersStoreAccessor.getUsersStore();
	  }

	/** 
	 * this call is a **REALLT** bad idea for several reason
	 * 1) We have a path collision with ../chirps/{id}. The only reason this works is because of the different between GET and POST
	 * 2) We are not in a logical context. We are saying create a post at a user resource. It makes more sense to create a post the posts resource 
	 * @param username the user's name
	 * @param application injected reference to the Application
	 * @param uriInfo injected reference to the UriInfo
	 * @param content the plain/text message that represents this post.
	 * @return a response object
	 */
	@DeprecatedRequest // applies our custom "deprecated" filter to this call only
	@POST
	@Path("/{username}")
	@Produces({"application/xml", "application/json"})
	public Response createChirp(@PathParam("username") String username,
								@Context Application application,
								@Context UriInfo uriInfo,
								String content) {
		
		UserResource resource = new UserResource(usersStore);
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
