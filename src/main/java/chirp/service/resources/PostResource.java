package chirp.service.resources;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.Post;
import chirp.model.Timestamp;
import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.PostCollectionRepresentation;
import chirp.service.representations.PostRepresentation;

@Path("/posts/{username}")
// chose this over /user/{usernamae}/post to decouple users and posts
public class PostResource {

	private final UserRepository userRepository = UserRepository.getInstance();

	// Create a @POST createPost method that also creates the timestamp for the
	// post
	@POST
	public Response createPost(@PathParam("username") String username,
			@FormParam("content") String content) {

		final User user = userRepository.getUser(username);
		final Post post = user.createPost(content);

		final URI location = UriBuilder.fromPath(
				"/posts/" + username + "/" + post.getTimestamp()).build();
		return Response.created(location).build();
	}

	private Post getLastPost(Collection<Post> posts) {
		Post last = null;
		for (Post p : posts) {
			last = p;
		}

		return last;
	}

	// Create a @GET getPosts method for a username -- returns a collection of
	// posts.
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getPostsForUser(@PathParam("username") String username) {
		
		Collection<Post> posts = userRepository.getUser(username).getPosts();
		
		return Response
				.ok(new PostCollectionRepresentation(posts))
				.link(UriBuilder.fromPath("/users/" + username).build(), "related")
				.link(UriBuilder.fromPath("/posts/"+username).build(), "self")
				.link(UriBuilder.fromPath("/posts/" + username + "/" + posts.iterator().next().getTimestamp().toString()).build(), "first")
				.link(UriBuilder.fromPath("/posts/" + username + "/" + getLastPost(posts).getTimestamp().toString()).build(), "last")
				.build();
	}

	// Create a @GET getPosts method for a username -- returns a collection of
	// posts.
	@GET
	@Path("/{timestamp}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPost(@PathParam("username") String username,
			@PathParam("timestamp") String timestamp) {

		final Post post = userRepository.getUser(username).getPost(
				new Timestamp(timestamp));
		return Response
				.ok(new PostRepresentation(post, false))
				.link(UriBuilder.fromPath("/users/" + username).build(),
						"related")
				.link(UriBuilder.fromPath("/posts/" + username).build(), "up")
				.link(UriBuilder.fromPath(
						"/posts/" + username + "/" + timestamp).build(), "self")
				.build();
	}

}
