package chirp.service.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement(name="users")
public class UserCollectionRepresentation {
	
	private List<UserRepresentation> chirpList = new ArrayList<>();
	
	public UserCollectionRepresentation() {}
	
	public UserCollectionRepresentation(Collection<User> chirps) {
		for (User chirp : chirps) {
			UserRepresentation rep = new UserRepresentation(chirp, true);
			chirpList.add(rep);
		}
	}

	@XmlElement(name="user")
	public List<UserRepresentation> getUserList() {
		return chirpList;
	}

	public void setUserList(List<UserRepresentation> chirpList) {
		this.chirpList = chirpList;
	}

	
	
}
