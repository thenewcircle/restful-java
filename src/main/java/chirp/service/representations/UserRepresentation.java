package chirp.service.representations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

@XmlRootElement
public class UserRepresentation {

	private String username;
	private String realname;
	
	public UserRepresentation() {
		
	}

	public UserRepresentation(User user) {
		username = user.getUsername();
		realname = user.getRealname();
	}

	@JsonCreator
	public UserRepresentation(@JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		this.username = username;
		this.realname = realname;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	@XmlElement
		public String getRealname() {
		return realname;
	}

}
