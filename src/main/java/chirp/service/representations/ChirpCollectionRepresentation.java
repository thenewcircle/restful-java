package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpCollectionRepresentation {

	private Collection<ChirpRepresentation> chirps = new ArrayList<>();
	private URI self;

	public ChirpCollectionRepresentation() {
	}

	public ChirpCollectionRepresentation(Collection<Chirp> chirps,
			UriInfo uriInfo) {
		for (Chirp chirp : chirps)
			this.chirps.add(new ChirpRepresentation(chirp, uriInfo
					.getAbsolutePathBuilder().path(chirp.getId().toString())
					.build(), true));

		this.self = uriInfo.getAbsolutePath();
	}

	@XmlElement
	public Collection<ChirpRepresentation> getChirps() {
		return chirps;
	}

	public void setChirps(Collection<ChirpRepresentation> chirps) {
		this.chirps = chirps;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

}
