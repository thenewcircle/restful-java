package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

public class UsersCollectionRepresentation {

	private Collection<UserRepresentation> userReps = new ArrayList<>();
	private URI self;

	@JsonCreator
	public UsersCollectionRepresentation(
			@JsonProperty("users") Collection<UserRepresentation> users,
			@JsonProperty("self") URI self) {
		super();
		this.userReps = users;
		this.self = self;
	}

	public UsersCollectionRepresentation(Collection<User> users, UriInfo uriInfo) {
		List<UserRepresentation> ur = new ArrayList<>(users.size());
		for (User user : users) {
			ur.add(new UserRepresentation(user, uriInfo
					.getAbsolutePathBuilder().path(user.getUsername()).build(),
					true));
		}
		userReps = Collections.unmodifiableCollection(ur);
		self = uriInfo.getAbsolutePathBuilder().build();
	}

	public Collection<UserRepresentation> getUsers() {
		return userReps;
	}

	public URI getSelf() {
		return self;
	}

}
