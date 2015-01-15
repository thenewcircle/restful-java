package chirp.service.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import chirp.model.Chirp;

@XmlRootElement(name="chirp")
public class ChirpRepresentation {

	private String username;
	private String message;
	private String id;
	
//	@InjectLinks({
//	@InjectLink(rel="self", resource=ChirpResource.class, method="getChirp", style=Style.ABSOLUTE),
//	@InjectLink(rel="self", value="/chirps", style=Style.ABSOLUTE)})
	private List<Link> links;
//
//	@InjectLink(rel="next", value="/chirps/{username}/{id}", style=Style.ABSOLUTE)
	private Link myLink;

	@InjectLink(value="/chirps/{username}/{id}", style=Style.ABSOLUTE)
	private URI self;
	
	public ChirpRepresentation() {
		super();
	}

	public ChirpRepresentation(Chirp chirp) {
		this.username = chirp.getUser().getUsername();
		this.message = chirp.getContent();
		this.id = chirp.getId().toString();
//		this.self = UriBuilder.fromPath("http://localhost:8080/chirps/{username}/{id}").build(username, id);
//		this.self = UriBuilder.fromMethod(ChirpResource.class, "getChirp").build(username, id);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name="link")
	@XmlElementWrapper(name="links")
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> link) {
		this.links = link;
	}

	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

	public Link getMyLink() {
		return myLink;
	}

	public void setMyLink(Link myLink) {
		this.myLink = myLink;
	}
	
}
