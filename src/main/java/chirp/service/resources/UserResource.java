package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.CollectionRepresentation;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

@Path("/users")
public class UserResource {

	private final UserRepository userRepository = UserRepository.getInstance();

	@POST
	public Response createUser(final @FormParam("username") String username,
			final @FormParam("realname") String realname) {

		userRepository.createUser(username, realname);

		// http://localhost:8080/users</username>
		final URI location = UriBuilder.fromResource(this.getClass())
				.path(username).build();

		// should return a 201 status code with the URL to the user created
		// in the location header.
		return Response.created(location).build();
	}
	
	protected Response createHeadGetResponse(boolean isHead, String username) {
	
		final User user = userRepository.getUser(username);
		ResponseBuilder rb = (isHead) ?  rb = Response.ok() : Response.ok(new UserRepresentation(user, false));
		
		return rb.link(UriBuilder.fromPath("/posts/" + user.getUsername()).build(), "related")
		.link(UriBuilder.fromPath("/users").build(), "up")
		.link(UriBuilder.fromPath("/users/" + user.getUsername()).build(), "self")
		.build();
	}	
	
	@HEAD
	@Path("/{username}")
	public Response head(@PathParam("{username}") String username) {
		return createHeadGetResponse(true, username);
	}

	@GET
	@Path("/{username}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUser(final @PathParam("username") String username) {
		return createHeadGetResponse(false,username);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public CollectionRepresentation<UserRepresentation> getAllUsers() {
		return new UserCollectionRepresentation(userRepository.getUsers());
	}

}
