package chirp.service.representations;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

@XmlRootElement
public class PostCollectionRepresentation extends
AbstractCollectionRepresentationImpl<Post,PostRepresentation> {

	public PostCollectionRepresentation() {
		super(null,null);
	}

	public PostCollectionRepresentation(Collection<Post> postCollection) {
		super(UriBuilder.fromPath("/posts")
				.path(postCollection.iterator().next().getUser().getUsername())
				.build());
		addEntities(postCollection);
	}
	
	protected PostRepresentation create(Post post) {
		return new PostRepresentation(post, true);
	}

	@JsonCreator
	public PostCollectionRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("posts") Collection<PostRepresentation> posts) {
		super(self,posts);
	}

}
