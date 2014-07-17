package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

@XmlRootElement
public class UsersCollectionRepresentation {

	private Collection<UserRepresentation> userReps = new ArrayList<>();
	private URI self;

	public UsersCollectionRepresentation() {}
	
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

	@XmlElement
	public Collection<UserRepresentation> getUsers() {
		return userReps;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setUserReps(Collection<UserRepresentation> userReps) {
		this.userReps = userReps;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

}
