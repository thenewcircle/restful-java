package chirp.service.representations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;

import chirp.model.Post;

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

	@InjectLink(rel = "self", style = Style.ABSOLUTE, value="/posts/{username}")
	private Link self;
	
	@InjectLink(rel = "next", style = Style.ABSOLUTE, value="/posts/{username}?offset=10&limit=10")
	private Link next;

	private String username;
	
	private List<PostRepresentation> posts = new ArrayList<PostRepresentation>();

	public PostListRepresentation() {}
	
	public PostListRepresentation(Collection<Post> postList) {
		username = postList.iterator().next().getUser().getUsername();
		for (Post p : postList) {
			posts.add(new PostRepresentation(p));
		}
	}
	
	@XmlElement(name="link")
	@JsonProperty("_links")
	@JsonSerialize(using=JsonHalLinkSerializer.class)
	@XmlJavaTypeAdapter(Link.JaxbAdapter.class)
	public List<Link> getLinks() {
		return Arrays.asList(self, next);
	}

	public void setLinks(List<Link> links) {
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

	
	
}
