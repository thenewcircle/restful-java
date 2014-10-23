package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement
public class UserCollectionRepresentation {
	private Collection<UserRepresentation> userReps = new ArrayList<>();
	private URI self;
	
	public UserCollectionRepresentation() {
		
	}
	
	public UserCollectionRepresentation(Collection<User> users,
			UriInfo uriInfo) {
		super();
		this.self = uriInfo.getAbsolutePathBuilder().build();
		
		for (User user : users) {
			URI userURI = uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build(); 
			userReps.add(new UserRepresentation(true, userURI, user));
		}
		
	}
	@XmlElement
	public Collection<UserRepresentation> getUsers() {
		return userReps;
	}
	public void setUsers(Collection<UserRepresentation> users) {
		this.userReps = users;
	}
	
	@XmlElement
	public URI getSelf() {
		return self;
	}
	public void setSelf(URI self) {
		this.self = self;
	}
	
	

}
