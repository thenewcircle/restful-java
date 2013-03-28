package chirp.service.representations;

import chirp.model.Post;

public class PostRepresentation {

	private final String timestamp;
	private final String content;

	public PostRepresentation(Post post, boolean summary) {
		timestamp = post.getTimestamp().toString();
		content = summary ? null : post.getContent();
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getContent() {
		return content;
	}

}
