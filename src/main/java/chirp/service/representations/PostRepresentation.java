package chirp.service.representations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

@XmlRootElement
public class PostRepresentation {

	private String timestamp;
	private String content;
	
	public PostRepresentation() {
	}

	public PostRepresentation(Post post) {
		timestamp = post.getTimestamp().toString();
		content = post.getContent();
	}

	@JsonCreator
	public PostRepresentation(@JsonProperty("timestamp") String timestamp,
			@JsonProperty("content") String content) {
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

}
