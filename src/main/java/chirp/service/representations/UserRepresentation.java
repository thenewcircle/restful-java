package chirp.service.representations;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

public class UserRepresentation {

	private final String username;
	private final String realname;

	public UserRepresentation(User user) {
		username = user.getUsername();
		realname = user.getRealname();
	}

	@JsonCreator
	public UserRepresentation(@JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		super();
		this.username = username;
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

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

}
