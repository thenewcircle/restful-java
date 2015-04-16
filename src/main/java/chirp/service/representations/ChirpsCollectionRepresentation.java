package chirp.service.representations;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpsCollectionRepresentation {

	private Deque<ChirpRepresentation> chirps = new ArrayDeque<>();
	private URI self;

	public ChirpsCollectionRepresentation(Collection<Chirp> chirps,
			String username, UriInfo uriInfo) {

		for (Chirp chirp : chirps) {
			this.chirps.add(new ChirpRepresentation(chirp, true, uriInfo));

		}

		self = uriInfo.getAbsolutePathBuilder().build();
	}

	public ChirpsCollectionRepresentation() {
	}

	@XmlElement
	public Deque<ChirpRepresentation> getChirps() {
		return chirps;
	}

	public void setChirps(Deque<ChirpRepresentation> chirps) {
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
