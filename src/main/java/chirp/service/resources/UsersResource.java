package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.DuplicateEntityException;
import chirp.model.UserRepository;

@Path("/users")
public class UsersResource {

  UserRepository users = UserRepository.getInstance(); 
  
  @POST
  public Response createUser(@FormParam("username") String username, 
                             @FormParam("realname") String realName) {

    users.createUser(username, realName);
    URI uri = UriBuilder.fromResource(this.getClass()).path(username).build();
    return Response.created(uri).build();
  }
}
