package chirp.service.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement(name="chirps")
public class ChirpCollectionRepresentation {
	
	private List<ChirpRepresentation> chirpList = new ArrayList<>();
	
	public ChirpCollectionRepresentation() {}
	
	public ChirpCollectionRepresentation(Collection<Chirp> chirps) {
		for (Chirp chirp : chirps) {
			ChirpRepresentation rep = new ChirpRepresentation(chirp);
			chirpList.add(rep);
		}
	}

	@XmlElement(name="chirp")
	public List<ChirpRepresentation> getChirpList() {
		return chirpList;
	}

	public void setChirpList(List<ChirpRepresentation> chirpList) {
		this.chirpList = chirpList;
	}

	
	
}
