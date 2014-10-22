package chirp.service.representations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement
public class UserRepresentation {
	
	private String username;
	private String realname;

	// required by jaxb marshalling 
	public UserRepresentation() {}
	
	// use from our UserResource
	public UserRepresentation(User user) {
		this.username = user.getUsername();
		this.realname = user.getRealname();
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

	}
