package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

@XmlRootElement
public class PostCollectionRepresentation {

	private Collection<PostRepresentation> posts = new ArrayList<>();
	private URI self;

	public PostCollectionRepresentation(Collection<Post> posts, String username, UriInfo uriInfo) {

		for (Post post : posts) {
			this.posts.add(new PostRepresentation(post, true, uriInfo.getAbsolutePathBuilder().path(post.getTimestamp().toString()).build()));
		}

		self = uriInfo.getAbsolutePathBuilder().build();
	}
	
	public PostCollectionRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("posts") Collection<PostRepresentation> posts) {
		this.self = self;
		this.posts = posts;
	}
	
	public PostCollectionRepresentation() {}

	@XmlElement
	public Collection<PostRepresentation> getPosts() {
		return Collections.unmodifiableCollection(posts);
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

}
