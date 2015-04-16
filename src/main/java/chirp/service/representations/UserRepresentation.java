package chirp.service.representations;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement
public class UserRepresentation {

	private String username;
	private String realname;
	private URI self;

	public UserRepresentation() {
	}

	public UserRepresentation(User user, boolean summary, URI self) {
		if (summary == false) {
			this.username = user.getUsername();
			this.realname = user.getRealname();
		}
		this.self = self;

	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

}
