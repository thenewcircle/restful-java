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
import chirp.model.User;

@XmlRootElement
public class PostCollectionRepresentation {
	
	@XmlElement
	private URI self;
	
	@XmlElement
	private Collection<PostRepresentation> posts;

	
	public PostCollectionRepresentation() {
		self = null;
		posts= null;
	}
	
	public PostCollectionRepresentation(Collection<Post> postCollection) {
		
		this.posts = new ArrayList<>();
		for (Post p : postCollection)
			this.posts.add(new PostRepresentation(p,true));

		User user = postCollection.iterator().next().getUser();
		
		self = UriBuilder.fromPath("/posts").path(user.getUsername()).build();
	}
	
	
	@JsonCreator
	public PostCollectionRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("posts") Collection<PostRepresentation> posts) {
		super();
		this.self = self;
		this.posts = posts;
	}

	public URI getSelf() {
		return self;
	}

	public Collection<PostRepresentation> getPosts() {
		return posts;
	}


}
