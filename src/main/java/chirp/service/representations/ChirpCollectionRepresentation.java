package chirp.service.representations;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpCollectionRepresentation extends
		AbstractCacheableRepresentation {

	private Collection<ChirpRepresentation> chirps = new LinkedList<>();
	private URI self;

	public ChirpCollectionRepresentation() {
		super.setLastModificationTime(new Date());

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
		return chirps;
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

}
