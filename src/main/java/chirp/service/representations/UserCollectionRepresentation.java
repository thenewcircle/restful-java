package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.UriInfo;

import chirp.model.User;

public class UserCollectionRepresentation {

	private Collection<UserRepresentation> users = new ArrayList<>();
	private URI self;

	public UserCollectionRepresentation() {
	}

	public UserCollectionRepresentation(Collection<User> users, UriInfo uriInfo) {
		for (User user : users)
			this.users.add(new UserRepresentation(user, uriInfo
					.getAbsolutePathBuilder().path(user.getUsername()).build(),
					true));

		this.self = uriInfo.getAbsolutePath();
	}

	public Collection<UserRepresentation> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserRepresentation> users) {
		this.users = users;
	}

	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

}
