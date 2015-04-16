package chirp.service.representations;

import java.net.URI;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpRepresentation {

	private String content;
	private String chirpId;
	private URI self;

	public ChirpRepresentation() {
	}

	public ChirpRepresentation(Chirp chirp, boolean summary, UriInfo uriInfo) {
		super();
		if (summary == false) {
			this.content = chirp.getContent();
			this.chirpId = chirp.getId().toString();
		}

		self = uriInfo.getAbsolutePathBuilder().path(chirp.getId().toString())
				.build(chirp.getUser().getUsername());
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

	@XmlElement
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@XmlElement
	public String getChirpId() {
		return chirpId;
	}

	public void setChirpId(String chirpId) {
		this.chirpId = chirpId;
	}

}
