package chirp.service.representations;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

@XmlRootElement
public class UserRepresentation {

	private String username;
	private String realname;
	private URI self;

	public UserRepresentation() {

	}

	public UserRepresentation(User user, boolean summary) {
		self = UriBuilder.fromPath("/users").path(user.getUsername()).build();
		if (summary == false) {
			username = user.getUsername();
			realname = user.getRealname();
		}
	}

	@JsonCreator
	public UserRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		this.self = self;
		this.username = username;
		this.realname = realname;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	@XmlElement
	public String getRealname() {
		return realname;
	}

	// Added setters so test cases can work -- still looking for solution
	// without setters.
	public void setUsername(String username) {
		this.username = username;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
}
