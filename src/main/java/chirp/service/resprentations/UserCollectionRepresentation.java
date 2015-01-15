package chirp.service.resprentations;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement
public class UserCollectionRepresentation {

	private Collection<UserRepresentation> users = new LinkedList<>();

	public UserCollectionRepresentation() {

	}

	public UserCollectionRepresentation(Collection<User> users) {
		for (User user : users) {
			this.users.add(new UserRepresentation(user));
		}

	}

	@XmlElement
	public Collection<UserRepresentation> getUsers() {
		return Collections.unmodifiableCollection(users);
	}

}
