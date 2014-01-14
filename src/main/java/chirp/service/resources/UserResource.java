package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.UserRepository;


@Path("/users")
public class UserResource {
	
	private UserRepository userRepository = UserRepository.getInstance();
	
	@POST
	public Response createUser(@FormParam("username") String username, @FormParam("realname") String realname) {
		
		userRepository.createUser(username, realname);
		
		// http://localhost:8080/users</username>
		URI location = UriBuilder.fromResource(this.getClass()).path(username).build();
		
		// should return a 201 status code with the URL to the user created in the
		// location header. 
		return Response.created(location).build(); 
		
	}

}
