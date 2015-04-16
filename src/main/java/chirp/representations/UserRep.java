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
  private final ChirpsRep chirps;
  
  private UserRep() {
    this.username = null;
    this.realname = null;
    this.chirps = null;
  }
  
  public UserRep(String username, String realname) {
    this.username = username;
    this.realname = realname;
    this.chirps = null;
  }

  public UserRep(User user, boolean summary) {
    this.username = user.getUsername();
    
    this.realname = summary ? null : user.getRealname();
    this.chirps = summary ? null : new ChirpsRep(user.getChirps(), summary);
  }

  public String getUsername() {
    return username;
  }

  public String getRealname() {
    return realname;
  }

  public ChirpsRep getChirps() {
    return chirps;
  }
}
