package chirp.service.resources;

import java.net.URI;
import java.util.Deque;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
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
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.caching.CacheableResponseBuilder;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

@Path("/users")
public class UserResource {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Response createSingleResponse(Request request,
			final UriInfo uriInfo, String username) {

		final User user = UserRepository.getInstance().getUser(username);

		logger.info("Building {} repsonse for username={}",
				request.getMethod(), username);

		return new CacheableResponseBuilder().addLinksCommand(
				new CacheableResponseBuilder.AddLinksCommand() {
					public Link[] execute() {

						Link[] links = {

								Link.fromUriBuilder(
										uriInfo.getAbsolutePathBuilder())
										.rel("self").build(),

								Link.fromUriBuilder(
										uriInfo.getBaseUriBuilder().path(
												uriInfo.getPathSegments()
														.get(0).getPath()))
										.rel("up").title("all users").build(),

								Link.fromUriBuilder(
										uriInfo.getBaseUriBuilder()
												.path("/chirps")
												.path(user.getUsername()))
										.rel("related")
										.title(user.getRealname() + "'s chirps")
										.build() };
						return links;
					};
				}).build(request,
				new UserRepresentation(user, uriInfo.getAbsolutePath(), false));
	}

	@HEAD
	@Path("{username}")
	public Response headUser(@Context Request request,
			@Context UriInfo uriInfo, @PathParam("username") String username) {

		return createSingleResponse(request, uriInfo, username);
	}

	// http://localhost:8080/users/username
	@GET
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUser(@Context Request request, @Context UriInfo uriInfo,
			@PathParam("username") String username) {

		return createSingleResponse(request, uriInfo, username);
	}

	private Response createCollectionResponse(Request request,
			final UriInfo uriInfo) {

		final Deque<User> users = UserRepository.getInstance().getUsers();

		logger.info("Building {} repsonse for all users", request.getMethod());

		return new CacheableResponseBuilder().addLinksCommand(
				new CacheableResponseBuilder.AddLinksCommand() {
					public Link[] execute() {

						Link[] links = {

								Link.fromUriBuilder(
										uriInfo.getAbsolutePathBuilder())
										.rel("self").build(),

								Link.fromUriBuilder(
										uriInfo.getAbsolutePathBuilder().path(
												users.getFirst().getUsername()))
										.rel("first").build(),

								Link.fromUriBuilder(
										uriInfo.getAbsolutePathBuilder().path(
												users.getLast().getUsername()))
										.rel("last").build() };
						return links;
					};
				}).build(request,
				new UserCollectionRepresentation(uriInfo));
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUsers(@Context Request request,
			@Context UriInfo uriInfo,
			@DefaultValue("20") @QueryParam("limit") int limit) {

		return createCollectionResponse(request, uriInfo);

	}

	@HEAD
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response headUser(@Context Request request,
			@Context UriInfo uriInfo,
			@DefaultValue("20") @QueryParam("limit") int limit) {

		return createCollectionResponse(request, uriInfo);

	}

	@POST
	public Response createUser(

			@NotNull(message = "{usersresource.createuserfromrequest.username.notnull}") @FormParam("username") String username,
			@NotNull(message = "everyone has realname, please provide yours.") @FormParam("realname") String realname) {

		// remember to validate input parameters in product code.

		logger.info("Creating a user with realname={} and username={}",
				realname, username);

		UserRepository.getInstance().createUser(username, realname);

		// The URL for the resource created is
		// http://localhost:8080/users/<username> or
		// http://localhost:8080/users/student
		URI location = UriBuilder.fromResource(this.getClass()).path(username)
				.build();

		return Response.created(location).build();

	}

}
