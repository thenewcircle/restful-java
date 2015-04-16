package chirp.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;
import chirp.model.User;

@XmlRootElement
public class ChirpsRep {

  @XmlElement
  private URI self;
  
  @XmlElement
  private final List<ChirpRep> chirps = new ArrayList<>();
  
  private ChirpsRep() {
  }
  
  ChirpsRep(User user, Collection<? extends Chirp> chirps, boolean summary, UriInfo uriInfo) {
    for (Chirp chirp : chirps) {
      ChirpRep chirpRep = new ChirpRep(chirp, summary, uriInfo);
      this.chirps.add(chirpRep);
    }
    this.self = uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).path("chirps").build();
  }
  
  public URI getSelf() {
    return self;
  }
  
  public List<ChirpRep> getChirps() {
    return chirps;
  }
}
