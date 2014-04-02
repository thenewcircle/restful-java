package chirp.service.representations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Post;

/*
 * <posts>
 *   <post>....</post>
 *   <post>....</post>
 * </posts>
 *
 */
@XmlRootElement(name="posts")
public class PostListRepresentation {

	private List<PostRepresentation> posts = new ArrayList<PostRepresentation>();

	public PostListRepresentation() {}
	
	public PostListRepresentation(Collection<Post> postList) {
		for (Post p : postList) {
			posts.add(new PostRepresentation(p));
		}
	}
	
	@XmlElement(name="post")
	public List<PostRepresentation> getPosts() {
		return posts;
	}

	public void setPosts(List<PostRepresentation> posts) {
		this.posts = posts;
	}
	
}
