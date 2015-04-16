package chirp.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;
import chirp.model.User;

@XmlRootElement
public class UserRep {

  @XmlElement
  private URI self;
  
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

  public UserRep(User user, boolean summary, UriInfo uriInfo) {
    this.username = user.getUsername();
    
    this.realname = summary ? null : user.getRealname();
    this.chirps = summary ? null : new ChirpsRep(user, user.getChirps(), summary, uriInfo);

    this.self = uriInfo.getBaseUriBuilder().path("users").path(username).build();
  }

  public URI getSelf() {
    return self;
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
