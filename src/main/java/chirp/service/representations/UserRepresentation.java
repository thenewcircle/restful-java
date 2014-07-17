package chirp.service.representations;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

@XmlRootElement
public class UserRepresentation {

	private URI self;
	private String username;
	private String realname;
	
	public UserRepresentation() {}

	public UserRepresentation(User user, URI self, boolean summary) {
		if (summary) {
			this.username = null;
			this.realname = null;
		}
		else {
			this.username = user.getUsername();
			this.realname = user.getRealname();
		}
		this.self = self;
	}

	@JsonCreator
	public UserRepresentation(@JsonProperty("username") String username,
			@JsonProperty("realname") String realname,
			@JsonProperty("self") URI self) {
		super();
		this.username = username;
		this.realname = realname;
		this.self = self;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRepresentation other = (UserRepresentation) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

}
