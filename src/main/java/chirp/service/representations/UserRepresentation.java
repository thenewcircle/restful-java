package chirp.service.representations;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;
import chirp.service.resources.UserResource;

@XmlRootElement
public class UserRepresentation {

	@XmlElement
	private String username;

	@XmlElement
	private String realname;

	@XmlElement
	private URI self;
	
	@XmlElement
	private URI posts;

	public UserRepresentation(User user) {
		this.username = user.getUsername();
		this.realname = user.getRealname();
		
		// http://localhost:8080/users/<username>
		this.self = UriBuilder.fromResource(UserResource.class).path(username).build();
		
		// http://localhost:8080/posts/<username>
		this.posts = UriBuilder.fromPath("/posts").path(username).build();
	}

	public UserRepresentation() {
		this.username = null;
		this.realname = null;
		this.posts = null;
		this.self = null;
	}

	@JsonCreator()
	public UserRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("posts") URI posts,
			@JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		this.username = username;
		this.realname = realname;
		this.self = self;
		this.posts = posts;
	}

	public String getUsername() {
		return username;
	}

	public String getRealname() {
		return realname;
	}

	public URI getSelf() {
		return self;
	}

	public URI getPosts() {
		return posts;
	}

}
