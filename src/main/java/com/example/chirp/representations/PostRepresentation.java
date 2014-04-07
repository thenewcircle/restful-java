package com.example.chirp.representations;

import static org.glassfish.jersey.linking.InjectLink.Style.ABSOLUTE;

import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;

import static com.example.util.rest.LinkRel.*;

import org.glassfish.jersey.linking.InjectLink;

import com.example.chirp.model.Post;
import com.example.util.rest.Representation;

@XmlRootElement(name="post")
public class PostRepresentation extends Representation {

	@InjectLink(rel=SELF, value="/posts/{guid}", style=ABSOLUTE)
	private Link selfLink;

	@InjectLink(rel=COLLECTION, value="/posts?username={user.username}", style=ABSOLUTE)
	private Link upLink;
	
	private String guid;
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

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
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
