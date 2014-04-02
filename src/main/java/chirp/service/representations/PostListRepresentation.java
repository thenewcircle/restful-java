package chirp.service.representations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;

import chirp.model.Post;
import chirp.service.resources.PostResource;

/*
 * <posts>
 *   <post>....</post>
 *   <post>....</post>
 * </posts>
 *
 */
@XmlRootElement(name="posts")
@XmlType(propOrder={"links", "posts"})
public class PostListRepresentation {

//	@InjectLink(rel = "self", style = Style.ABSOLUTE,
//			resource = PostResource.class, method = "getPosts",
//			bindings = { @Binding(name = "username", value = "{username}") })
	@InjectLink(rel = "self", style = Style.ABSOLUTE, value="/posts/{username}")
	private Link self;
	
	private MyLink next;

	private String username;
	
	private List<PostRepresentation> posts = new ArrayList<PostRepresentation>();

	public PostListRepresentation() {}
	
	public PostListRepresentation(Collection<Post> postList) {
		username = postList.iterator().next().getUser().getUsername();
//		self = makeLink("self", "http://localhost:8080/posts/%s", username);
		next = MyLink.link("next", "http://localhost:8080/posts/%s?offset=%d&count=%d", username, 10, 10);
		for (Post p : postList) {
			posts.add(new PostRepresentation(p));
		}
	}
	
	@XmlElement(name="link")
	@JsonProperty("_links")
	public List<MyLink> getLinks() {
		return MyLink.list(MyLink.link(self), next);
	}

	public void setLinks(List<MyLink> links) {
	}

	@XmlElement(name="post")
	public List<PostRepresentation> getPosts() {
		return posts;
	}

	public void setPosts(List<PostRepresentation> posts) {
		this.posts = posts;
	}
	
	@XmlTransient
	@JsonIgnore
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
