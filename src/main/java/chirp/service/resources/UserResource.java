package chirp.service.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.UserRepository;

@Path("/users")
public class UserResource {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@POST
	public void createUser(@FormParam("username") String username, @FormParam("realname") String realname) {
		
		logger.info("Creating a user with realname={} and username={}", realname, username);
		UserRepository.getInstance().createUser(username, realname);
		
	}

}
