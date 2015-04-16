package chirp.service.representations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpRepresentation {

	private String content;
	private String chirpId;

	public ChirpRepresentation() {
	}

	public ChirpRepresentation(Chirp chirp) {
		super();
		this.content = chirp.getContent();
		this.chirpId = chirp.getId().toString();
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
