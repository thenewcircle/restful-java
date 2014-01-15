package chirp.service.resources;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

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
import chirp.model.User;
import chirp.model.UserRepository;


@Path("/posts/{username}")  // chose this over /user/{usernamae}/post to decouple users and posts
public class PostResource {
	
	private final UserRepository userRepository = UserRepository.getInstance();

	// Create a @POST createPost method that also creates the timestamp for the post
	@POST
	public Response createPost(@PathParam("username") String username, @FormParam("content") String content) {
		
		final User user = userRepository.getUser(username);
		user.createPost(content);
		
		final URI location = UriBuilder.fromResource(this.getClass()).build();
		return Response.created(location).build();
	}
	
	// Create a @GET getPosts method for a username -- returns a collection of posts.
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Post> getPostsForUser(@PathParam("username") String username) {
		return Collections.unmodifiableCollection(userRepository.getUser(username).getPosts());
	}
	
}
