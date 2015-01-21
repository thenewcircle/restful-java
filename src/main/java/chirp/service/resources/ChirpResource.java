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
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.Chirp;
import chirp.model.ChirpId;
import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.caching.CacheableResponseBuilder;
import chirp.service.resprentations.ChirpCollectionRepresentation;
import chirp.service.resprentations.ChirpRepresentation;

@Path("/chirps/{username}")
public class ChirpResource {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private Response createSingleResponse(Request request,
			final UriInfo uriInfo, String username, String chirpId) {

		final User user = UserRepository.getInstance().getUser(username);

		final Chirp chirp = user.getChirp(new ChirpId(chirpId));

		return new CacheableResponseBuilder()
				.addLinksCommand(
						new CacheableResponseBuilder.AddLinksCommand() {
							public Link[] execute() {

								Link[] links = {

										Link.fromUriBuilder(
												uriInfo.getAbsolutePathBuilder())
												.rel("self").build(),

										Link.fromUriBuilder(
												uriInfo.getBaseUriBuilder()
														.path(uriInfo
																.getPathSegments()
																.get(0)
																.getPath()))
												.rel("up")
												.title(user.getRealname()
														+ "'s chirps").build(),

										Link.fromUriBuilder(
												uriInfo.getBaseUriBuilder()
														.path("/user")
														.path(user
																.getUsername()))
												.rel("related")
												.title(user.getRealname())
												.build() };
								return links;
							};
						}).build(
						request,
						new ChirpRepresentation(chirp, uriInfo
								.getAbsolutePath(), false));
	}

	@GET
	@Path("{chirpId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChirp(@Context Request request,
			@Context UriInfo uriInfo, @PathParam("username") String username,
			@PathParam("chirpId") String chirpId) {

		return createSingleResponse(request, uriInfo, username, chirpId);

	}

	@HEAD
	@Path("{chirpId}")
	public Response headChirp(@Context Request request,
			@Context UriInfo uriInfo, @PathParam("username") String username,
			@PathParam("chirpId") String chirpId) {

		return createSingleResponse(request, uriInfo, username, chirpId);

	}

	@POST
	public Response createChirp(@PathParam("username") String username,
			@FormParam("content") String content) {

		Chirp chirp = UserRepository.getInstance().getUser(username)
				.createChirp(content);

		URI location = UriBuilder.fromResource(this.getClass())
				.path(chirp.getId().toString()).build(username);

		return Response.created(location).build();
	}

	private Response createCollectionResponse(Request request,
			final UriInfo uriInfo, String username) {

		final User user = UserRepository.getInstance().getUser(username);
		final Deque<Chirp> chirps = user.getChirps();

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
												chirps.getFirst().getId()
														.toString()))
										.rel("first").build(),

								Link.fromUriBuilder(
										uriInfo.getAbsolutePathBuilder().path(
												chirps.getLast().getId()
														.toString()))
										.rel("last").build() };
						return links;
					};
				}).build(request,
				new ChirpCollectionRepresentation(chirps, uriInfo));
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChirps(@Context Request request,
			@Context UriInfo uriInfo, @PathParam("username") String username) {

		return createCollectionResponse(request, uriInfo, username);

	}

	@HEAD
	public Response headChirps(@Context Request request,
			@Context UriInfo uriInfo, @PathParam("username") String username) {

		return createCollectionResponse(request, uriInfo, username);

	}
}
