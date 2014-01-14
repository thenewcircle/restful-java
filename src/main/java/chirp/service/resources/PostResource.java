package chirp.service.resources;

import javax.ws.rs.Path;


@Path("/posts/{username}")  // chose this over /user/{usernamae}/post to decouple users and posts
public class PostResource {

	// Create a @POST createPost method that also creates the timestamp for the post
	
	// Create a @GET getPosts method for a username -- returns a collection of posts.
	
	// Create a test case for both. 

}
