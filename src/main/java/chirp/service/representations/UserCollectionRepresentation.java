package chirp.service.representations;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement
public class UserCollectionRepresentation extends
		AbstractCacheableRepresentation {

	private Collection<UserRepresentation> users = new LinkedList<>();
	private URI self;

	public UserCollectionRepresentation() {
		super.setLastModificationTime(new Date());
	}

	public UserCollectionRepresentation(Collection<User> users, UriInfo uriInfo) {
		this();
		for (User user : users) {
			this.users.add(new UserRepresentation(user, uriInfo
					.getAbsolutePathBuilder().path(user.getUsername()).build(),
					true));
		}

		self = uriInfo.getAbsolutePathBuilder().build();

	}

	@XmlElement
	public Collection<UserRepresentation> getUsers() {
		// return Collections.unmodifiableCollection(users);
		return users;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

}
