package chirp.service.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.sun.research.ws.wadl.Application;

import chirp.model.DuplicateEntityException;
import chirp.model.User;
import chirp.model.UserRepository;
import chirp.representations.UserRep;

@Path("/users")
public class UsersResource {

  UserRepository users = UserRepository.getInstance(); 
  
  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "text/csv"})
  public List<UserRep> getUsers() {
    List<UserRep> list = new ArrayList<>();
    for (User user : this.users.getUsers()) {
      list.add(new UserRep(user));
    }
    return list;
  }
  
  @GET
  @Path("{username}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "text/csv"})
  public UserRep getUser(@PathParam("username") String username) {
    User user = users.getUser(username);
    return new UserRep(user);
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response createUserForm(@FormParam("username") String username, 
                                 @FormParam("realname") String realName) {

    users.createUser(username, realName);
    URI uri = UriBuilder.fromResource(this.getClass()).path(username).build();
    return Response.created(uri).build();
  }
  
  @POST
  @Consumes("text/csv")
  public Response createUserWithCsv(UserRep user) {
    users.createUser(user.getUsername(), user.getRealname());
    URI uri = UriBuilder.fromResource(this.getClass()).path(user.getUsername()).build();
    return Response.created(uri).build();
  }
}
