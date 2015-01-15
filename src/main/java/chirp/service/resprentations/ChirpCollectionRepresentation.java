package chirp.service.resprentations;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpCollectionRepresentation {
	
	private Collection<ChirpRepresentation> chirps = new LinkedList<>();
	
	public ChirpCollectionRepresentation() {
		
	}
	
	public ChirpCollectionRepresentation(Collection<Chirp> chirps) {
		for (Chirp chirp : chirps) {
			this.chirps.add(new ChirpRepresentation(chirp));
		}
	
	}
	
	@XmlElement
	public Collection<ChirpRepresentation> getChirps() {
		// return Collections.unmodifiableCollection(chirps);
		return chirps; // TODO: fix unmodifable collection issue with @After
	}

}
