package chirp.service.representations;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

@XmlRootElement
public class UserCollectionRepresentation extends AbstractCollectionRepresentationImpl<User, UserRepresentation> {

	public UserCollectionRepresentation() {
		super(null,null);
	}

	public UserCollectionRepresentation(Collection<User> userCollection) {
		super(UriBuilder.fromPath("/users").build());
		addEntities(userCollection);
	}

	@JsonCreator
	public UserCollectionRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("users") Collection<UserRepresentation> users) {
		super(self, users);
	}
	
	protected UserRepresentation create(User user) {
		return new UserRepresentation(user, true); 
	}

}
