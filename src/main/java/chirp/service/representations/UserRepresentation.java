package chirp.service.representations;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;
import chirp.service.resources.UserResource;

@XmlRootElement
public class UserRepresentation extends AbstractEntityRepresentationImpl {

	private String username;
	private String realname;
	
	public UserRepresentation(User user, boolean summary) {
		super(UriBuilder.fromResource(UserResource.class).path(user.getUsername()).build());
		this.username = summary ? null : user.getUsername();
		this.realname = summary ? null : user.getRealname();
	}

	public UserRepresentation() {
		super(null);
		this.username = null;
		this.realname = null;
	}

	@JsonCreator()
	public UserRepresentation(@JsonProperty("self") URI self,
			@JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		super(self);
		this.username = username;
		this.realname = realname;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	@XmlElement
	public String getRealname() {
		return realname;
	}



}
