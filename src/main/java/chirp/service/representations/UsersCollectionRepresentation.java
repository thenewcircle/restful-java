package chirp.service.representations;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement
public class UsersCollectionRepresentation {

	private Deque<UserRepresentation> users = new ArrayDeque<>();
	private URI self;

	public UsersCollectionRepresentation(Collection<User> users, UriInfo uriInfo) {

		for (User user : users) {
			this.users
					.add(new UserRepresentation(user, true, uriInfo
							.getAbsolutePathBuilder().path(user.getUsername())
							.build()));
		}

		self = uriInfo.getAbsolutePathBuilder().build();
	}

	public UsersCollectionRepresentation() {
	}

	@XmlElement
	public Deque<UserRepresentation> getUsers() {
		return users;
	}

	public void setUsers(Deque<UserRepresentation> users) {
		this.users = users;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

}
