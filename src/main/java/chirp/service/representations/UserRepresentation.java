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

	@Override
	public String toString() {
		return String.format("UserRepresentation [realname=%s, username=%s]",
				realname, username);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((realname == null) ? 0 : realname.hashCode());
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
		if (realname == null) {
			if (other.realname != null)
				return false;
		} else if (!realname.equals(other.realname))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
