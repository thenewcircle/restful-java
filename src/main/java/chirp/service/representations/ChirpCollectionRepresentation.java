package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpCollectionRepresentation {

    private Collection<ChirpRepresentation> chirps = new ArrayList<>();
    private URI self;

    public ChirpCollectionRepresentation(Collection<Chirp> chirps, String username,
            UriInfo uriInfo) {

        for (final Chirp chirp : chirps) {
            this.chirps.add(new ChirpRepresentation(chirp, true, uriInfo.getAbsolutePathBuilder()
                    .path(chirp.getId().toString()).build()));
        }

        self = uriInfo.getAbsolutePathBuilder().build();
    }

    public ChirpCollectionRepresentation(URI self, Collection<ChirpRepresentation> chirps) {
        this.self = self;
        this.chirps = chirps;
    }

    public ChirpCollectionRepresentation() {
    }

    @XmlElement
    public Collection<ChirpRepresentation> getChirps() {
        return Collections.unmodifiableCollection(chirps);
    }

    @XmlElement
    public URI getSelf() {
        return self;
    }

}
