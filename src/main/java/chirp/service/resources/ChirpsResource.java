package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.Chirp;
import chirp.model.ChirpId;
import chirp.model.NoSuchEntityException;
import chirp.model.User;
import chirp.model.UserRepository;
import chirp.representations.ChirpRep;

@Path("/chirps")
public class ChirpsResource {

  private final UserRepository users = UserRepository.getInstance();
  
  @GET
  @Path("{chirpId}")
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response getChirp(@PathParam("chirpId") String chirpId) {

    ChirpId id = new ChirpId(chirpId);
    
    for (User user : users.getUsers()) {
      try {
        Chirp chirp = user.getChirp(id);
        ChirpRep chirpRep = new ChirpRep(chirp);
        return Response.ok(chirpRep).build();

      } catch (NoSuchEntityException ignored) {
        continue;
      }
    }
    
    return Response.status(Response.Status.NOT_FOUND).build();
  }
}
