package chirp.service.representations;

import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement(name="user")
public class UserRepresentation {

	private String username;

	private String realname;

	public UserRepresentation() {
	}
	
	public UserRepresentation(User user) {
		username = user.getUsername();
		realname = user.getRealname();
	}
	
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

}
