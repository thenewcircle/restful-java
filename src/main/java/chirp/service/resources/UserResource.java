package chirp.service.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

@Path("/user")
public class UserResource {

	private UserRepository userRepository = UserRepository.getInstance();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUserUsingJSON(UserRepresentation user) {

		userRepository.createUser(user.getUsername(), user.getRealname());

		// using a java.net.URI for creating a location
		// URI location = URI.create("/user/" + username);
		// return Response.created(location).build();

		return Response.created(
				UriBuilder.fromPath("user").path(user.getUsername()).build())
				.build();

	}

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		userRepository.createUser(username, realname);

		// using a java.net.URI for creating a location
		// URI location = URI.create("/user/" + username);
		// return Response.created(location).build();

		return Response.created(
				UriBuilder.fromPath("user").path(username).build()).build();

	}

	@GET
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("username") String username) {
		return createHeadResponse(true, username);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public UserCollectionRepresentation getAllUsers() {
		return new UserCollectionRepresentation(userRepository.getUsers());
	}

	private Response createHeadResponse(boolean isGet, String username) {
		User user = userRepository.getUser(username);

		ResponseBuilder rb = (isGet) ? Response.ok(new UserRepresentation(user,
				false)) : Response.ok();
		rb.links(
				Link.fromUri("/user/" + username).rel("self")
						.title(user.getRealname()).build(),
				Link.fromUri("/user/").rel("up").build(),
				Link.fromUri("/post/" + username).rel("related")
						.title(user.getRealname() + "chirps").build());

		return rb.build();
	}

	@HEAD
	@Path("{username}")
	public Response headResponse(@PathParam("username") String username) {
		return createHeadResponse(false, username);
	}

}
