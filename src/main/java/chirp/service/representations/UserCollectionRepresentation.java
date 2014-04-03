package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.UriBuilder;

import chirp.model.User;

public class UserCollectionRepresentation {
	
	private Collection<UserRepresentation> userReps = new ArrayList<>();
	private URI self;
	
	public UserCollectionRepresentation(Collection<User> users) {
		
		for (User user : users) {
			userReps.add(new UserRepresentation(user, true));
		}
		
		self = UriBuilder.fromPath("/user").build();
	}
	
	public Collection<UserRepresentation> getAll() {
		return Collections.unmodifiableCollection(userReps);
	}
	
	public URI getSelf() { return self; }

}
