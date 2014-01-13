package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.User;
import chirp.model.UserRepository;

@Path("/users")
public class UsersResource {

	private final UserRepository userRepository = UserRepository.getInstance();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {
			logger.trace("Creating a user with realname={} and username={}",
					realname, username);
			userRepository.createUser(username, realname);
			URI location = UriBuilder.fromResource(this.getClass()).path(username).build();
			return Response.created(location).build();
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserAsJSON(@PathParam("username") String username) {
		return userRepository.getUser(username);
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_XML)
	public User getUserAsXML(@PathParam("username") String username) {
		return userRepository.getUser(username);
	}


}
