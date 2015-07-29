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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserCollectionRepresentation;
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

	private Response createSingleResponse(boolean isGet, String username,
			UriInfo uriInfo) {
		User user = UserRepository.getInstance().getUser(username);

		ResponseBuilder rb = (isGet) ? Response.ok(new UserRepresentation(user,
				uriInfo.getAbsolutePath(), false)) : Response.ok();

		rb.links(
				Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
						.rel("self").title(user.getRealname()).build(),
				Link.fromUriBuilder(
						uriInfo.getBaseUriBuilder().path(
								uriInfo.getPathSegments().get(0).getPath()))
						.rel("up").title("All Users").build(),
				Link.fromUriBuilder(
						uriInfo.getBaseUriBuilder().path(ChirpResource.class))
						.rel("related").title(user.getRealname() + "'s Chirps")
						.build(username));

		return rb.build();

	}

	@GET
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUser(@PathParam("username") String username,
			@Context UriInfo uriInfo) {

		return createSingleResponse(true, username, uriInfo);
	}

	@HEAD
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response headUser(@PathParam("username") String username,
			@Context UriInfo uriInfo) {

		return createSingleResponse(false, username, uriInfo);

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllUsers(@Context UriInfo uriInfo) {

		Deque<User> users = UserRepository.getInstance().getUsers();

		ResponseBuilder rb = Response.ok(new UserCollectionRepresentation(
				UserRepository.getInstance().getUsers(), uriInfo));

		rb.links(
				Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
						.rel("self").title("All Users").build(),
				Link.fromUriBuilder(
						uriInfo.getAbsolutePathBuilder().path(
								users.getFirst().getUsername())).rel("first")
						.title(users.getFirst().getRealname()).build(),
				Link.fromUriBuilder(
						uriInfo.getAbsolutePathBuilder().path(
								users.getLast().getUsername())).rel("last")
						.title(users.getLast().getRealname()).build());

		return rb.build();

	}
}
