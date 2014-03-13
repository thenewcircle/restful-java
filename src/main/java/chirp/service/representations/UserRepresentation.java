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
	private URI self;
	@XmlElement
	private String username;
	@XmlElement
	private String realname;

	public UserRepresentation(User user) {
		this.username = user.getUsername();
		this.realname = user.getRealname();
		 self = UriBuilder.fromResource(UserResource.class).path(username).build();
	}
	
	public UserRepresentation() {
		this.username = null;
		this.realname = null;
	}

	@JsonCreator()
	public UserRepresentation(@JsonProperty("self") URI self, @JsonProperty("username") String username,
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
