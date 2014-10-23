package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

@Path("/users")
public class UserResource {

	private final UserRepository userRepository = UserRepository.getInstance();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		logger.info("Creating a user with username={} and realname={}",
				username, realname);

		userRepository.createUser(username, realname);
		return Response
				.created(
						UriBuilder.fromResource(this.getClass()).path(username)
								.build()).build();

	}

	private Response createSingleUserResponse(boolean isGet, String username,
			UriInfo uriInfo) {
		User user = userRepository.getUser(username); // will validate if the
														// users exists
		URI self = uriInfo.getAbsolutePathBuilder().build();
		ResponseBuilder rb = (isGet) ? Response.ok(new UserRepresentation(
				false, self, user)) : Response.ok();
		return rb.build();
	}

	@GET
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("username") String username,
			@Context UriInfo uriInfo) {
		return createSingleUserResponse(true, username, uriInfo);
	}

	@HEAD
	@Path("{username}")
	public Response getUserHeaders(@PathParam("username") String username,
			@Context UriInfo uriInfo) {
		return createSingleUserResponse(false, username, uriInfo);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public UserCollectionRepresentation getUsers(@Context UriInfo uriInfo) {

		return new UserCollectionRepresentation(userRepository.getUsers(),
				uriInfo);

	}

}
