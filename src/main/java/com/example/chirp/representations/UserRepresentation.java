package com.example.chirp.representations;

import static com.example.util.rest.LinkRel.RELATED;
import static com.example.util.rest.LinkRel.SELF;
import static org.glassfish.jersey.linking.InjectLink.Style.ABSOLUTE;

import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.glassfish.jersey.linking.InjectLink;

import com.example.chirp.model.User;
import com.example.util.rest.Representation;

@XmlRootElement(name="user")
public class UserRepresentation extends Representation {

	@InjectLink(rel=SELF, value="/users/{username}", style=ABSOLUTE)
	private Link selfLink;
	
	@InjectLink(rel=RELATED, value="/posts/{username}", title="posts", style=ABSOLUTE)
	private Link relatedLink;
	
	private String username;

	private String realname;
	
	private PostListRepresentation posts;

	public UserRepresentation() {
	}
	
	public UserRepresentation(User user, boolean summary) {
		username = user.getUsername();
		if (!summary) {
			realname = user.getRealname();
			posts = new PostListRepresentation(user.getPosts());
		}
	}
	
	@Override
	protected Collection<Link> includeLinks() {
		return Arrays.asList(selfLink, relatedLink);
	}

	@XmlAttribute
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public PostListRepresentation getPosts() {
		return posts;
	}

	public void setPosts(PostListRepresentation posts) {
		this.posts = posts;
	}

	public void applyChanges(User dbUser) {
		if (realname == null) {
			//Don't change existing value.
		} else {
			if (realname.isEmpty()) {
				dbUser.setRealname(null);
			} else {
				dbUser.setRealname(realname);
			}
		}
	}

}
