package chirp.service.resources;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import chirp.model.Post;
import chirp.model.Timestamp;
import chirp.model.UserRepository;
import chirp.service.representations.PostListRepresentation;
import chirp.service.representations.PostRepresentation;

@Path("posts")
public class PostResource {

	/** GET /posts/yoda/10001111000001 */
	@GET
	@Path("{username}/{timestamp}")
	public PostRepresentation getPost(@PathParam("username") String username, @PathParam("timestamp") String timestamp) {
		UserRepository repo = UserRepository.getInstance();
		Timestamp ts = new Timestamp(timestamp);
		Post post = repo.getUser(username).getPost(ts);
		PostRepresentation body = new PostRepresentation(post);
		return body;
	}

	/** GET /posts/yoda */
	@GET
	@Path("{username}")
	public PostListRepresentation getPost(@PathParam("username") String username) {
		UserRepository repo = UserRepository.getInstance();
		Collection<Post> posts = repo.getUser(username).getPosts();
		PostListRepresentation body = new PostListRepresentation(posts);
		return body;
	}
}
