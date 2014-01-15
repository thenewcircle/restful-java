package chirp.service.resources;

import java.net.URI;
import java.util.ArrayList;
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
import chirp.service.representations.PostRepresentation;


@Path("/posts/{username}")  // chose this over /user/{usernamae}/post to decouple users and posts
public class PostResource {
	
	private final UserRepository userRepository = UserRepository.getInstance();

	// Create a @POST createPost method that also creates the timestamp for the post
	@POST
	public Response createPost(@PathParam("username") String username, @FormParam("content") String content) {
		
		final User user = userRepository.getUser(username);
		final Post post = user.createPost(content);
		
		final URI location = UriBuilder.fromPath("/posts/"+username+"/"+post.getTimestamp()).build();
		return Response.created(location).build();
	}
	
	// Create a @GET getPosts method for a username -- returns a collection of posts.
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<PostRepresentation> getPostsForUser(@PathParam("username") String username) {
		
		Collection<PostRepresentation> postReps = new ArrayList<>();
		for (Post p : userRepository.getUser(username).getPosts())
			postReps.add(new PostRepresentation(p));
		
		return postReps;
	}
	
	
	// Create a @GET getPosts method for a username -- returns a collection of posts.
	@GET
	@Path("/{timestamp}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public PostRepresentation getPost(@PathParam("username") String username, @PathParam("timestamp") String timestamp ) {
		return new PostRepresentation (userRepository.getUser(username).getPost(new Timestamp(timestamp)));
	}

	
}
