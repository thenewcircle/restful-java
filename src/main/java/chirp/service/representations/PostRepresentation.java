package chirp.service.representations;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Post;

@XmlRootElement
public class PostRepresentation {

	private String timestamp;
	private String content;
	private URI self;

	public PostRepresentation() {
	}

	public PostRepresentation(Post post, boolean summary, URI self) {
		this.self = self;
		if (summary == false) {
			timestamp = post.getTimestamp().toString();
			content = post.getContent();
		}
	}

	public PostRepresentation(URI self, String timestamp, String content) {
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
	public URI getSelf() {
		return self;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSelf(URI self) {
		this.self = self;
	}
	
}
