package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/greetings")
public class GreetingResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String greet(@QueryParam("name") String name) {
		if ( name == null ) {
			return "Hello!";
		} else {
			return "Hello " + name + "!";
		}
	}

}