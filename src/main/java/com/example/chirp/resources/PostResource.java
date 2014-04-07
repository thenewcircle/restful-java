package com.example.chirp.resources;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.example.chirp.model.Post;
import com.example.chirp.representations.PostListRepresentation;
import com.example.chirp.representations.PostRepresentation;
import com.example.chirp.services.ChirpRepository;
import com.example.chirp.services.ConfigurationService;

@Path("posts")
public class PostResource {

	/** GET /posts/wars01 */
	@GET
	@Path("{guid}")
	public PostRepresentation getPost(@PathParam("username") String username, @PathParam("guid") String guid) {
		ChirpRepository repo = ConfigurationService.getChirpRepository();
		Post post = repo.getPost(guid);
		PostRepresentation body = new PostRepresentation(post);
		return body;
	}

	/** GET /posts?user=yoda */
	@GET
	public PostListRepresentation getPost(@QueryParam("username") String username) {
		if (username == null) {
			ChirpRepository repo = ConfigurationService.getChirpRepository();
			Collection<Post> posts = repo.getAllPosts();
			PostListRepresentation body = new PostListRepresentation(posts);
			return body;
		} else {
			ChirpRepository repo = ConfigurationService.getChirpRepository();
			Collection<Post> posts = repo.getPostsForUser(username);
			PostListRepresentation body = new PostListRepresentation(posts);
			return body;
		}
	}
}
