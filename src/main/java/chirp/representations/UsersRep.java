package chirp.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement
public class UsersRep {

  @XmlElement
  private final List<UserRep> users;

  public UsersRep() {
    this.users = new ArrayList<>();
  }
  
  public UsersRep(Collection<User> users, boolean summary) {
    this.users = new ArrayList<UserRep>();
    for (User user : users) {
      this.users.add(new UserRep(user, summary));
    }
  }

  public List<UserRep> getUsers() {
    return users;
  }

}
