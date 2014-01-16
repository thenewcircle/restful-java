package chirp.service.representations;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;
import chirp.model.Timestamp;

@XmlRootElement
public class PostRepresentation extends AbstractRepresentationImpl {

	private Timestamp timestamp;
	private String content;

	public PostRepresentation() {
		super(null);
		timestamp = null;
		content = null;
	}

	public PostRepresentation(Post post, boolean summary) {
		// http://localhost:8080/posts/<username>/<timestamp>
		super( UriBuilder.fromPath("/posts")
				.path(post.getUser().getUsername())
				.path(post.getTimestamp().toString()).build());
		timestamp = summary ? null : post.getTimestamp();
		content = summary ? null : post.getContent();

	}

	@JsonCreator
	public PostRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("timestamp") Timestamp timestamp,
			@JsonProperty("content") String content) {
		super(self);
		this.timestamp = timestamp;
		this.content = content;
	}

	@XmlElement
	public Timestamp getTimestamp() {
		return timestamp;
	}

	@XmlElement
	public String getContent() {
		return content;
	}

}
