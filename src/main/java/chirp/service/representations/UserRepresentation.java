package chirp.service.representations;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement(name="user")
public class UserRepresentation {

	private String self;
	
	private String username;

	private String realname;
	
	private PostListRepresentation posts;

	public UserRepresentation() {
	}
	
	public UserRepresentation(User user, boolean summary) {
		self=String.format("http://localhost:8080/users/%s", user.getUsername());
		username = user.getUsername();
		if (!summary) {
			realname = user.getRealname();
			posts = new PostListRepresentation(user.getPosts());
		}
	}
	
	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
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
