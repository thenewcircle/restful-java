package chirp.service.representations;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpRepresentation {

	private String id;
	private String content;
	private URI self;

	public ChirpRepresentation() {
	}

	public ChirpRepresentation(Chirp chirp, boolean summary, URI self) {
		this.self = self;
		if (summary == false) {
			id = chirp.getId().toString();
			content = chirp.getContent();
		}
	}

	public ChirpRepresentation(URI self, String id, String content) {
		this.self = self;
		this.id = id;
		this.content = content;
	}

	@XmlElement
	public String getId() {
		return id;
	}

	@XmlElement
	public String getContent() {
		return content;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setId(String timestamp) {
		this.id = timestamp;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSelf(URI self) {
		this.self = self;
	}
	
}