package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.Chirp;
import chirp.model.User;
import chirp.model.UserRepository;

@Path("/chirps/{username}")
public class ChirpsResource {
	
	public Response createChirp(String username, String content) {
		
		User user = UserRepository.getInstance().getUser(username);
		Chirp chirp = user.createChirp(content); // update to use chirp representation
		
		URI location = UriBuilder.fromResource(this.getClass()).path(chirp.getId().toString()).build(username);
		
		return Response.created(location).build(); 
		
	}

}
