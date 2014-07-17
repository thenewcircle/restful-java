package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

@XmlRootElement
public class PostCollectionRepresentation {

	private Collection<PostRepresentation> postReps = new ArrayList<>();
	private URI self;
	
	public PostCollectionRepresentation() {}

	@JsonCreator
	public PostCollectionRepresentation(
			@JsonProperty("users") Collection<PostRepresentation> posts,
			@JsonProperty("self") URI self) {
		super();
		this.postReps = posts;
		this.self = self;
	}

	public PostCollectionRepresentation(Collection<Post> posts, UriInfo uriInfo) {
		List<PostRepresentation> pr = new ArrayList<>(posts.size());
		for (Post post : posts) {
			pr.add(new PostRepresentation(post, uriInfo
					.getAbsolutePathBuilder().path(post.getTimestamp().toString()).build(),
					true));
		}
		postReps = Collections.unmodifiableCollection(pr);
		self = uriInfo.getAbsolutePathBuilder().build();
	}

	@XmlElement
	public Collection<PostRepresentation> getUsers() {
		return postReps;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setPostReps(Collection<PostRepresentation> postReps) {
		this.postReps = postReps;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

}
