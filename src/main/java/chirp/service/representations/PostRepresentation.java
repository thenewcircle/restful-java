package chirp.service.representations;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

@XmlRootElement
public class PostRepresentation {

	private String timestamp;
	private String content;
	private URI self; 
	
	public PostRepresentation() {
	}

	public PostRepresentation(Post post, boolean summary) {
		self = UriBuilder.fromPath("/post").path(post.getUser().getUsername()).path(post.getTimestamp().toString()).build();
		if (summary == false) {
			timestamp = post.getTimestamp().toString();
			content = post.getContent();
		}
	}

	@JsonCreator
	public PostRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("timestamp") String timestamp,
			@JsonProperty("content") String content) {
		this.self = self;
		this.timestamp = timestamp;
		this.content = content;
	}

	@XmlElement
	public String getTimestamp() {
		return timestamp;
	}

	@XmlElement
	public String getContent() {
		return content;
	}
	
	@XmlElement
	public URI getSelf() { return self; }

}
