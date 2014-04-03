package chirp.service.representations;

import static org.glassfish.jersey.linking.InjectLink.Style.ABSOLUTE;

import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;

import static chirp.service.representations.LinkRel.*;

import org.glassfish.jersey.linking.InjectLink;

import chirp.model.Post;

@XmlRootElement(name="post")
public class PostRepresentation extends Representation {

	@InjectLink(rel=SELF, value="/posts/{user.username}/{timestamp}", style=ABSOLUTE)
	private Link selfLink;

	@InjectLink(rel=COLLECTION, value="/posts/{user.username}", style=ABSOLUTE)
	private Link upLink;
	
	private String timestamp;
	private UserRepresentation user;
	private String message;

	public PostRepresentation() {
	}
	
	public PostRepresentation(Post post) {
		timestamp = post.getTimestamp().toString();
		user = new UserRepresentation(post.getUser(), true);
		message = post.getContent();
	}
	
	@Override
	protected Collection<Link> includeLinks() {
		return Arrays.asList(selfLink, upLink);
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
