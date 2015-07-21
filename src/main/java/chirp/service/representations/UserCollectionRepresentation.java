package chirp.service.representations;

import java.net.URI;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;
import chirp.model.UserRepository;

@XmlRootElement
public class UserCollectionRepresentation extends
		AbstractCacheableRepresentation {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((self == null) ? 0 : self.hashCode());
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
		UserCollectionRepresentation other = (UserCollectionRepresentation) obj;
		if (self == null) {
			if (other.self != null)
				return false;
		} else if (!self.equals(other.self))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("UserCollectionRepresentation [self=%s, users=%s]",
						self, users);
	}

}
