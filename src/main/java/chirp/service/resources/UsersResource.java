package chirp.service.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserRepresentation;

@Path("/users")
public class UsersResource {

	private final UserRepository userRepository = UserRepository.getInstance();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createUsers(@FormParam("username") String username,
			@FormParam("realname") String realname) {
		logger.info("Creating a user with realname={} and username={}",
				realname, username);
		userRepository.createUser(username, realname);
		return Response
				.created(
						UriBuilder.fromResource(this.getClass()).path(username)
								.build()).build();
	}
	
	@GET
	@Path("{username}")
	public UserRepresentation getUser(@PathParam("username") String username) {
		
		return new UserRepresentation(userRepository.getUser(username),false);
		
	}
	
	@GET
	public Collection<UserRepresentation> getAllUsers() {
		
		Collection<UserRepresentation> users = new ArrayList<>();
		for (User u: userRepository.getUsers()) {
			users.add(new UserRepresentation(u,true));
		}
		
		return Collections.unmodifiableCollection(users);
		
	}

}
