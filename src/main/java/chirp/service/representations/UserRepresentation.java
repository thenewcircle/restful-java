package chirp.service.representations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

@XmlRootElement
public class UserRepresentation {

	@XmlElement
	private String username;
	@XmlElement
	private String realname;

	public UserRepresentation(User user) {
		this.username = user.getUsername();
		this.realname = user.getRealname();
	}
	
	public UserRepresentation() {
		this.username = null;
		this.realname = null;
	}

	@JsonCreator()
	public UserRepresentation(@JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		this.username = username;
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public String getRealname() {
		return realname;
	}

}
