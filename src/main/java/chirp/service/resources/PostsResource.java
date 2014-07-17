package chirp.service.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.Post;
import chirp.model.Timestamp;
import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.PostRepresentation;

@Path("/posts/{username}")
public class PostsResource {

	private final UserRepository repo = UserRepository.getInstance();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createPost(@PathParam("username") String username,
			@FormParam("content") String content) {

		logger.info("Creating a post for username={} and content={}", username,
				content);

		User user = repo.getUser(username);
		Post post = user.createPost(content);

		return Response.created(
				UriBuilder.fromResource(this.getClass()).path(username)
						.path(post.getTimestamp().toString()).build()).build();
	}

	@GET
	@Path("{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public PostRepresentation getPost(@PathParam("username") String username,
			@PathParam("timestamp") String timestamp, @Context UriInfo uriInfo) {
		logger.info("Retrieving post for username={} and timestamp={}",
				username, timestamp);
		User user = repo.getUser(username);
		Post post = user.getPost(new Timestamp(timestamp));
		return new PostRepresentation(post, uriInfo.getAbsolutePathBuilder()
				.build());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PostRepresentation> getAllPostsForUser(
			@PathParam("username") String username, @Context UriInfo uriInfo) {
		Collection<Post> posts = repo.getUser(username).getPosts();
		List<PostRepresentation> postReps = new ArrayList<>(posts.size());
		for (Post post : posts) {
			postReps.add(new PostRepresentation(post, uriInfo
					.getAbsolutePathBuilder()
					.path(post.getTimestamp().toString()).build()));
		}
		return Collections.unmodifiableCollection(postReps);
	}
}
