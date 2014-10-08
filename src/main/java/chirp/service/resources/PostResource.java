package chirp.service.resources;

import java.util.ArrayList;
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
import chirp.model.Timestamp;
import chirp.model.UserRepository;
import chirp.service.representations.PostRepresentation;

@Path("/posts/{username}")
public class PostResource {

	private UserRepository userRepository = UserRepository.getInstance();

	@POST
	public Response createChirp(@PathParam("username") String username,
			@FormParam("content") String content) {

		Post post = userRepository.getUser(username).createPost(content);

		return Response.created(
				UriBuilder.fromResource(this.getClass()).
						path(post.getTimestamp().toString()).build()).build();

	}

	@GET
	@Path("{timestamp}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public PostRepresentation getChirp(@PathParam("username") String username,
			@PathParam("timestamp") String timestamp) {

		return new PostRepresentation(userRepository.getUser(username).getPost(
				new Timestamp(timestamp)));

	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<PostRepresentation> getAllChirps(@PathParam("username") String username) {
		
		ArrayList<PostRepresentation> chirps = new ArrayList<>();
		
		for (Post post : userRepository.getUser(username).getPosts()) {
			chirps.add(new PostRepresentation(post));
		}
		
		return Collections.unmodifiableCollection(chirps);
	}

}
