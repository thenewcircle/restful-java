package chirp.service.resources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement(name="chirp")
public class ChirpRepresentation {

	private String username;
	private String message;
	private String id;

	public ChirpRepresentation() {
		super();
	}

	public ChirpRepresentation(Chirp chirp) {
		this.username = chirp.getUser().getUsername();
		this.message = chirp.getContent();
		this.id = chirp.getId().toString();
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
	
}
