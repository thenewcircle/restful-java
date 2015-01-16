package chirp.service.resources;

import java.net.URI;
import java.util.Deque;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.resprentations.UserCollectionRepresentation;
import chirp.service.resprentations.UserRepresentation;

@Path("/users")
public class UserResource {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Response createSingleResponse(boolean isGet, String username,
			UriInfo uriInfo, Request request) {
		User user = UserRepository.getInstance().getUser(username);
		logger.info("Retrived username={}", username);
		
		UserRepresentation userRep = new UserRepresentation(user,
				uriInfo.getAbsolutePath(), false);
		
		EntityTag eTag = new EntityTag(Integer.toHexString(userRep
				.hashCode()));

		ResponseBuilder rb = request.evaluatePreconditions(eTag);

		if (rb == null) {

			rb = (isGet) ? Response.ok(userRep) : Response.ok();
			rb.tag(eTag);
		
		rb.links(

				Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
						.rel("self").build(),

				Link.fromUriBuilder(
						uriInfo.getBaseUriBuilder().path(
								uriInfo.getPathSegments().get(0).getPath()))
						.rel("up").title("all users").build(),

				Link.fromUriBuilder(
						uriInfo.getBaseUriBuilder().path("/chirps")
								.path(user.getUsername())).rel("related")
						.title(user.getRealname() + "'s chirps").build());
		}
		
		CacheControl cc = new CacheControl();
		cc.setMaxAge(300); // how long the client should wait before asking for
							// the chirp again
		cc.setNoStore(true); // don't store on disk
		cc.setPrivate(true); // make it public or is this a good idea?
		rb.cacheControl(cc); // set it in the header


		return rb.build();
	}

	@HEAD
	@Path("{username}")
	public Response getUserHead(@PathParam("username") String username,
			@Context UriInfo uriInfo, @Context Request request) {

		return createSingleResponse(false, username, uriInfo, request);
	}

	// http://localhost:8080/users/username
	@GET
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUser(@PathParam("username") String username,
			@Context UriInfo uriInfo, @Context Request request) {
		
		return createSingleResponse(true, username, uriInfo, request);
	}

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

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

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUsers(@Context UriInfo uriInfo,
			@DefaultValue("20") @QueryParam("limit") int limit) {

		Deque<User> users = UserRepository.getInstance().getUsers();

		ResponseBuilder rb = Response.ok(new UserCollectionRepresentation(
				UserRepository.getInstance().getUsers(), uriInfo));

		if (users.size() > 0) {
			rb.links(

					Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
							.rel("self").build(),

					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									users.getFirst().getUsername()))
							.rel("first").build(),

					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									users.getLast().getUsername())).rel("last")
							.build());
		}

		return rb.build();

	}

}
