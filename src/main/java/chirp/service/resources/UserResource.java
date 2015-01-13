package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
	public void createUser(@PathParam("username") String username, String realName) {
		UserRepository database = UserRepository.getInstance();
		database.createUser(username,  realName);
	}
	
	/**
	 * GET http://localhost:8080/users/{username}
	 * 
	 * Download: the real name of the user as plain text in the body
	 */
	@Path("{username}")
	@GET
	public String getUserRealName(@PathParam("username") String username) {
		UserRepository database = UserRepository.getInstance();
		String realName = database.getUser(username).getRealname();
		return realName;
	}
	
	
}
