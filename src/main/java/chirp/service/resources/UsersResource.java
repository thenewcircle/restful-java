package chirp.service.resources;

import java.util.Deque;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
import chirp.service.representations.UserRepresentation;
import chirp.service.representations.UsersCollectionRepresentation;

@Path("/users")
public class UsersResource {

	private final UserRepository repo = UserRepository.getInstance();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		logger.info("Creating a user with realname={} and username={}",
				realname, username);

		repo.createUser(username, realname);

		return Response
				.created(
						UriBuilder.fromResource(this.getClass()).path(username)
								.build()).build();
	}

	@GET
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserRepresentation getUser(@PathParam("username") String username,
			@Context UriInfo uriInfo) {
		return new UserRepresentation(repo.getUser(username), uriInfo
				.getAbsolutePathBuilder().build(), false);
	}

	public Response createHeadorGetResponse(boolean isGet,
			@Context UriInfo uriInfo) {
		Deque<User> users = repo.getUsers();
		ResponseBuilder rb = (isGet) ? Response
				.ok(new UsersCollectionRepresentation(repo.getUsers(), uriInfo))
				: Response.ok();

		if (users.size() > 0) {

			rb.links(
					Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
							.rel("self").build(),
					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									users.getFirst().getUsername())).rel("first")
							.title(users.getFirst().getRealname()).build(),
					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									users.getLast().getUsername())).rel("last")
							.title(users.getLast().getRealname()).build());
		}

		return rb.build();

	}

	@HEAD
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response headAllUsers(@Context UriInfo uriInfo) {
		return createHeadorGetResponse(false, uriInfo);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllUsers(@Context UriInfo uriInfo) {
		return createHeadorGetResponse(true, uriInfo);
	}

}
