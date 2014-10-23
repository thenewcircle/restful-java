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

import chirp.model.Chirp;
import chirp.model.ChirpId;
import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.ChirpCollectionRepresentation;
import chirp.service.representations.ChirpRepresentation;

@Path("/chirps/{username}")
public class ChirpResource {

	private UserRepository userRepository = UserRepository.getInstance();

	@POST
	public Response createChirp(@PathParam("username") String username,
			@FormParam("content") String content) {

		Chirp chirp = userRepository.getUser(username).createChirp(content);

		return Response.created(
				UriBuilder.fromResource(this.getClass())
						.path(chirp.getId().toString()).build(username))
				.build();
	}

	private Response createSingleChirpResponse(boolean isGet, String username,
			String id, UriInfo uriInfo) {
		User user = userRepository.getUser(username);

		ResponseBuilder rb = (isGet) ? Response.ok(new ChirpRepresentation(user
				.getChirp(new ChirpId(id)), false, uriInfo
				.getAbsolutePathBuilder().build())) : Response.ok();

		rb.links(

				Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
						.rel("self").title("chirp").build(),

				Link.fromUriBuilder(
						uriInfo.getBaseUriBuilder().path(
								uriInfo.getPathSegments().get(0).getPath()
										+ "/"
										+ uriInfo.getPathSegments().get(1)
												.getPath())).rel("up")
						.title(user.getRealname() + " chrips").build(),

				Link.fromUriBuilder(
						uriInfo.getBaseUriBuilder().path("/users")
								.path(user.getUsername())).rel("related")
						.title(user.getRealname()).build());

		return rb.build();
	}

	@HEAD
	@Path("{id}")
	public Response headResponse(@PathParam("username") String username,
			@PathParam("id") String id, @Context UriInfo uriInfo) {
		return createSingleChirpResponse(false, username, id, uriInfo);
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getChirp(@PathParam("username") String username,
			@PathParam("id") String id, @Context UriInfo uriInfo) {
		return createSingleChirpResponse(true, username, id, uriInfo);

	}

	private Response createCollectionChirpResponse(boolean isGet,
			String username, UriInfo uriInfo) {
		User user = userRepository.getUser(username);
		Deque<Chirp> chirps = user.getChirps();

		ResponseBuilder rb = (isGet) ? Response
				.ok(new ChirpCollectionRepresentation(user.getChirps(),
						username, uriInfo)) : Response.ok();

		rb.links(Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
				.rel("self").title(user.getRealname() + "'s chirps").build());

		if (chirps.size() > 0) {
			rb.links(

					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									chirps.getFirst().getId().toString()))
							.rel("first").build(),

					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									chirps.getLast().getId().toString()))
							.rel("last").build());
		}

		return rb.build();
	}

	@HEAD
	public Response headChirps(@PathParam("username") String username,
			@Context UriInfo uriInfo) {

		return createCollectionChirpResponse(false, username, uriInfo);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAllChirps(@PathParam("username") String username,
			@Context UriInfo uriInfo) {

		return createCollectionChirpResponse(true, username, uriInfo);

	}

}
