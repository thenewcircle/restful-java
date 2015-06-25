package chirp.service.resources;

import java.net.URI;
import java.util.Deque;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
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
public class UsersResource {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Use this to create a new user than does not already exist in the system.
	 * 
	 * DuplicationEntityExceptions handled by DuplicatedEntityExceptionMapper
	 * class
	 * 
	 * @param username
	 * @param realname
	 * @return
	 */
	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {
		logger.info("Creating user {} with realname {}", username, realname);

		UserRepository.getInstance().createUser(username, realname);
		URI location = UriBuilder.fromResource(this.getClass()).path(username)
				.build();
		return Response.created(location).build();

	}

	private Response createSingleUserResponse(boolean isGet, String username,
			UriInfo uriInfo) {

		logger.info("Getting info for user {}", username);

		User user = UserRepository.getInstance().getUser(username);

		ResponseBuilder rb = (isGet) ? Response.ok(new UserRepresentation(user,
				uriInfo.getAbsolutePathBuilder().build(), false)) : Response
				.ok();

		rb.links(
				Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
						.rel("self").title(user.getUsername()).build(),

				Link.fromUriBuilder(
						uriInfo.getBaseUriBuilder().path(
								uriInfo.getPathSegments().get(0).getPath()))
						.rel("up").title("all users").build("users"),

				Link.fromUriBuilder(
						uriInfo.getBaseUriBuilder().path(ChirpsResource.class))
						.rel("related").title(user.getRealname() + "'s Chirps")
						.build(user.getUsername()));

		return rb.build();
	}

	// http://localhost:8080/users/student
	@Path("{username}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUser(@PathParam("username") String username,
			@Context UriInfo uriInfo) {
		return createSingleUserResponse(true, username, uriInfo);

	}

	@HEAD
	public Response headUser(@PathParam("username") String username,
			@Context UriInfo uriInfo) {

		return createSingleUserResponse(false, username, uriInfo);

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllUsers(@Context UriInfo uriInfo,
			@QueryParam("offset") Integer offset,
			@QueryParam("limit") Integer limit) {

		int correctedOffset = 0;
		int correctedLimit = 20;

		if (offset != null) {
			correctedOffset = offset;
		}

		if (limit != null) {
			correctedLimit = limit;
		}

		Deque<User> users = UserRepository.getInstance().getUsers();

		ResponseBuilder rb = Response.ok(new UserCollectionRepresentation(
				uriInfo));

		if (users.size() > 0) {
			rb.links(
					Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
							.rel("self").title("All Users").build(),

					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									users.getFirst().getUsername()))
							.rel("first").title(users.getFirst().getRealname())
							.build(),

					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									users.getLast().getUsername())).rel("last")
							.title(users.getLast().getRealname()).build(),

					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder()
									.queryParam(
											"offset",
											Integer.toString(correctedOffset
													+ correctedLimit))
									.queryParam("limit",
											Integer.toString(correctedLimit)))
							.rel("next").title("next").build());
		}

		return rb.build();
	}
}