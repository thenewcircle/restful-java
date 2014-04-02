package chirp.service.representations;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Post;

@XmlRootElement(name="post")
public class PostRepresentation {

	private String self = "none";
	private String timestamp;
	private UserRepresentation user;
	private String message;

	public PostRepresentation() {
	}
	
	public PostRepresentation(Post post) {
		timestamp = post.getTimestamp().toString();
		user = new UserRepresentation(post.getUser());
		message = post.getContent();
	}
	
	@XmlAttribute
	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public UserRepresentation getUser() {
		return user;
	}

	public void setUser(UserRepresentation user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
