package chirp.service.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserRepresentation;

@Path("/users")
public class UsersResource {

	private final UserRepository repo = UserRepository.getInstance();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		logger.info("Creating a user with realname={} and username={}",
				realname, username);

		repo.createUser(username, realname);

		return Response
				.created(
						UriBuilder.fromResource(this.getClass()).path(username)
								.build()).build();
	}

	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserRepresentation getUser(@PathParam("username") String username,
			@Context UriInfo uriInfo) {
		return new UserRepresentation(repo.getUser(username), uriInfo
				.getAbsolutePathBuilder().build());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UserRepresentation> getAllUsers(@Context UriInfo uriInfo) {
		Collection<User> users = repo.getUsers();
		List<UserRepresentation> userReps = new ArrayList<>(users.size());
		for (User user : users) {
			userReps.add(new UserRepresentation(user, uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build()));
		}
		return Collections.unmodifiableCollection(userReps);
	}

}
