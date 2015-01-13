package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import chirp.model.DuplicateEntityException;
import chirp.model.NoSuchEntityException;
import chirp.model.UserRepository;

@Path("/users")
public class UserResource {

	/**
	 * PUT http://localhost:8080/users/{username}
	 * 
	 * Upload: The real name of the user as plain text in the body...
	 */
	@Path("{username}")
	@PUT
	public Response createUser(@PathParam("username") String username, String realName) {
		try {
			UserRepository database = UserRepository.getInstance();
			database.createUser(username,  realName);
			return Response.ok().build();
		} catch (DuplicateEntityException e) {
			return Response.status(Status.CONFLICT).build();
		}
	}
	
	/**
	 * GET http://localhost:8080/users/{username}
	 * 
	 * Download: the real name of the user as plain text in the body
	 */
	@Path("{username}")
	@GET
	public Response getUserRealName(@PathParam("username") String username) {
		UserRepository database = UserRepository.getInstance();
		String realName = database.getUser(username).getRealname();
		return Response.ok(realName).build();
	}
	
	
}
