package chirp.service.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import chirp.model.UserRepository;

@Path("/user")
public class UserResource {

	private UserRepository userRepository = UserRepository.getInstance();

	@POST
	public void createUser(@FormParam("username") String foo,
			@FormParam("realname") String bar) {
		userRepository.createUser(foo, bar);
	}

}
