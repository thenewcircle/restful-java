package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

@XmlRootElement
public class UserCollectionRepresentation {

	private Collection<UserRepresentation> users = new ArrayList<>();
	private URI self;

	public UserCollectionRepresentation() {
	}

	public UserCollectionRepresentation(Collection<User> users) {

		for (User user : users) {
			this.users.add(new UserRepresentation(user, true));
		}

		self = UriBuilder.fromPath("/users").build();
	}

	public UserCollectionRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("users") Collection<UserRepresentation> users) {
		this.self = self;
		this.users = users;
	}

	@XmlElement
	public Collection<UserRepresentation> getUsers() {
		return Collections.unmodifiableCollection(users);
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

}
