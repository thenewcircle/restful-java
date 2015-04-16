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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.sun.research.ws.wadl.Application;

import chirp.model.Chirp;
import chirp.model.DuplicateEntityException;
import chirp.model.User;
import chirp.model.UserRepository;
import chirp.representations.UserRep;
import chirp.representations.UsersRep;

@Path("/users")
public class UsersResource {

  UserRepository users = UserRepository.getInstance(); 
  
  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "text/csv"})
  public UsersRep getUsers(@QueryParam("summary") boolean summary) {
    return new UsersRep(users.getUsers(), summary);
  }
  
  @GET
  @Path("{username}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "text/csv"})
  public UserRep getUser(@PathParam("username") String username, @QueryParam("summary") boolean summary) {
    User user = users.getUser(username);
    return new UserRep(user, summary);
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

  @POST
  @Path("{username}/chirps")
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response createChirp(@PathParam("username") String username, String content) {
    
    Chirp chrip = users.getUser(username).createChirp(content);
    
    URI uri = UriBuilder.fromResource(ChirpsResource.class)
        .path(chrip.getId().toString())
        .build();

    return Response.created(uri).build();
  }
}
