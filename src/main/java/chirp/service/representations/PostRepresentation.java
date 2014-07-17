package chirp.service.representations;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

public class PostRepresentation {

	private final String content;
	private final String timestamp;

	public PostRepresentation(Post post) {
		content = post.getContent();
		timestamp = post.getTimestamp().toString();
	}

	@JsonCreator
	public PostRepresentation(@JsonProperty("content") String content,
			@JsonProperty("timestamp") String timestamp) {
		this.content = content;
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public String getTimestamp() {
		return timestamp;
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
