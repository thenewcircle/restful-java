package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import chirp.model.User;
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
	@Consumes("text/plain")  //What media-type of the "realName" parameter
	@Produces("text/plain")  //What media-type is the response msg 'successfully created'
	public Response createUser(@PathParam("username") String username, String realName) {
		UserRepository database = UserRepository.getInstance();
		database.createUser(username,  realName);
		return Response.ok("successfully created").build();
	}
	
	@Path("redirect")
	public Response redirect() {
//		return Response.status(302).location(URI.create("http://www.disney.com/")).build();
		return Response.seeOther(URI.create("http://www.disney.com/")).build();
	}
	
	/**
	 * GET http://localhost:8080/users/{username}
	 * 
	 * Download: the real name of the user as plain text in the body
	 */
	@Path("{username}")
	@GET
	@Produces("text/plain")
	public Response getUserPlainText(@PathParam("username") String username) {
		UserRepository database = UserRepository.getInstance();
		String realName = database.getUser(username).getRealname();
		return Response.ok(realName).type("text/plain").build();
	}
	/**
	 * GET http://localhost:8080/users/{username}
	 * 
	 * Download: the real name of the user as plain text in the body
	 */
	
	@Path("{username}")
	@GET
	@Produces({"text/xml", "application/xml", "application/json"})
	public Response getUser(@PathParam("username") String username) {
		UserRepository database = UserRepository.getInstance();
		User user = database.getUser(username);
		UserRepresentation body = new UserRepresentation(user);
		return Response.ok(body).build();
	}
	
	
}
