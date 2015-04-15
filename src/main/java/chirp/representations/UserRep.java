package chirp.representations;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;
import chirp.model.User;

@XmlRootElement
public class UserRep {

  @XmlAttribute
  private final String username;
  
  @XmlAttribute
  private final String realname;
  
  @XmlElement
  List<ChirpRep> chirps = new ArrayList<>();
  
  private UserRep() {
    this.username = null;
    this.realname = null;
  }
  
  public UserRep(String username, String realname) {
    this.username = username;
    this.realname = realname;
  }

  public UserRep(User user) {
    this.username = user.getUsername();
    this.realname = user.getRealname();
    
    for (Chirp chirp : user.getChirps()) {
      ChirpRep chirpRep = new ChirpRep(chirp);
      this.chirps.add(chirpRep);
    }
  }

  public String getUsername() {
    return username;
  }

  public String getRealname() {
    return realname;
  }

  public List<ChirpRep> getChirps() {
    return chirps;
  }
}
