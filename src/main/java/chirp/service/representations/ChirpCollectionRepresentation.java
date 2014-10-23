package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpCollectionRepresentation {
	private Collection<ChirpRepresentation> chripReps = new ArrayList<>();
	private URI self;
	
	public ChirpCollectionRepresentation() {
		
	}
	
	public ChirpCollectionRepresentation(Deque<Chirp> chirps,
			UriInfo uriInfo) {

		this.self = uriInfo.getAbsolutePathBuilder().build();
		
		for (Chirp chirp : chirps) {
			URI chirpURI = uriInfo.getAbsolutePathBuilder().path(chirp.getId().toString()).build(); 
			chripReps.add(new ChirpRepresentation(true, chirpURI, chirp));
		}
		
	}
	@XmlElement
	public Collection<ChirpRepresentation> getChirps() {
		return chripReps;
	}
	public void setChirps(Collection<ChirpRepresentation> users) {
		this.chripReps = users;
	}
	
	@XmlElement
	public URI getSelf() {
		return self;
	}
	public void setSelf(URI self) {
		this.self = self;
	}
	
	

}
