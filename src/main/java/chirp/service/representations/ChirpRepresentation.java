package chirp.service.representations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpRepresentation {

	private String content;
	private String id;

	public ChirpRepresentation() {
	}

	public ChirpRepresentation(Chirp chirp) {
		content = chirp.getContent();
		id = chirp.getId().toString();
	}

	@XmlElement
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChirpRepresentation other = (ChirpRepresentation) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("ChirpRepresentation [content=%s, id=%s]",
				content, id);
	}
	
	

}
