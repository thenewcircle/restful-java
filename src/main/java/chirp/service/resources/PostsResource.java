package chirp.service.resources;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.Post;
import chirp.model.Timestamp;
import chirp.model.User;
import chirp.model.UserRepository;

@Path("/posts/{username}")
public class PostsResource {

	private final UserRepository repo = UserRepository.getInstance();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createPost(@PathParam("username") String username,
			@FormParam("content") String content) {

			logger.info("Creating a post for username={} and content={}",
					username, content);

			User user = repo.getUser(username);
			Post post = user.createPost(content);

			return Response.created(
					UriBuilder.fromPath("/posts").path(username).path(post.getTimestamp().toString()).build())
					.build();
	}
	
	@GET
	@Path("{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public Post getPost(@PathParam("username") String username, @PathParam("timestamp") String timestamp) {
		User user = repo.getUser(username);
		Post post = user.getPost(new Timestamp(timestamp)); 
		logger.info("Retrieving post for username={} and timestamp={}",username, timestamp);
		return post; 
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Post> getAllUsers(@PathParam("username") String username) {
		return repo.getUser(username).getPosts();
	}

}
