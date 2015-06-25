package chirp.service.representations;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;
import chirp.model.UserRepository;

@XmlRootElement
public class UserCollectionRepresentation {

	private URI self;
	private Collection<UserRepresentation> users = new LinkedList<>();

	public UserCollectionRepresentation() {
	}

	public UserCollectionRepresentation(UriInfo uriInfo) {

		for (User user : UserRepository.getInstance().getUsers()) {
			users.add(new UserRepresentation(user, uriInfo
					.getAbsolutePathBuilder().path(user.getUsername()).build(),
					true));
		}

		self = uriInfo.getAbsolutePathBuilder().build();
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

	@XmlElement
	public Collection<UserRepresentation> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserRepresentation> users) {
		this.users = users;
	}

}
