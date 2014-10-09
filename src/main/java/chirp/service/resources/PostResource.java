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

import chirp.model.Post;
import chirp.model.Timestamp;
import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.PostCollectionRepresentation;
import chirp.service.representations.PostRepresentation;

@Path("/posts/{username}")
public class PostResource {

	private UserRepository userRepository = UserRepository.getInstance();

	@POST
	public Response createChirp(@PathParam("username") String username,
			@FormParam("content") String content) {

		Post post = userRepository.getUser(username).createPost(content);
		
		return Response.created(
				UriBuilder.fromResource(this.getClass())
						.path(post.getTimestamp().toString()).build(username))
				.build();
	}

	private Response createSinglePostResponse(boolean isGet, String username,
			String timestamp, UriInfo uriInfo) {
		User user = userRepository.getUser(username);

		ResponseBuilder rb = (isGet) ? Response.ok(new PostRepresentation(user
				.getPost(new Timestamp(timestamp)), false, uriInfo.getAbsolutePathBuilder().build())) : Response.ok();

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
	@Path("{timestamp}")
	public Response headResponse(@PathParam("username") String username,
			@PathParam("timestamp") String timestamp, @Context UriInfo uriInfo) {
		return createSinglePostResponse(false, username, timestamp, uriInfo);
	}

	@GET
	@Path("{timestamp}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPost(@PathParam("username") String username,
			@PathParam("timestamp") String timestamp, @Context UriInfo uriInfo) {
		return createSinglePostResponse(true, username, timestamp, uriInfo);

	}

	private Response createCollectionPostResponse(boolean isGet,
			String username, UriInfo uriInfo) {
		User user = userRepository.getUser(username);
		Deque<Post> posts = user.getPosts();

		ResponseBuilder rb = (isGet) ? Response
				.ok(new PostCollectionRepresentation(user.getPosts(), username, uriInfo))
				: Response.ok();

		rb.links(Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
				.rel("self").title(user.getRealname() + "'s chirps").build());

		if (posts.size() > 0) {
			rb.links(

					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									posts.getFirst().getTimestamp().toString()))
							.rel("first").build(),

					Link.fromUriBuilder(
							uriInfo.getAbsolutePathBuilder().path(
									posts.getLast().getTimestamp().toString()))
							.rel("last").build());
		}

		return rb.build();
	}

	@HEAD
	public Response headChirps(@PathParam("username") String username,
			@Context UriInfo uriInfo) {

		return createCollectionPostResponse(false, username, uriInfo);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAllChirps(@PathParam("username") String username,
			@Context UriInfo uriInfo) {

		return createCollectionPostResponse(true, username, uriInfo);

	}

}
