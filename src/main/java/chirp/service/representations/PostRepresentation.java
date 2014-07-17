package chirp.service.representations;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

public class PostRepresentation {

	private final String content;
	private final String timestamp;
	private final URI self;

	public PostRepresentation(Post post, URI self) {
		this.content = post.getContent();
		this.timestamp = post.getTimestamp().toString();
		this.self = self;
	}

	@JsonCreator
	public PostRepresentation(@JsonProperty("content") String content,
			@JsonProperty("timestamp") String timestamp, @JsonProperty("self") URI self) {
		this.content = content;
		this.timestamp = timestamp;
		this.self = self;
	}

	public String getContent() {
		return content;
	}

	public String getTimestamp() {
		return timestamp;
	}

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

}
