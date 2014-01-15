package chirp.service.representations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;
import chirp.model.Timestamp;

@XmlRootElement
public class PostRepresentation {

	@XmlElement
	private Timestamp timestamp;
	@XmlElement
	private String content;

	public PostRepresentation() {
		timestamp = null;
		content = null;
	}

	public PostRepresentation(Post post) {
		timestamp = post.getTimestamp();
		content = post.getContent();
	}

	@JsonCreator
	public PostRepresentation(@JsonProperty("timestamp") Timestamp timestamp,
			@JsonProperty("content") String content) {
		super();
		this.timestamp = timestamp;
		this.content = content;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getContent() {
		return content;
	}

}
