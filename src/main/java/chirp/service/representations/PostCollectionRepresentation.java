package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

@XmlRootElement
public class PostCollectionRepresentation extends
		AbstractEntityRepresentationImpl {

	private Collection<PostRepresentation> posts;

	public PostCollectionRepresentation() {
		super(null);
		posts = null;
	}

	public PostCollectionRepresentation(Collection<Post> postCollection) {
		super(UriBuilder.fromPath("/posts")
				.path(postCollection.iterator().next().getUser().getUsername())
				.build());
		this.posts = new ArrayList<>();
		for (Post p : postCollection)
			this.posts.add(new PostRepresentation(p, true));
	}

	@JsonCreator
	public PostCollectionRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("posts") Collection<PostRepresentation> posts) {
		super(self);
		this.posts = posts;
	}

	@XmlElement
	public Collection<PostRepresentation> getPosts() {
		return posts;
	}

}
