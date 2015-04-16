package chirp.representations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;

@XmlRootElement
public class ChirpsRep {

  @XmlElement
  private final List<ChirpRep> chirps = new ArrayList<>();
  
  private ChirpsRep() {
  }
  
  ChirpsRep(Collection<? extends Chirp> chirps, boolean summary) {
    for (Chirp chirp : chirps) {
      ChirpRep chirpRep = new ChirpRep(chirp, summary);
      this.chirps.add(chirpRep);
    }
  }
  
  public List<ChirpRep> getChirps() {
    return chirps;
  }
}
