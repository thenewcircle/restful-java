package chirp.service.resprentations;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpCollectionRepresentation {

	private Collection<ChirpRepresentation> chirps = new LinkedList<>();
	private URI self;

	public ChirpCollectionRepresentation() {

	}

	public ChirpCollectionRepresentation(Collection<Chirp> chirps,
			UriInfo uriInfo) {
		for (Chirp chirp : chirps) {
			this.chirps.add(new ChirpRepresentation(chirp, uriInfo
					.getAbsolutePathBuilder().path(chirp.getId().toString())
					.build(), true));
		}
		
		this.self = uriInfo.getAbsolutePathBuilder().build();

	}

	@XmlElement
	public Collection<ChirpRepresentation> getChirps() {
		// return Collections.unmodifiableCollection(chirps);
		return chirps; // TODO: fix unmodifable collection issue with @After
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}
	
	

}
