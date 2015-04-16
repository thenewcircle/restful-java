package chirp.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement
public class UsersRep {

  @XmlElement
  URI self;
  
  @XmlElement
  private final List<UserRep> users;

  public UsersRep() {
    this.users = new ArrayList<>();
  }
  
  public UsersRep(Collection<User> users, boolean summary, UriInfo uriInfo) {
    this.users = new ArrayList<UserRep>();
    for (User user : users) {
      this.users.add(new UserRep(user, summary, uriInfo));
    }
    this.self = uriInfo.getBaseUriBuilder().path("users").build();
  }

  public URI getSelf() {
    return self;
  }
  
  public List<UserRep> getUsers() {
    return users;
  }

}
