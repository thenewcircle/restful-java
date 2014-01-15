package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

@XmlRootElement
public class UserCollectionRepresentation {

	@XmlElement
	private URI self;
	@XmlElement
	private Collection<UserRepresentation> users;

	public UserCollectionRepresentation() {
		self = null;
		users = null;
	}

	public UserCollectionRepresentation(Collection<User> userCollection) {
		this.users = new ArrayList<UserRepresentation>();
		for (User u : userCollection)
			this.users.add(new UserRepresentation(u, true));
		// this.self = UriBuilder.fromResource(UserResource.class).build();
		this.self = UriBuilder.fromPath("/users").build();
	}

	@JsonCreator
	public UserCollectionRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("users") Collection<UserRepresentation> users) {
		this.self = self;
		this.users = users;
	}

	public URI getSelf() {
		return self;
	}

	public Collection<UserRepresentation> getUsers() {
		return users;
	}

}
