package chirp.service.representations;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.Chirp;
import chirp.model.UserRepository;

@XmlRootElement
public class ChirpCollectionRepresentation {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private URI self;
	private Collection<ChirpRepresentation> chirps = new LinkedList<>();

	public ChirpCollectionRepresentation() {
	}

	public ChirpCollectionRepresentation(String username, UriInfo uriInfo) {

		logger.info("Getting all chirps for user {}", username);

		for (Chirp chirp : UserRepository.getInstance().getUser(username)
				.getChirps()) {
			chirps.add(new ChirpRepresentation(chirp, uriInfo
					.getAbsolutePathBuilder().path(chirp.getId().toString())
					.build(), true));
		}
		self = uriInfo.getAbsolutePathBuilder().build();
	}

	@XmlElement
	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

	@XmlElement
	public Collection<ChirpRepresentation> getChirps() {
		return chirps;
	}

	public void setChirps(Collection<ChirpRepresentation> chirps) {
		this.chirps = chirps;
	}

}
