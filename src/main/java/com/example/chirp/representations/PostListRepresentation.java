package com.example.chirp.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.example.chirp.model.Post;
import com.example.util.rest.CollectionRepresentation;

/*
 * <posts>
 *   <post>....</post>
 *   <post>....</post>
 * </posts>
 *
 */
@XmlRootElement(name="posts")
public class PostListRepresentation extends CollectionRepresentation {

	private List<PostRepresentation> posts = new ArrayList<PostRepresentation>();

	public PostListRepresentation() {}
	
	public PostListRepresentation(Collection<Post> postList) {
		for (Post p : postList) {
			posts.add(new PostRepresentation(p));
		}
		URI self = UriBuilder.fromPath("/posts").build();
		super.linkPagination(self, 0, 100);
	}
	
//	@Override
//	protected Collection<Link> includeLinks() {
//		return new ArrayList<Link>(Arrays.asList(self, next));
//	}

	@XmlElement(name="post")
	public List<PostRepresentation> getPosts() {
		return posts;
	}

	public void setPosts(List<PostRepresentation> posts) {
		this.posts = posts;
	}
	
}
