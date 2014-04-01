package chirp.service.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import chirp.model.User;
import chirp.model.UserRepository;

@Path("/users")
public class UserResource {
	/** POST /users, with form encoded data */
	@POST
	public void createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {
		UserRepository repo = UserRepository.getInstance();
		User user = repo.createUser(username, realname);
	}
}
