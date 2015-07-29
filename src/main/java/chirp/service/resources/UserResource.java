package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import chirp.model.UserRepository;
import chirp.service.representations.UserRepresentation;

@Path("/users")
public class UserResource {

	@POST
	public Response addUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		Response response = null;

		UserRepository.getInstance().createUser(username, realname);
		URI location = UriBuilder.fromResource(this.getClass()).path(username)
				.build();

		response = Response.created(location).build();

		return response;

	}

	@GET
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserRepresentation getUser(@PathParam("username") String username,
			@Context UriInfo uriInfo) {
		return new UserRepresentation(UserRepository.getInstance().getUser(
				username), uriInfo.getAbsolutePath());
	}
}
