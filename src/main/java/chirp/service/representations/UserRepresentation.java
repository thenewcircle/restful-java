package chirp.service.representations;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;
import chirp.service.resources.UserResource;

@XmlRootElement
public class UserRepresentation {
	
	@XmlElement
	private Link self;
	@XmlElement
	private String username;
	@XmlElement
	private String realname;

	public UserRepresentation(User user) {
		this.username = user.getUsername();
		this.realname = user.getRealname();
		 self = Link.fromUriBuilder(UriBuilder.fromResource(UserResource.class).path(username)).build();
	}
	
	public UserRepresentation() {
		this.username = null;
		this.realname = null;
	}

	@JsonCreator()
	public UserRepresentation(@JsonProperty("self") Link self, @JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		this.username = username;
		this.realname = realname;
		this.self = self;

	}

	public String getUsername() {
		return username;
	}

	public String getRealname() {
		return realname;
	}

	public Link getSelf() {
		return self;
	}

}
