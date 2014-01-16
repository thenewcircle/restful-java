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
public class UserCollectionRepresentation extends AbstractRepresentationImpl {

	private Collection<UserRepresentation> users;

	public UserCollectionRepresentation() {
		super(null);
	}

	public UserCollectionRepresentation(Collection<User> userCollection) {
		super(UriBuilder.fromPath("/users").build());
		this.users = new ArrayList<UserRepresentation>();
		for (User u : userCollection)
			this.users.add(new UserRepresentation(u, true));
	}

	@JsonCreator
	public UserCollectionRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("users") Collection<UserRepresentation> users) {
		super(self);
		this.users = users;
	}

	@XmlElement
	public Collection<UserRepresentation> getUsers() {
		return users;
	}

}
