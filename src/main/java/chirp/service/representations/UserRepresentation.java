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
	
	public UserRepresentation(User user, boolean summary) {
		this.username = summary ? null : user.getUsername();
		this.realname = summary ? null : user.getRealname();
		
		// http://localhost:8080/users/<username>
		this.self = UriBuilder.fromResource(UserResource.class).path(user.getUsername()).build();
		
	}

	public UserRepresentation() {
		this.username = null;
		this.realname = null;
		this.self = null;
	}

	@JsonCreator()
	public UserRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		this.username = username;
		this.realname = realname;
		this.self = self;
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

}
