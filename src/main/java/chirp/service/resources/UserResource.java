package chirp.service.resources;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserRepresentation;

@Path("/user")
public class UserResource {

	private UserRepository userRepository = UserRepository.getInstance();

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		userRepository.createUser(username, realname);

		// using a java.net.URI for creating a location
		// URI location = URI.create("/user/" + username);
		// return Response.created(location).build();

		return Response.created(
				UriBuilder.fromPath("user").path(username).build()).build();

	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserRepresentation getUser(@PathParam("username") String username) {
		return new UserRepresentation(userRepository.getUser(username));
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Collection<UserRepresentation> getAllUsers() {
		
		ArrayList<UserRepresentation> users = new ArrayList<>();
		
		for (User user : userRepository.getUsers()) {
			users.add(new UserRepresentation(user));
		}
		
		return users;
	}

	
}
