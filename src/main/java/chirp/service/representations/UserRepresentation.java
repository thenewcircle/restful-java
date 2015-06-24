package chirp.service.representations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement
public class UserRepresentation {
	private String realname;
	private String username;
	
	public UserRepresentation() {}
	
	public UserRepresentation(User user) {
		this.realname = user.getRealname();
		this.username = user.getUsername();
	}
	
	@XmlElement
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	@XmlElement
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
