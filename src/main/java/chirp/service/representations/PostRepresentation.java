package chirp.service.representations;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;
import chirp.model.Timestamp;
import chirp.service.resources.UserResource;

@XmlRootElement
public class PostRepresentation {

	@XmlElement
	private Timestamp timestamp;
	@XmlElement
	private String content;
	@XmlElement
	private URI self;

	public PostRepresentation() {
		timestamp = null;
		content = null;
	}

	public PostRepresentation(Post post, boolean summary) {
		timestamp = summary ? null : post.getTimestamp();
		content = summary ? null : post.getContent();

		// http://localhost:8080/posts/<username>/<timestamp>
		this.self = UriBuilder.fromPath("/posts")
				.path(post.getUser().getUsername())
				.path(post.getTimestamp().toString()).build();

	}

	@JsonCreator
	public PostRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("timestamp") Timestamp timestamp,
			@JsonProperty("content") String content) {
		super();
		this.timestamp = timestamp;
		this.content = content;
		this.self = self;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getContent() {
		return content;
	}

	public URI getSelf() {
		return self;
	}

}
