package chirp.service.representations;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

@XmlRootElement
public class PostRepresentation {

	private String content;
	private String timestamp;
	private URI self;
	
	public PostRepresentation() {}

	public PostRepresentation(Post post, URI self, boolean summary) {
		if (summary == false) {
			this.content = post.getContent();
			this.timestamp = post.getTimestamp().toString();
		}
		this.self = self;
	}

	@JsonCreator
	public PostRepresentation(@JsonProperty("content") String content,
			@JsonProperty("timestamp") String timestamp,
			@JsonProperty("self") URI self) {
		this.content = content;
		this.timestamp = timestamp;
		this.self = self;
	}

	@XmlElement
	public String getContent() {
		return content;
	}

	@XmlElement
	public String getTimestamp() {
		return timestamp;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostRepresentation other = (PostRepresentation) obj;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

}
